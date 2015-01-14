import java.lang.Thread;

class SumThread extends Thread {
    static int SEQUENTIAL_CUTOFF = 1000; // arbitrary
    int lo, hi; // fields for communicating inputs
    int[] arr;
    int ans = 0; // for communicating output

    SumThread(int[] a, int l, int h) {
        lo = l; hi = h; arr = a;
    }

    public void run() { // override from Thread
        if (hi - lo < SEQUENTIAL_CUTOFF) {
            for (int i = lo; i < hi; i++) {
                ans += arr[i];
            }
        } else {
            try {
                SumThread left = new SumThread(arr, lo, (lo+hi)/2);
                SumThread right = new SumThread(arr, (lo+hi)/2, hi);
                left.start();
                right.start();
                left.join();
                right.join();
                ans = left.ans + right.ans;
            } catch (InterruptedException e) {
            }
        }
    }
}

class ThreadingTest003 {
    static int sum(int[] arr) {
        SumThread t = new SumThread(arr, 0, arr.length);
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
