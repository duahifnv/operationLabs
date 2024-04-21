import graph.*;
import graph.ostov.Method;
import graph.ostov.Ostov;
import json.JSONmanager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String jsonPath = "D:\\DSTU\\operationLabs\\Minimum Spanning Tree\\graphs\\example.json";
        List<Edge> edges = JSONmanager.parse(jsonPath, Edge.class);
        // TODO: Проверка валидности графа
        Graph graph = new Graph(edges);
        Graph minOstov;
        try {
            minOstov = Ostov.findMinimal(graph, Method.KRASKAL);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new Error("Не существует указанного метода");
        }
    }
}