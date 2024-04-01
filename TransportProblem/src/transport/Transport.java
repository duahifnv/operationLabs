package transport;

/**
 *  Класс для решения транспортной задачи
 */
public class Transport {
    private int[] srcWeights;
    private int[] dstWeights;
    private int[][] costs;
    private Plan plan;
    public Transport(int[] srcWeights, int[] dstWeights, int[][] costs) {
        this.srcWeights = srcWeights;
        this.dstWeights = dstWeights;
        this.costs = costs;
        this.plan = NorthWest.CreateSourcePlan(new Table(srcWeights, dstWeights, costs));
    }
    /** Вывод таблицы в консоль */
    public void PrintTable() {
        Print.PrintTable(plan.getTable());
    }
    public void PrintTotalCost() {
        System.out.println("Стоимость перевозок: " + plan.getTotalCost());
    }
}
