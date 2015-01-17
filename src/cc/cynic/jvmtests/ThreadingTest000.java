package cc.cynic.jvmtests;

class T extends Thread {
    int id;

    T(int i) {
        id = i;
    }

    public void run() {
        System.out.println("Hi from thread " + id + ".");
        System.out.println("Bye from thread " + id + ".");
    }
}

class ThreadingTest000 {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            T t = new T(i);
            t.start();
        }
    }
}
