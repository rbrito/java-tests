package cc.cynic.jvmtests;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class ThreadedMaxFinder extends RecursiveTask<Integer> {
    static int SEQUENTIAL_CUTOFF = 1000;

    // Only one for `ForkJoinPool` the whole program.
    static final ForkJoinPool fjPool = new ForkJoinPool();

    int lo, hi; // fields for communicating inputs
    int[] arr;

    MaxFinder(int[] a, int l, int h) {
        lo = l;
        hi = h;
        arr = a;
    }

    protected Integer compute() { // override from RecursiveTask
        if (hi - lo <= SEQUENTIAL_CUTOFF) {
            return seqmax(arr);
        } else {
            // No need for the try-catch block as with Thread
            MaxFinder left = new MaxFinder(arr, lo, (lo + hi) / 2);
            MaxFinder right = new MaxFinder(arr, (lo + hi) / 2, hi);
            left.fork(); // *not* start
            int rightAns = right.compute(); // call `compute` to halve the
                                            // number of threads
            int leftAns = left.join();
            return (leftAns > rightAns) ? leftAns : rightAns;
        }
    }

    static int seqmax(int[] arr) {
        int len = arr.length;
        int ans = Integer.MIN_VALUE; // "minus infinity" (not good style)

        for (int i = 0; i < len; i++)
            if (arr[i] > ans)
                ans = arr[i];

        return ans;
    }

    static int max(int[] arr) {
        MaxFinder t = new MaxFinder(arr, 0, arr.length);
        // Note the inclusion of the Task into the Pool.
        return fjPool.invoke(t);
    }

    /** Initialization for dummy test */
    static void initarray(int[] arr, final int n) {
        for (int i = 0; i < n; i++)
            arr[i] = n - i;
    }

    public static void main(String[] args) {
        final int n = 10000;
        int[] arr = new int[n];

        initarray(arr, n);

        System.out.println("Sequential max gives: " + MaxFinder.seqmax(arr));
        System.out.println("Threaded max gives: " + MaxFinder.max(arr));
    }
}
