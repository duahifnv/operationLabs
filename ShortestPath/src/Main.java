import graph.Edge;
import graph.Graph;
import graph.path.Path;
import json.*;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String jsonPath = "D:\\DSTU\\operationLabs\\ShortestPath\\graphs\\graph.json";
        List<Edge> edges = JSONmanager.parse(jsonPath, Edge.class);
        // TODO: Проверка валидности графа
        Graph graph = new Graph(edges);
        System.out.println(graph);
        Path path = new Path(graph, false);
        /*Path path = new Path(new int[][]{  {Integer.MAX_VALUE, 3, -1, 2},
                                            {4, Integer.MAX_VALUE, 5, 1},
                                            {3, -1, Integer.MAX_VALUE, 6},
                                            {4, 5, 1, Integer.MAX_VALUE}},
                false);*/
        while (true) {
            int from = Input.Number(">Кратчайший путь из: ", 0, graph.vertexes().size() - 1);
            int to = Input.Number(">В: ", 0, graph.vertexes().size() - 1);
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
}