package transport;

import java.util.Arrays;

/**
 *  Класс опорного плана
 */
public class Plan {
    protected Table table;
    private int[][] traffic;
    private int totalCost;
    private int basedCount;
    private boolean isOptimized;
    /** Конструктор опорного плана */
    public Plan(Table table) {
        this.table = table;
        UpdateTraffic(table);
    }
    /** Обновление матрицы перевозок и ее параметров */
    private void UpdateTraffic(Table table) {
        int totalCost = 0;
        int basedCount = 0;
        int[][] traffic = new int[table.getSrcWeights().length]          // Матрица перевозок
                                 [table.getDstWeights().length];
        Arrays.stream(traffic).forEach(row -> Arrays.fill(row, -1)); // Заполняем -1
        for(Cell[] cells : table.getCells()) {
            for (Cell cell : cells) {
                if (cell.isHasTraffic()) {
                    traffic[cell.getX()][cell.getY()] = cell.getTraffic();
                    totalCost += cell.getTraffic() * cell.getCost();
                    basedCount++;
                }
            }
        }
        if (isDegeneracy(basedCount, table)) {
            throw new Error("Вырожденный план: m + n - 1 != " + basedCount);
        }
        this.traffic = traffic;
        this.totalCost = totalCost;
        this.basedCount = basedCount;
    }
    /** Проверка плана на вырожденность */
    private static boolean isDegeneracy(int baseN, Table table) {
        // m + n - 1 = N
        int requiredN = table.getSrcWeights().length + table.getDstWeights().length - 1;
        return (requiredN != baseN);
    }
    public Table getTable() {
        return table;
    }
    public int[][] getTraffic() {
        return traffic;
    }
    public void setTraffic(int[][] traffic) {
        this.traffic = traffic;
    }
    public int[][] getBase() {
        return traffic;
    }
    public int getTotalCost() {
        return totalCost;
    }
}
