/**
 * <h2>Умножение цепочки матриц (МСОР)</h2>
 * <h3>Задача оптимизации</h3>
 */
public class MatrixChain {
    private final int[] vector;
    private int[][] minScalars;
    private int[][] optIndices;
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
    }
    public int[] getVector() {
        return vector;
    }
}
