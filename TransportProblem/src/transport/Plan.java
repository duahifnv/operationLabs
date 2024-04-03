package transport;

/**
 *  Класс опорного плана
 */
public class Plan {
    protected Table table;
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
        for(Cell[] cells : table.getCells()) {
            for (Cell cell : cells) {
                if (cell.isHasTraffic()) {
                    totalCost += cell.getTraffic() * cell.getCost();
                    basedCount++;
                }
            }
        }
        if (isDegeneracy(basedCount, table)) {
            throw new Error("Вырожденный план: m + n - 1 != " + basedCount);
        }
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
    public int getTotalCost() {
        return totalCost;
    }
    public int getBasedCount() {
        return basedCount;
    }
    public void setBasedCount(int basedCount) {
        this.basedCount = basedCount;
    }
}
