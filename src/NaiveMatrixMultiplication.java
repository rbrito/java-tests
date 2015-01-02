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

	// We should probably make a Matrix an object
	public static void main(String[] args) {
		Random gen = new Random(0); // set seed to obtain deterministic results

		final int m = 4, n = 4, p = 4;
		double A[][] = new double[m][p];
		double B[][] = new double[p][n];
		double C[][] = new double[m][n];

		for (int i = 0; i < m; i++)
			for (int j = 0; j < p; j++)
				A[i][j] = gen.nextDouble();

		print(A, m, p, "A");

		for (int i = 0; i < p; i++)
			for (int j = 0; j < n; j++)
				B[i][j] = gen.nextDouble();

		print(B, p, n, "B");

		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				for (int k = 0; k < p; k++)
					C[i][j] += A[i][k] * B[k][j];

		print(C, m, n, "C");
	}
}
