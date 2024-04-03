package taskList;

import transport.*;

public class TaskList {
    public static void main(String[] args) {
        /*int[] srcWeights = {150, 320, 400};
        int[] dstWeights = {100, 120, 100, 200, 300};
        int[][] costs = {{1, 2, 4, 1, 5},
                         {1, 2, 1, 3, 1},
                         {2, 1, 3, 3, 1}};*/
        int[] srcWeights = {200, 120, 150};
        int[] dstWeights = {100, 90, 200, 30, 80};
        int[][] costs = {{1, 2, 4, 1, 5},
                         {1, 2, 1, 3, 1},
                         {2, 1, 3, 3, 1}};
        Transport transport = new Transport(srcWeights, dstWeights, costs);
        int maxRecounts = 10;
        transport.PrintTable("Исходный опорный план");
        transport.PrintTotalCost();
        for (int i = 0; i < maxRecounts; i++) {
            if (transport.isOptimized()) break;
            transport.RecalcPlan();
            transport.PrintTable("Опорный план после оптимизации");
            transport.PrintTotalCost();
        }
    }
}