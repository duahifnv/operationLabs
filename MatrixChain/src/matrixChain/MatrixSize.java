package matrixChain;

public record MatrixSize(int rows, int cols) {
    @Override
    public String toString() {
        return "Матрица " + rows + " x " + cols;
    }
}
