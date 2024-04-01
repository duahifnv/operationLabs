package transport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import utils.Table;
/**
 * Класс для решения транспортной задачи
 */
public class Transport {
    private int[] srcWeigths;
    private int[] dstWeigths;
    private int[][] costs;
    private Cell[][] table;

    /** Конструктор без готового исходного плана */
    public Transport(int[] srcWeigths, int[] dstWeights, int[][] costs) {
        this.srcWeigths = srcWeigths;
        this.dstWeigths = dstWeights;
        this.costs = costs;
        InitTable();
    }
    /** Конструктор с готовым исходным планом */
    public Transport(int[] srcWeigths, int[] dstWeights, int[][] costs, Cell[][] table) {
        this.srcWeigths = srcWeigths;
        this.dstWeigths = dstWeights;
        this.costs = costs;
        this.table = table;
    }
    /** Добавление фиктивного поставщика */
    private void CreateFictSrc(int weight) {
        int[] newSrc = Arrays.copyOf(srcWeigths, srcWeigths.length + 1);
        newSrc[srcWeigths.length] = weight;
        this.srcWeigths = newSrc;
        int[][] newCosts = Arrays.copyOf(costs, costs.length + 1);
        int nCols = costs[0].length;
        newCosts[costs.length] = Collections.nCopies(nCols, 0).stream().
                mapToInt(i -> i).toArray();
        this.costs = newCosts;
    }
    /** Добавление фиктивного потребителя */
    private void CreateFictDst(int weight) {
        int[] newDst = Arrays.copyOf(dstWeigths, dstWeigths.length + 1);
        newDst[dstWeigths.length] = weight;
        this.dstWeigths = newDst;
        int nCols = costs[0].length;
        int[][] newCosts = new int[costs.length][nCols + 1];
        for(int i = 0; i < costs.length; i++) {
            int[] newRow = Arrays.copyOf(costs[i], nCols + 1);
            newRow[nCols] = 0;
            newCosts[i] = newRow;
        }
        this.costs = newCosts;
    }
    /** Инициализация таблицы с ценами */
    private void InitTable() {
        int srcTotal = Arrays.stream(srcWeigths).sum();
        int dstTotal = Arrays.stream(dstWeigths).sum();
        if (srcTotal > dstTotal) {
            CreateFictDst(srcTotal - dstTotal);
        }
        if (srcTotal < dstTotal) {
            CreateFictSrc(dstTotal - srcTotal);
        }
        this.table = new Cell[srcWeigths.length][dstWeigths.length];
        for (int i = 0; i < srcWeigths.length; i++) {
            for (int j = 0; j < dstWeigths.length; j++) {
                table[i][j] = new Cell(costs[i][j]);
            }
        }
    }
    public void PrintTable() {
        List<String> labels = IntStream.range(0, dstWeigths.length)
                .mapToObj(i -> "B" + (i + 1) + " / " + dstWeigths[i]).toList();
        List<List<String>> params = new ArrayList<>();
        for (int i = 0; i < srcWeigths.length; i++) {
            List<String> row = new ArrayList<>(Collections.singleton("A" + (i + 1) + " / " + srcWeigths[i]));
            for (Cell cell : table[i]) {
                String value = (cell.isHasTraffic()) ? String.valueOf(cell.getTraffic()) : "-";
                row.add(value + "|" + cell.getCost());
            }
            params.add(row);
        }
        Table.PrintTable(dstWeigths.length, labels, params,
                "Исходный опорный план", Collections.nCopies(dstWeigths.length, 10));
    }
    /** Создание исходного опорного плана задачи */
    public void CreateSourcePlan() {
        int[] srcTemp = Arrays.copyOf(srcWeigths, srcWeigths.length);
        int[] dstTemp = Arrays.copyOf(dstWeigths, dstWeigths.length);
        CellDistribute(srcTemp, dstTemp, 0, 0);
    }
    /** Рекурсивная функция распределения поставок по ячейкам */
    public void CellDistribute(int[] srcTemp, int[] dstTemp, int src, int dst) {
        if (src == srcTemp.length || dst == dstTemp.length) {
            if (srcTemp[src - 1] != 0 || dstTemp[dst - 1] != 0) {
                throw new Error("Невозможно составить исходный план методом СЗ");
            }
            return;
        }
        if (srcTemp[src] >= dstTemp[dst]) {
            table[src][dst].setTraffic(dstTemp[dst]);
            srcTemp[src] -= dstTemp[dst];
            dstTemp[dst] = 0;
            CellDistribute(srcTemp, dstTemp, src, dst + 1); // У поставщика еще остался груз
        }
        else {
            table[src][dst].setTraffic(srcTemp[src]);
            dstTemp[dst] -= srcTemp[src];
            srcTemp[src] = 0;
            CellDistribute(srcTemp, dstTemp, src + 1, dst); // Потребителю нужно еще груза
        }
    }
}
