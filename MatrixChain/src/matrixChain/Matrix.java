package matrixChain;

public record Matrix(int rows, int cols) {
    @Override
    public String toString() {
        return "Матрица " + rows + " x " + cols;
    }
}
