package cc.cynic.jvmtests;

public class TestFloatingPoint {
    public static void main(String[] args) {
        double inf = 1.0 / 0.0;
        double neginf = -1.0 / 0.0;
        double zero = 1.0 / inf;
        double negzero = 1.0 / neginf;
        double nan = 0.0 / 0.0;

        System.out.println("inf: " + inf);
        System.out.println("neginf: " + neginf);
        System.out.println("zero: " + zero);
        System.out.println("negzero: " + negzero);
        System.out.println("nan: " + nan);
    }
}
