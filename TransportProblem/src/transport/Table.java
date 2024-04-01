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
    protected int[] srcWeights;
    protected int[] dstWeights;
    protected int[][] costs;
    private Cell[][] table;
    private Plan plan;
    public Table(int[] srcWeights, int[] dstWeights, int[][] costs) {
        this.srcWeights = srcWeights;
        this.dstWeights = dstWeights;
        this.costs = costs;
    }
    public Cell[][] getTable() {
        return table;
    }
    public void CreateTable() {
        this.table = EmptyTable();
    }
    /** Создание опорного плана задачи */
    public void CreatePlan() {
        this.plan = new Plan(srcWeights, dstWeights, costs, table);
        plan.CreateSourcePlan();
    }
    /** Инициализация опорной таблицы */
    private Cell[][] EmptyTable() {
        int srcTotal = Arrays.stream(srcWeights).sum();
        int dstTotal = Arrays.stream(dstWeights).sum();
        if (srcTotal > dstTotal) {
            // Фиктивный потребитель
            CreateFictDst(srcTotal - dstTotal);
        }
        if (srcTotal < dstTotal) {
            // Фиктивный поставщик
            CreateFictSrc(dstTotal - srcTotal);
        }
        Cell[][] table = new Cell[srcWeights.length][dstWeights.length];
        for (int i = 0; i < srcWeights.length; i++) {
            for (int j = 0; j < dstWeights.length; j++) {
                table[i][j] = new Cell(costs[i][j]);
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
    /** Вывод таблицы в консоль */
    public void PrintTable() {
        List<String> labels = new ArrayList<>(IntStream.range(0, dstWeights.length)
                .mapToObj(i -> "B" + (i + 1) + "/" + dstWeights[i]).toList());
        labels.add(0, "Пост/Маг");
        List<List<String>> params = new ArrayList<>();
        for (int i = 0; i < srcWeights.length; i++) {
            List<String> row = new ArrayList<>(Collections.singleton("A" + (i + 1) + "/" + srcWeights[i]));
            for (Cell cell : table[i]) {
                String value = (cell.isHasTraffic()) ? String.valueOf(cell.getTraffic()) : "-";
                row.add(value + "|" + cell.getCost());
            }
            params.add(row);
        }
        utils.Table.PrintTable(labels.size(), labels, params,
                "Исходный опорный план", Collections.nCopies(labels.size(), 10));
    }
}
