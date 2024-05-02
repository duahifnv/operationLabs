package graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class Graph {
    private final List<Edge> edges;
    private final List<Integer> vertexes;
    public Graph(List<Edge> edges) {
        this.edges = edges;
        Set<Integer> vertexes = new HashSet<>();
        for (Edge edge : edges) {
            vertexes.add(edge.from());
            vertexes.add(edge.to());
        }
        this.vertexes = vertexes.stream().toList();
    }
    public List<Edge> edges() {
        return edges;
    }
    public List<Integer> vertexes() {
        return vertexes;
    }
    public int weight() {
        int weight = 0;
        for (Edge edge : edges) {
            weight += edge.weight();
        }
        return weight;
    }
    public int[][] weights() {
        int size = this.vertexes.size();
        int[][] weights = new int[size][size];
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[0].length; j++) {
                weights[i][j] = Integer.MAX_VALUE;
            }
        }
        for (Edge edge : this.edges()) {
            weights[edge.from()][edge.to()] = edge.weight();
        }
        for (int i = 0; i < size; i++) {
            weights[i][i] = 0;
        }
        return weights;
    }
    @Override
    public String toString() {
        return "Граф: " +
                Arrays.deepToString(edges.toArray());
    }
}
