package graph;

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
        flows = new int[n][n];
        for (int i = 1; i < n; i++) {
            flows[0][i] = costs[0][i];
            flows[i][0] = -costs[0][i];
        }
        heights = new int[n];
        heights[0] = n;
        heights[n-1] = 0;
        excess = new int[n];
        for (int i = 1; i < n; i++) {
            excess[i] = flows[0][i];
        }
    }
    // Поиск максимального предпотока
    public int find() {
        while (true) {
            int i;
            for (i = 1; i < n - 1; i++) {
                if (excess[i] > 0) break;
            }
            if (i == n - 1) break;
            int j;
            for (j = 0; j < n; j++) {
                if (costs[i][j] - flows[i][j] > 0 &&
                        heights[i] == heights[j] + 1) break;
            }
            if (j < n) push(i, j);
            else lift(i);
        }
        int flow = 0;
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
    }
}
