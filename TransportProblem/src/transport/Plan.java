package transport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *  Класс опорного плана
 */
public class Plan {
    private Table table;
    private List<Integer> base;
    private int totalCost;
    /** Конструктор опорного плана */
    public Plan(Table table, List<Integer> base, int totalCost) {
        this.table = table;
        this.base = base;
        this.totalCost = totalCost;
    }
    public Table getTable() {
        return table;
    }
    public List<Integer> getBase() {
        return base;
    }
    public int getTotalCost() {
        return totalCost;
    }
}
