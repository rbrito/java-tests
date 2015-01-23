package cc.cynic.jvmtests;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class SumArray007 extends RecursiveTask<Integer> {
    static int SEQUENTIAL_CUTOFF = 1000;

    int lo, hi; // fields for communicating inputs
    int[] arr;

    SumArray007(int[] a, int l, int h) {
        lo = l;
        hi = h;
        arr = a;
    }

    protected Integer compute() { // override from RecursiveTask
        if (hi - lo <= SEQUENTIAL_CUTOFF) {
            int ans = 0;
            for (int i = lo; i < hi; i++) {
                ans += arr[i];
            }
            return ans;
        } else {
            // No need for the try-catch block as with Thread
            SumArray007 left = new SumArray007(arr, lo, (lo + hi) / 2);
            SumArray007 right = new SumArray007(arr, (lo + hi) / 2, hi);
            left.fork(); // *not* start
            int rightAns = right.compute(); // call `compute` to halve the number of threads
            int leftAns = left.join();
            return leftAns + rightAns;
        }
    }
}

class ThreadingTest007 {
    // Only one for `ForkJoinPool` the whole program.
    static final ForkJoinPool fjPool = new ForkJoinPool();

    static int sum(int[] arr) {
        SumArray007 t = new SumArray007(arr, 0, arr.length);
        // Note the inclusion of the Task into the Pool.
        return fjPool.invoke(t);
    }

    static int seqsum(int[] arr) {
        int len = arr.length;
        int ans = 0;

        for (int i = 0; i < len; i++)
            ans += arr[i];

        return ans;
    }

    public static void main(String[] args) {
        final int n = 10000;
        int[] arr = new int[n];

        // Initialization for dummy test
        for (int i = 0; i < n; i++)
            arr[i] = n - i;

        System.out.println("Sequential sum gives: " + seqsum(arr));
        System.out.println("Threaded sum gives: " + sum(arr));
    }
}
