package cc.cynic.jvmtests;

class InitOrder {
    public static void main(String[] args) {
        B b = new B(1, 2);
    }
}

class A {
    A() {
        System.out.println("Initializing A");
    }
}

class B extends A {
    B() {
        System.out.println("Initializing B");
    }

    B(int a, int b) {
        this();
        System.out.println("Initializing B(1, 2)");
    }
}
