import graph.Edge;
import graph.Graph;
import graph.path.Path;
import json.*;

import java.util.Arrays;
import java.util.InputMismatchException;

public class Main {
    private static String jsonPath = "D:\\DSTU\\operationLabs\\ShortestPath\\graphs\\example.json";
    private static Graph graph = new Graph(JSONmanager.parse(jsonPath, Edge.class));
    private static int[][] weights = new int[][]   {{0, 3, -1, 2},
                                                    {4, 0, 5, 1},
                                                    {3, -1, 0, 6},
                                                    {4, 5, 1, 0}};
    public static void main(String[] args) {
        Path path = new Path(weights, true);
        while (true) {
            int from = Input.Number(">Кратчайший путь из: ", 0, graph.vertexes().size() - 1);
            int to = Input.Number(">В: ", 0, graph.vertexes().size() - 1);
            printPath(path, from, to);
        }
    }
    public static void printPath(Path path, int from, int to) {
        System.out.println("------------------");

        try {
            int shortestPath = path.findShortestDistance(from, to);
            System.out.printf("Кратчайший путь из %d в %d: %s%n", from, to, Arrays.toString(path.findShortestPath(from, to)));
            System.out.printf("Расстояние из %d в %d: %d%n", from, to, shortestPath);
        }
        catch (InputMismatchException e) {
            System.out.printf("Не существует пути из %d в %d!%n", from, to);
        }
        System.out.println("------------------");
    }
}