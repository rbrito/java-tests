package cc.cynic.jvmtests;

import java.util.Vector;

/**
 * StrongTyping: Add test to show strong typing in Java.
 *
 * Not even what would be allowed as a type promotion in C/C++ (namely, int
 * to double) is allowed when setting an element of a Vector<Double>.
 */
public class StrongTyping {
    public static void main(String[] args) {
        Vector<Double> vd = new Vector<Double>();

        // vd.add(1); // doesn't compile fails
        vd.add(1.0); // works

        for (double d : vd) {
            System.out.println(d);
        }
    }
}
