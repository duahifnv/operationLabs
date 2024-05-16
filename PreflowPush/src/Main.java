import graph.PreflowPush;

public class Main {
    private static int[][] weights = new int[][]   {{0, 2, 3, 0},
                                                    {0, 0, 1, 1},
                                                    {0, 0, 0, 4},
                                                    {0, 0, 0, 0}};
    public static void main(String[] args) {
        PreflowPush preflowPush = new PreflowPush(weights);
        System.out.println("Максимальный поток: " + preflowPush.find());
    }
}