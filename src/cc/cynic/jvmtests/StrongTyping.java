package cc.cynic.jvmtests;

import java.util.Vector;

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
