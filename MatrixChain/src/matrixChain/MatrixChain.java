package matrixChain;

import utils.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <h2>Умножение цепочки матриц (МСОР)</h2>
 * <h3>Задача оптимизации</h3>
 */
public class MatrixChain {
    public final int[] vector;
    private final Integer[][] minScalars;
    private final Integer[][] optIndices;
    private final int tableSize;
    public MatrixChain(ArrayList<Matrix> matrices) {
        this.vector = new int[matrices.size() + 1];
        vector[0] = matrices.get(0).rows();
        for (int i = 1; i < vector.length - 1; i++) {
            if (matrices.get(i).rows() != matrices.get(i - 1).cols()) {
                throw new ArithmeticException("Несовместимые размеры");
            }
            vector[i] = matrices.get(i).rows();
        }
        vector[vector.length - 1] = matrices.getLast().cols();
        minScalars = new Integer[matrices.size()][matrices.size()];
        optIndices = new Integer[matrices.size()][matrices.size()];
        tableSize = matrices.size();
        calcOptimal();
    }
    private void calcOptimal() {
        int n = tableSize;
        // Заполняем главные диагонали нулями
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
                        optIndices[i][j] = k;
                    }
                }
            }
        }
        printTable(minScalars, "Матрица минимальных скаляров");
        printTable(optIndices, "Матрица оптимальных индексов");
    }
    private void printTable(Integer[][] matrix, String title) {
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < tableSize; i++) {
            labels.add(String.valueOf(i + 1));
        }
        labels.add(0, "i/j");
        List<List<String>> params = new ArrayList<>();
        for (int i = 0; i < tableSize; i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < tableSize; j++) {
                if (matrix[i][j] == null) {
                    row.add("-");
                }
                else row.add(String.valueOf(matrix[i][j]));
            }
            row.add(0, String.valueOf(i + 1));
            params.add(row);
        }
        int n_columns = tableSize + 1;
        Table.printTable(n_columns,
                        labels,
                        params,
                        title,
                        Collections.nCopies(n_columns, 10));
    }
    private String getChain() {
        StringBuilder chain = new StringBuilder();
        buildChain(chain, 0, tableSize - 1);
        return chain.toString();
    }
    private void buildChain(StringBuilder chain, int i, int j) {
        if (i == j) {
            chain.append("A").append(i+1);
        }
        else {
            chain.append("(");
            buildChain(chain, optIndices[i][j] + 1, j);
            buildChain(chain, i, optIndices[i][j]);
            chain.append(")");
        }
    }
    @Override
    public String toString() {
        return "Оптимальная расстанока скобок: " + getChain();
    }
}
