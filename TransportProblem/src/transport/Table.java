package transport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 *  Класс таблицы плана
 */
public class Table {
    private int[] srcWeights;
    private int[] dstWeights;
    private int[][] costs;
    private Cell[][] cells;
    public Table(int[] srcWeights, int[] dstWeights, int[][] costs) {
        this.srcWeights = srcWeights;
        this.dstWeights = dstWeights;
        this.costs = costs;
        this.cells = EmptyTable();
    }
    public Cell[][] getCells() {
        return cells;
    }
    public void setCell(int src, int dst, Cell cell) {
        cells[src][dst] = cell;
    }
    public int[] getSrcWeights() {
        return srcWeights;
    }
    public int[] getDstWeights() {
        return dstWeights;
    }
    public int[][] getCosts() {
        return costs;
    }
    /** Инициализация опорной таблицы */
    private Cell[][] EmptyTable() {
        int srcTotal = Arrays.stream(srcWeights).sum();
        int dstTotal = Arrays.stream(dstWeights).sum();
        if (srcTotal > dstTotal) {
            // Фиктивный потребитель
            CreateFictDst(srcTotal - dstTotal);
            System.out.printf("Добавлен фиктивный потребитель: B%d = %d%n", dstWeights.length, srcTotal - dstTotal);
        }
        if (srcTotal < dstTotal) {
            // Фиктивный поставщик
            CreateFictSrc(dstTotal - srcTotal);
            System.out.printf("Добавлен фиктивный поставщик: A%d = %d%n", srcWeights.length, dstTotal - srcTotal);
        }
        Cell[][] table = new Cell[srcWeights.length][dstWeights.length];
        for (int i = 0; i < srcWeights.length; i++) {
            for (int j = 0; j < dstWeights.length; j++) {
                table[i][j] = new Cell(i, j, costs[i][j]);
            }
        }
        return table;
    }
    /** Добавление фиктивного поставщика */
    private void CreateFictSrc(int weight) {
        int[] newSrc = Arrays.copyOf(srcWeights, srcWeights.length + 1);
        newSrc[srcWeights.length] = weight;
        srcWeights = newSrc;
        int[][] newCosts = Arrays.copyOf(costs, costs.length + 1);
        int nCols = costs[0].length;
        newCosts[costs.length] = Collections.nCopies(nCols, 0).stream().
                mapToInt(i -> i).toArray();
        costs = newCosts;
    }
    /** Добавление фиктивного потребителя */
    private void CreateFictDst(int weight) {
        int[] newDst = Arrays.copyOf(dstWeights, dstWeights.length + 1);
        newDst[dstWeights.length] = weight;
        dstWeights = newDst;
        int nCols = costs[0].length;
        int[][] newCosts = new int[costs.length][nCols + 1];
        for(int i = 0; i < costs.length; i++) {
            int[] newRow = Arrays.copyOf(costs[i], nCols + 1);
            newRow[nCols] = 0;
            newCosts[i] = newRow;
        }
        costs = newCosts;
    }

}
