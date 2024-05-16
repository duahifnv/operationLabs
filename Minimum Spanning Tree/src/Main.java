import graph.*;
import graph.ostov.Method;
import graph.ostov.Ostov;
import json.JSONmanager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String jsonPath = "D:\\DSTU\\operationLabs\\Minimum Spanning Tree\\graphs\\graph.json";
        List<Edge> edges = JSONmanager.parse(jsonPath, Edge.class);
        // TODO: Проверка валидности графа
        Graph graph = new Graph(edges);
        System.out.println(graph);
        System.out.println("Вес: " + graph.weight());
        Graph minOstov;
        try {
            minOstov = Ostov.findMinimal(graph, Method.KRASKAL);
            System.out.println("Минимальный остов: ");
            System.out.println(minOstov);
            System.out.println("Вес мин. остова: " + minOstov.weight());
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new Error("Не существует указанного метода");
        }
    }
}