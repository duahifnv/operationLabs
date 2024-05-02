package graph;

public record Edge(int from, int to, int weight) {
    @Override
    public String toString() {
        return "Из: " + from + " в: " + to + " с весом " + weight;
    }
}
