package cc.cynic.jvmtests;

class ClassWithStaticMethod {
    static void foo() {
        System.out.println("Hi from static method.");
    }
}

public class StaticMethodAccess {
    public static void main(String[] args) {
        ClassWithStaticMethod c = new ClassWithStaticMethod();
        c.foo(); // Generates a warning, but works when compiled.
        ClassWithStaticMethod.foo();
    }
}
