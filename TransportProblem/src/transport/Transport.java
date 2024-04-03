package transport;

/**
 *  Класс для решения транспортной задачи (v1.0)
 */
public class Transport {
    private int[] srcWeights;
    private int[] dstWeights;
    private int[][] costs;
    private Plan plan;
    private Optimizer optimizer;
    public Transport(int[] srcWeights, int[] dstWeights, int[][] costs) {
        this.srcWeights = srcWeights;
        this.dstWeights = dstWeights;
        this.costs = costs;
        this.plan = NorthWest.CreateSourcePlan(new Table(srcWeights, dstWeights, costs));
    }
    /** Вывод таблицы в консоль */
    public void PrintTable(String title) {
        Print.PrintTable(plan.getTable(), title);
    }
    public void PrintTotalCost() {
        System.out.println("Стоимость перевозок: " + plan.getTotalCost());
    }
    public boolean isOptimized() {
        this.optimizer = new Optimizer(plan);
        optimizer.FindPotentials();
        if (optimizer.FindMinDelta() >= 0) {
            System.out.println("Минимальная дельта оценка >= 0. План оптимален");
            plan.setOptimized(true);
            return true;
        }
        return false;
    }
    public void RecalcPlan() {
        System.out.println("**Пересчет плана**");
        optimizer.RecalcPlan();
        plan.UpdateTraffic();
    }
}
