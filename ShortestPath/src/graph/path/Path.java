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
        for (int i = 0; i < distances.length; i++) {
            distances[i][i] = 0;
        }
        size = graph.vertexes().size();
        ftr = new int[size][size];
        if (hasNegativeCycle()) throw new Error("Найден цикл отриацтельного веса");
        initMatricies(verbose);
    }
    public Path(int[][] weights, boolean verbose) {
        distances = weights;
        for (int i = 0; i < distances.length; i++) {
            distances[i][i] = 0;
        }
        size = weights.length;
        ftr = new int[size][size];
        if (hasNegativeCycle()) throw new Error("Найден цикл отриацтельного веса");
        initMatricies(verbose);

    }
    private boolean hasNegativeCycle() {
        for (int i = 0; i < distances.length; i++) {
            if (findNegCycle(i, distances[i])) {
                return true;
            }
        }
        return false;
    }
    private boolean findNegCycle(int index, int[] l) {
        for (int i = 0; i < l.length; i++) {
            if (l[i] < 0) {
                if (i == index) return true;
                if (findNegCycle(index, distances[i])) return true;
            }
        }
        return false;
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
            System.out.println("Distances:");
            for (int i = 0; i < distances.length; i++) {
                System.out.println(Arrays.toString(distances[i]));
            }
            System.out.println("Ftr:");
            for (int i = 0; i < ftr.length; i++) {
                System.out.println(Arrays.toString(ftr[i]));
            }
        }
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (distances[i][j] > distances[i][k] + distances[k][j]) {
                        distances[i][j] = distances[i][k] + distances[k][j];
                        ftr[i][j] = ftr[k][j];
                    }
                }
            }
            if (verbose) {
                System.out.println("k = " + (k+1));
                System.out.println("Distances:");
                for (int i = 0; i < distances.length; i++) {
                    System.out.println(Arrays.toString(distances[i]));
                }
                System.out.println("Ftr:");
                for (int i = 0; i < ftr.length; i++) {
                    System.out.println(Arrays.toString(ftr[i]));
                }
            }
        }
    }
}