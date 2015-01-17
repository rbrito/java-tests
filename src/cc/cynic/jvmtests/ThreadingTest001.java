package cc.cynic.jvmtests;

import java.lang.Thread;

class SumThread001 extends Thread {
    int lo, hi; // fields for communicating inputs
    int[] arr;
    int ans = 0; // for communicating output

    SumThread001(int[] a, int l, int h) {
        lo = l;
        hi = h;
        arr = a;
    }

    public void run() { // override from Thread
        for (int i = lo; i < hi; i++)
            ans += arr[i];
    }
}

class ThreadingTest001 {
    static int sum(int[] arr) throws InterruptedException {
        int len = arr.length;
        int ans = 0;

        // Here we lauch the threads to perform the computation. The number
        // of threads is hardcoded to be exactly 4 for simplicity.
        SumThread001[] ts = new SumThread001[4];

        for (int i = 0; i < 4; i++) {
            ts[i] = new SumThread001(arr, (i * len) / 4, ((i + 1) * len) / 4);
            ts[i].start();
        }

        // Here we collect the results
        for (int i = 0; i < 4; i++) {
            ts[i].join(); // CRUCIAL to get right
            ans += ts[i].ans;
        }

        return ans;
    }

    static int seqsum(int[] arr) {
        int len = arr.length;
        int ans = 0;

        for (int i = 0; i < len; i++)
            ans += arr[i];

        return ans;
    }

    public static void main(String[] args) throws InterruptedException {
        final int n = 1000;
        int[] arr = new int[n];

        // Initialization for dummy test
        for (int i = 0; i < n; i++)
            arr[i] = n - i;

        System.out.println("Sequential sum gives: " + seqsum(arr));
        System.out.println("Threaded sum gives: " + sum(arr));
    }
}
