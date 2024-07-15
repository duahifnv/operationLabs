package graph;

import java.util.Arrays;

public class PreflowPush {
    private int[][] costs, flows;
    private int[] heights, excess;
    private int n;
    public PreflowPush(int[][] weights) {
        costs = weights;
        if (hasNegative(costs)) {
            throw new Error("Граф имеет отрицательные веса");
        }
        n = costs.length;
        // Инициализация потоков
        flows = new int[n][n];
        for (int i = 1; i < n; i++) {
            flows[0][i] = costs[0][i];
            flows[i][0] = -costs[0][i];
        }
        // Инициализация высот вершин
        heights = new int[n];
        heights[0] = n;
        excess = new int[n];
        // Инициализация избытков
        for (int i = 1; i < n; i++) {
            excess[i] = flows[0][i];
        }
    }
    // Поиск максимального предпотока
    public int find() {
        int iteration = 1;
        while (true) {
            System.out.println("Итерация: " + iteration);
            System.out.println("Потоки: " + Arrays.deepToString(flows));
            System.out.println("Высоты вершин: " + Arrays.toString(heights));
            System.out.println("Избытки вершин: " + Arrays.toString(excess));
            int i;
            // Находим первую избыточную вершину
            for (i = 1; i < n - 1; i++) {
                if (excess[i] > 0) break;
            }
            // Если не нашли -> конец итераций
            if (i == n - 1) break;
            int j;
            for (j = 0; j < n; j++) {
                if (costs[i][j] - flows[i][j] > 0 &&
                        heights[i] == heights[j] + 1) break;
            }
            // Если нашли вершину, в которой поток меньше значения и она выше соседней вершины
            if (j < n) push(i, j);
            // Иначе поднимаем вершину
            else lift(i);
            iteration++;
        }
        int flow = 0;
        // Находим максимальный поток как сумму потоков с значением дуги > 0
        for (int i = 0; i < n; i++) {
            if (costs[0][i] > 0) {
                flow += flows[0][i];
            }
        }
        return Math.max(flow, 0);
    }
    // Проверка на дуги отрицательных весов
    private static boolean hasNegative(int[][] costs) {
        for (int[] w : costs) {
            for (int i : w) {
                if (i < 0) return true;
            }
        }
        return false;
    }
    // Проталкивание потока из from в to
    private void push(int from, int to) {
        int d = Math.min(excess[from], costs[from][to] - flows[from][to]);
        flows[from][to] += d;
        flows[to][from] = -flows[from][to];
        excess[from] -= d;
        excess[to] += d;
        System.out.println(">Протолкнули предпоток из " + from + " в " + to);
    }
    // Операция поднятия вершины
    private void lift(int vertex) {
        int d = Integer.MAX_VALUE;
        for (int i = 0; i < flows.length; i++) {
            if (costs[vertex][i] - flows[vertex][i] > 0) {
                d = Math.min(d, heights[i]);
            }
        }
        if (d == Integer.MAX_VALUE) return;
        heights[vertex] = d + 1;
        System.out.println(">Подняли вершину " + vertex + " на " + (d + 1));
    }
}
