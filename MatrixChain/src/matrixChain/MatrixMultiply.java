package matrixChain;

import utils.Regex;
import utils.Pair;

import java.util.*;

public class MatrixMultiply {
    MatrixChain matrixChain;
    public MatrixMultiply(MatrixChain matrixChain) {
        this.matrixChain = matrixChain;
    }
    public static Pair<Integer, Matrix> multiply(Matrix A, Matrix B) {
        if (A.cols() != B.rows()) {
            throw new ArithmeticException("Несовместимые размеры матриц");
        }
        int[][] matrixA = A.getMatrix();
        int[][] matrixB = B.getMatrix();
        int[][] matrixC = new int[A.rows()][B.cols()];
        int multiplyCounter = 0;
        for (int i = 0; i < A.rows(); i++) {
            matrixC[i] = new int[B.cols()];
            for (int j = 0; j < matrixC[i].length; j++) {
                int sum = 0;
                for (int k = 0; k < A.cols(); k++) {
                    sum += matrixA[i][k] * matrixB[k][j];
                    multiplyCounter++;
                }
                matrixC[i][j] = sum;
            }
        }
        return new Pair<>(multiplyCounter, new Matrix(matrixC));
    }
    private static ArrayDeque<Matrix> findMultiplyQueue(MatrixChain matrixChain, ArrayList<Matrix> matrices) {
        ArrayDeque<Matrix> multiplyQueue = new ArrayDeque<>();
        String chain = matrixChain.getChain();
        Regex regex = new Regex("matrixPair", "(\\([A-Z][0-9]{1,2}[A-Z][0-9]{1,2}\\))");
        String firstBrace = regex.findSubstr(chain);
        List<String> firstIndices = Arrays.asList(firstBrace.replaceAll("[^0-9]+", " ")
                .trim().split(" "));  // (i, j)
        multiplyQueue.add(matrices.get(Integer.parseInt(firstIndices.get(1)) - 1)); // (j)
        multiplyQueue.add(matrices.get(Integer.parseInt(firstIndices.get(0)) - 1)); // (i)
        chain = chain.replace(firstBrace, "A0");
        while (regex.findSubstr(chain) != null) {
            firstBrace = regex.findSubstr(chain);
            firstIndices = Arrays.asList(firstBrace.replaceAll("[^0-9]+", " ")
                    .trim().split(" "));
            if (Objects.equals(firstIndices.get(0), "0"))  {
                multiplyQueue.add(matrices.get(Integer.parseInt(firstIndices.get(1)) - 1));
            }
            else {
                multiplyQueue.add(matrices.get(Integer.parseInt(firstIndices.get(0)) - 1));
            }
            chain = chain.replace(firstBrace, "A0");
        }
        return multiplyQueue;
    }
    public static Matrix multiplyChain(MatrixChain matrixChain, ArrayList<Matrix> matrices) {
        int multiplyCounter = 0;
        String chain = matrixChain.getChain();
        ArrayDeque<Matrix> multiplyQueue = findMultiplyQueue(matrixChain, matrices);
        while (multiplyQueue.size() > 1) {
            Matrix first = multiplyQueue.pop();
            Matrix second = multiplyQueue.pop();
            if (isSwapped(second, first)) {
                Matrix C = first;
                first = second;
                second = C;
                // swap(second, first);
            }
            Pair<Integer, Matrix> multiplied = multiply(second, first);
            multiplyCounter += multiplied.first;
            multiplyQueue.addFirst(multiplied.second);
        }
        return multiplyQueue.pop();
    }
    private static boolean isSwapped(Matrix A, Matrix B) {
        return (A.rows() == B.cols()) && (A.cols() != B.rows());
    }
}