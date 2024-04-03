package taskList;

import transport.*;

public class TaskList {
    public static void main(String[] args) {
        // Вариант 6
        /*int[] srcWeights = {100, 120, 400};
        int[] dstWeights = {100, 120, 100, 200, 300};
        int[][] costs = {{1, 2, 4, 1, 5},
                         {1, 2, 1, 3, 1},
                         {2, 1, 3, 3, 1}};*/
        // Вариант 7+ (Калькулятор = 520)
        /*int[] srcWeights = {200, 350, 150};
        int[] dstWeights = {100, 100, 80, 90, 70};
        int[][] costs = {{1, 4, 5, 3, 1},
                         {2, 3, 1, 4, 2},
                         {2, 1, 3, 1, 1}};*/
        // Вариант 8+ (Калькулятор = 640) (Руками = 640)
        int[] srcWeights = {200, 120, 150};
        int[] dstWeights = {100, 90, 200, 30, 80};
        int[][] costs = {{1, 2, 4, 1, 5},
                {1, 2, 1, 3, 1},
                {2, 1, 3, 3, 1}};
        // Вариант 9+ (Калькулятор = 790)
        /*int[] srcWeights = {300, 120, 300};
        int[] dstWeights = {100, 120, 130, 100, 90};
        int[][] costs = {{1, 4, 5, 3, 1},
                         {2, 1, 2, 1, 2},
                         {3, 1, 4, 2, 1}};*/
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