package cc.cynic.jvmtests;

/* This can be compiled with gcj (as packaged in Debian) if you:

 wget http://gee.cs.oswego.edu/dl/jsr166/dist/jsr166.jar
 ecj-gcj -cp jsr166.jar TestJavaConcurrentInstallation.java

 Notice that we are using ecj-gcj here instead of pure gcj.
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// Stolen from:
// http://homes.cs.washington.edu/~djg/teachingMaterials/grossmanSPAC_forkJoinFramework.html
class Incrementor extends RecursiveTask<Integer> {
    int theNumber;

    Incrementor(int x) {
        theNumber = x;
    }

    public Integer compute() { // analogous of `run` for `Thread`s.
        return theNumber + 1;
    }
}

public class TestJavaConcurrentInstallation {
    // Global pool of threads
    public static ForkJoinPool fjPool = new ForkJoinPool();

    public static void main(String[] args) {
        // Use the pool

        // Idiom: Pass arguments to the thread by sending them to the
        // constructor of the class that implements the task.
        int fortyThree = fjPool.invoke(new Incrementor(42));
        System.out.println(fortyThree);
    }
}
