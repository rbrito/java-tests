package cc.cynic.jvmtests.threading;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

class SumArray006 extends RecursiveAction {
    static int SEQUENTIAL_CUTOFF = 1000;

    int lo, hi; // fields for communicating inputs
    int[] arr;
    int ans = 0; // for communicating output

    SumArray006(int[] a, int l, int h) {
        lo = l;
        hi = h;
        arr = a;
    }

    protected void compute() { // override from RecursiveAction
        if (hi - lo <= SEQUENTIAL_CUTOFF) {
            for (int i = lo; i < hi; i++) {
                ans += arr[i];
            }
        } else {
            // No need for the try-catch block as with Thread
            SumArray006 left = new SumArray006(arr, lo, (lo + hi) / 2);
            SumArray006 right = new SumArray006(arr, (lo + hi) / 2, hi);
            left.fork(); // *not* start
            right.compute(); // call `compute` to halve the number of threads
            left.join();
            ans = left.ans + right.ans;
        }
    }
}

class ThreadingTest006 {
    // Only one for `ForkJoinPool` the whole program.
    static final ForkJoinPool fjPool = new ForkJoinPool();

    static int sum(int[] arr) {
        SumArray006 t = new SumArray006(arr, 0, arr.length);
        // Note the inclusion of the Action into the Pool.
        fjPool.invoke(t);

        return t.ans;
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
