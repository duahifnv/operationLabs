/**
 * <h2>Умножение цепочки матриц (МСОР)</h2>
 * <h3>Задача оптимизации</h3>
 */
public class MatrixChain {
    private final int[] vector;
    private Integer[][] minScalars;
    private Integer[][] optIndices;
    public MatrixChain(Matrix[] matrices) {
        this.vector = new int[matrices.length + 1];
        vector[0] = matrices[0].rows();
        for (int i = 1; i < vector.length - 1; i++) {
            if (matrices[i].rows() != matrices[i - 1].cols()) {
                throw new ArithmeticException("Несовместимые размеры");
            }
            vector[i] = matrices[i].rows();
        }
        vector[vector.length - 1] = matrices[matrices.length - 1].cols();
        minScalars = new Integer[matrices.length][matrices.length];
        optIndices = new Integer[matrices.length][matrices.length];
    }
    public int[] getVector() {
        return vector;
    }
    public void calcOptimal() {
        int n = vector.length - 1;
        // Fill main diagonals with zeros
        for (int i = 0; i < n; i++) {
            minScalars[i][i] = 0;
            optIndices[i][i] = 0;
        }
        for (int sublen = 2; sublen <= n; sublen++) {
            for (int i = 0; i < n - sublen + 1; i++) {
                int j = i + sublen - 1;
                minScalars[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int value = minScalars[i][k] + minScalars[k+1][j]
                            + vector[i] * vector[k+1] * vector[j+1];
                    if (value < minScalars[i][j]) {
                        minScalars[i][j] = value;
                        optIndices[i][j] = k + 1;
                    }
                }
            }
        }
    }

}
