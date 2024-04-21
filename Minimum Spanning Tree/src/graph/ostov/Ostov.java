package graph.ostov;

import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ostov {
    public static Graph findMinimal(Graph graph, Method method) throws NoSuchMethodException
    {
        switch (method) {
            case KRASKAL -> {
                return kraskal(graph);
            }
            case PRIMA -> {
                // ...
            }
        }
        throw new NoSuchMethodException();
    }
    public static Graph kraskal(Graph graph) {
        List<Integer> vertexes = graph.vertexes();
        List<Edge> minEdges = new ArrayList<>();
        int[] ftr = new int[vertexes.size()];
        int[] rank = new int[vertexes.size()];
        for (int i = 0; i < ftr.length; i++) {
            ftr[i] = i;
            rank[i] = 0;
        }
        graph.edges().sort(Comparator.comparingInt(Edge::weight));
        for (Edge edge : graph.edges()) {
            if (find(edge.from(), ftr) != find(edge.to(), ftr)) {
                minEdges.add(edge);
                union(
                        find(edge.from(), ftr),
                        find(edge.to(), ftr),
                        ftr,
                        rank);
            }
        }
        return new Graph(minEdges);
    }
    private static int find(int i, int[] ftr) {
        if (i != ftr[i]) {
            ftr[i] = find(ftr[i], ftr);
        }
        return ftr[i];
    }
    private static void union(int r, int s, int[] ftr, int[] rank) {
        if (rank[r] >= rank[s]) {
            ftr[s] = r;
            if (rank[r] == rank[s]) {
                rank[r]++;
            }
        }
        else {
            ftr[r] = s;
        }
    }
}