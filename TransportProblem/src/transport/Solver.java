package transport;

public class Solver {
    public static void Solve(int[] srcWeights, int[] dstWeights, int[][] costs) {
        Transport transport = new Transport(srcWeights, dstWeights, costs);
        final int MAXRECOUNTS = 10;
        transport.PrintTable("Исходный опорный план");
        transport.PrintTotalCost();
        int idx = 0;
        while (true) {
            if (idx > MAXRECOUNTS) {
                System.out.printf("Превышено число пересчетов (%d)%n", MAXRECOUNTS);
                break;
            }
            if (transport.isOptimized()) break;
            transport.RecalcPlan();
            transport.PrintTable("Опорный план после оптимизации");
            transport.PrintTotalCost();
            idx++;
        }
    }
}
