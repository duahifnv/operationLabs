public record Matrix(int[][] matrix) {
    public int rows() {
        return matrix.length;
    }
    public int cols() {
        return matrix[0].length;
    }
    @Override
    public String toString() {
        return "Матрица " + rows() + " x " + cols();
    }
}
