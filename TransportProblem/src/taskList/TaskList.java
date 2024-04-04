package taskList;

import transport.*;

public class TaskList {
    public static void task1() {
        // Вариант 1
        int[] srcWeights = {200, 150, 350};
        int[] dstWeights = {120, 120, 200, 180, 110};
        int[][] costs = {{1, 2, 3, 5, 2},
                        {4, 6, 7, 3, 1},
                        {2, 2, 3, 4, 5}};
        Solver.Solve(srcWeights, dstWeights, costs);
    }
    public static void task3() {
        // Вариант 3+ (Руками = 2620)
        int[] srcWeights = {180, 300, 230};
        int[] dstWeights = {110, 140, 220, 190, 120};
        int[][] costs = {{2, 4, 5, 8, 6},
                         {7, 3, 6, 4, 5},
                         {8, 5, 6, 5, 3}};
        Solver.Solve(srcWeights, dstWeights, costs);
    }
    public static void task5() {
        // Вариант 5
        int[] srcWeights = {100, 300, 150};
        int[] dstWeights = {110, 200, 90, 100, 120};
        int[][] costs = {{5, 2, 3, 6, 1},
                {1, 1, 4, 4, 2},
                {4, 1, 2, 3, 5}};
        Solver.Solve(srcWeights, dstWeights, costs);
    }
    public static void task6() {
        // Вариант 6
        int[] srcWeights = {100, 120, 400};
        int[] dstWeights = {100, 120, 100, 200, 300};
        int[][] costs = {{1, 2, 4, 1, 5},
                         {1, 2, 1, 3, 1},
                         {2, 1, 3, 3, 1}};
        Solver.Solve(srcWeights, dstWeights, costs);
    }
    public static void task7() {
        // Вариант 7+ (Калькулятор = 520)
        int[] srcWeights = {200, 350, 150};
        int[] dstWeights = {100, 100, 80, 90, 70};
        int[][] costs = {{1, 4, 5, 3, 1},
                         {2, 3, 1, 4, 2},
                         {2, 1, 3, 1, 1}};
        Solver.Solve(srcWeights, dstWeights, costs);
    }
    public static void task8() {
        // Вариант 8+ (Калькулятор = 640) (Руками = 640)
        int[] srcWeights = {200, 120, 150};
        int[] dstWeights = {100, 90, 200, 30, 80};
        int[][] costs = {{1, 2, 4, 1, 5},
                {1, 2, 1, 3, 1},
                {2, 1, 3, 3, 1}};
        Solver.Solve(srcWeights, dstWeights, costs);
    }
    public static void task9() {
        // Вариант 9+ (Калькулятор = 790)
        int[] srcWeights = {300, 120, 300};
        int[] dstWeights = {100, 120, 130, 100, 90};
        int[][] costs = {{1, 4, 5, 3, 1},
                {2, 1, 2, 1, 2},
                {3, 1, 4, 2, 1}};
        Solver.Solve(srcWeights, dstWeights, costs);
    }
    public static void task0() {
        // Вариант 12+
        int[] srcWeights = {200, 150, 35};
        int[] dstWeights = {120, 130, 130, 180, 110};
        int[][] costs = {{1, 4, 7, 8, 1}, {2, 3, 1, 4, 1}, {5, 1, 3, 2, 3}};
        Solver.Solve(srcWeights, dstWeights, costs);
    }
}