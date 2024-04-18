package matrixChain;

import java.util.Arrays;
import java.util.Random;

public class Matrix {
    private final int[][] matrix;
    public Matrix(int rows, int cols, int min, int max) {
        this.matrix = getRandomFilled(rows, cols, min, max, 0);
    }
    public Matrix(int rows, int cols, int min, int max, long seed) {
        this.matrix = getRandomFilled(rows, cols, min, max, seed);
    }
    public Matrix(int[][] matrix) {
        this.matrix = matrix;
    }
    public static int[][] getRandomFilled(int rows, int cols, int min, int max, long seed) {
        Random random = new Random(seed);
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new int[cols];
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = random.nextInt((max - min) + 1) + min;
            }
        }
        return matrix;
    }
    public int[][] getMatrix() {
        return matrix;
    }
    public int rows() {
        return matrix.length;
    }
    public int cols() {
        return matrix[0].length;
    }
    @Override
    public String toString() {
        return "Матрица: " + Arrays.deepToString(matrix);
    }
}
