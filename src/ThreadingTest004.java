import java.lang.Thread;

class SumThread004 extends Thread {
    static int SEQUENTIAL_CUTOFF = 1000; // arbitrary
    int lo, hi; // fields for communicating inputs
    int[] arr;
    int ans = 0; // for communicating output

    SumThread004(int[] a, int l, int h) {
        lo = l; hi = h; arr = a;
    }

    public void run() { // override from Thread
        if (hi - lo < SEQUENTIAL_CUTOFF) {
            // notice that only this thread is writing onto the variable
            // ans, as there is one such variable for each instance of
            // SumThread (and each thread is in 1-to-1 correspondence with
            // the instance of SumThread).
            for (int i = lo; i < hi; i++) {
                ans += arr[i];
            }
        } else {
            try {
                SumThread004 left = new SumThread004(arr, lo, (lo+hi)/2);
                SumThread004 right = new SumThread004(arr, (lo+hi)/2, hi);
                left.start();
                right.run();
                left.join();
                ans = left.ans + right.ans;
            } catch (InterruptedException e) {
            }
        }
    }
}

class ThreadingTest004 {
    static int sum(int[] arr) {
        SumThread004 t = new SumThread004(arr, 0, arr.length);
        t.run(); // *not* start
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
