package graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class Graph {
    private List<Edge> edges;
    private List<Integer> vertexes;
    public Graph(List<Edge> edges) {
        this.edges = edges;
        Set<Integer> vertexes = new HashSet<>();
        for (Edge edge : edges) {
            vertexes.add(edge.from());
            vertexes.add(edge.to());
        }
        this.vertexes = vertexes.stream().toList();
    }
    public List<Edge> getEdges() {
        return edges;
    }
    public List<Integer> getVertexes() {
        return vertexes;
    }
}
