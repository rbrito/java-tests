import java.util.Random;

class NaiveMatrixMultiplication {
    static void print(double[][] M, int m, int n, String name) {
        System.out.println(name + " = [");

        for (int i = 0; i < m; i++) {
            System.out.print("[");
            for (int j = 0; j < n; j++) {
                System.out.print(M[i][j] + ", ");
            }
            System.out.println("], ");
        }
        System.out.println("]");

    }

    static void fill(double[][] M, int m, int n, Random gen) {
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                M[i][j] = gen.nextDouble();
    }

    static void multiply(double[][] A, int m, int p, double[][] B, int n, double[][] C) {
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < p; k++)
                    C[i][j] += A[i][k] * B[k][j];
    }

    // We should probably make a Matrix an object
    public static void main(String[] args) {
        Random gen = new Random(0); // set seed to obtain deterministic results

        final int m = 4, n = 4, p = 4;
        double A[][] = new double[m][p];
        double B[][] = new double[p][n];
        double C[][] = new double[m][n];

        fill(A, m, p, gen);
        print(A, m, p, "A");

        fill(B, p, n, gen);
        print(B, p, n, "B");

        multiply(A, m, p, B, n, C);

        print(C, m, n, "C");
    }
}
