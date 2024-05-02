package graph.path;

import graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Path {
    private final int[][] distances;
    private final int[][] ftr;
    private final int size;
    public Path(Graph graph, boolean verbose) {
        distances = graph.weights();
        size = graph.vertexes().size();
        ftr = new int[size][size];
        initMatricies(verbose);
    }
    public Path(int[][] weights, boolean verbose) {
        distances = weights;
        size = weights.length;
        ftr = new int[size][size];
        initMatricies(verbose);
    }
    public int findShortestDistance(int from, int to) throws InputMismatchException {
        if (distances[from][to] == Integer.MAX_VALUE) {
            throw new InputMismatchException();
        }
        return distances[from][to];
    }
    public Integer[] findShortestPath(int from, int to) {
        ArrayList<Integer> path = new ArrayList<>();
        path.add(from);
        findPath(from, to, path);
        return path.toArray(new Integer[0]);
    }
    private void findPath(int from, int to, ArrayList<Integer> path) {
        if (ftr[from][to] != from) findPath(from, ftr[from][to], path);
        path.add(to);
    }
    private void initMatricies(boolean verbose) {
        for (int i = 0; i < size; i++) {
            Arrays.fill(ftr[i], i);
        }
        if (verbose) {
            System.out.println("k = 0");
            System.out.println("Distances: " + Arrays.deepToString(distances) + "\n" +
                    "Ftr: " + Arrays.deepToString(ftr));
        }
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (distances[i][k] < Integer.MAX_VALUE &&
                        distances[k][j] < Integer.MAX_VALUE &&
                        distances[i][j] > distances[i][k] + distances[k][j])
                    {
                        distances[i][j] = distances[i][k] + distances[k][j];
                        ftr[i][j] = ftr[k][j];
                    }
                }
            }
            if (verbose) {
                System.out.println("k = " + k + 1);
                System.out.println("Distances: " + Arrays.deepToString(distances) + "\n" +
                        "Ftr: " + Arrays.deepToString(ftr));
            }
        }
    }
}