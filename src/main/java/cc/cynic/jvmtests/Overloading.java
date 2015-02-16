package cc.cynic.jvmtests;

class Overloading {
    static void f(int a, int b) {
        System.out.println("Inside the int function.");
    }

    static void f(double a, double b) {
        System.out.println("Inside the double function.");
    }

    public static void main(String[] args) {
        f(1, 2);
        f(1, 2.0);
    }
}
