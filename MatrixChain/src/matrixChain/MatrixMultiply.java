package matrixChain;

public class MatrixMultiply {
    MatrixChain matrixChain;
    public MatrixMultiply(MatrixChain matrixChain) {
        this.matrixChain = matrixChain;
    }
    public static Matrix multiply(Matrix A, Matrix B) {
        if (A.cols() != B.rows()) {
            throw new ArithmeticException("Несовместимые размеры матриц");
        }
        int[][] matrixA = A.getMatrix();
        int[][] matrixB = B.getMatrix();
        int[][] matrixC = new int[A.rows()][B.cols()];
        for (int i = 0; i < A.rows(); i++) {
            matrixC[i] = new int[B.cols()];
            for (int j = 0; j < matrixC[i].length; j++) {
                int sum = 0;
                for (int k = 0; k < A.cols(); k++) {
                    sum += matrixA[i][k] * matrixB[k][j];
                }
                matrixC[i][j] = sum;
            }
        }
        return new Matrix(matrixC);
    }
}
