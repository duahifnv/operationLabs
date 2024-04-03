package transport;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Класс оптимизации опорного плана
 */
public class Optimizer {
    private Cell worstDelta;
    private Table table;
    private Plan plan;
    private int[] srcPotentials;
    private int[] dstPotentials;
    public Optimizer(Plan plan) {
        this.plan = plan;
        this.table = plan.getTable();
    }
    /** Поиск потенциалов плана */
    public void FindPotentials() {
        Cell[][] cells = table.getCells();
        int[][] costs = table.getCosts();
        Integer[] srcPotentials = new Integer[table.getSrcWeights().length];
        Integer[] dstPotentials = new Integer[table.getDstWeights().length];
        List<Equation> unsolved = new ArrayList<>();
        for (int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[0].length; j++) {
                if (cells[i][j].isHasTraffic()) {
                    unsolved.add(new Equation(i, j, costs[i][j]));
                }
            }
        }
        // Решаем первое уравнение, т.к. u1 = 0
        Equation firstEq = unsolved.getFirst();
        firstEq.u = 0;
        firstEq.v = firstEq.d - firstEq.u;
        unsolved.remove(firstEq);
        srcPotentials[0] = firstEq.u;
        dstPotentials[0] = firstEq.v;
        // Решаем оставшиеся уравнения
        while (unsolved.size() > 0) {
            for (Equation equation : List.copyOf(unsolved)) {
                // u + ? = d
                if (srcPotentials[equation.uIdx] != null) {
                    equation.v = equation.d - srcPotentials[equation.uIdx];
                    dstPotentials[equation.vIdx] = equation.v;
                }
                // ? + v = d
                if (dstPotentials[equation.vIdx] != null) {
                    equation.u = equation.d - dstPotentials[equation.vIdx];
                    srcPotentials[equation.uIdx] = equation.u;
                }
                // u + v = d -> Уравнение решено
                if (equation.u != null && equation.v != null) {
                    unsolved.remove(equation);
                }
            }
        }
        this.srcPotentials = Arrays.stream(srcPotentials).mapToInt(Integer::intValue).toArray();
        this.dstPotentials = Arrays.stream(dstPotentials).mapToInt(Integer::intValue).toArray();
        System.out.println("Потенциалы поставщиков: " + Arrays.toString(srcPotentials));
        System.out.println("Потенциалы потребителей: " + Arrays.toString(dstPotentials));
    }
    /** Поиск минимальной дельта оценки */
    public int FindMinDelta() {
        int minDelta = Integer.MAX_VALUE;
        for(Cell[] cells : table.getCells()) {
            for(Cell cell : cells) {
                if(!(cell.isHasTraffic())) {
                    int delta = cell.getCost() - (srcPotentials[cell.getX()] + dstPotentials[cell.getY()]);
                    cell.setDelta(delta);
                    if (delta < minDelta) {
                        minDelta = delta;
                        worstDelta = cell;
                    }
                }
            }
        }
        System.out.printf("Минимальная дельта оценка: %d (Δ%d%d)%n", minDelta,
                worstDelta.getX() + 1, worstDelta.getY() + 1);
        return minDelta;
    }
    /** Цикл пересчета плана */
    public void RecalcPlan() {
        worstDelta.setTraffic(0);
        List<Cell> cycle = new ArrayList<>(Collections.singleton(worstDelta));
        if (CycleFinder.FindCycle(cycle, table.getCells())) {
            String formatCycle = cycle.stream().map(x -> String.format("x%d%d", x.getX() + 1, x.getY() + 1))
                    .collect(Collectors.joining("->"));
            System.out.println("Цикл перестановок: " + formatCycle);
        }
        int idx = 0;
        int lambda = Integer.MAX_VALUE;
        for (Cell cell : cycle) {
            if (idx % 2 != 0) {
                lambda = Math.min(lambda, cell.getTraffic());
            }
            idx++;
        }
        boolean degeneracy = true;  // Флаг вырожденности плана (добавляется новая ячейка)
        for (int i = 0; i < cycle.size() - 1; i++) {
            Cell cell = cycle.get(i);
            int sign = (i % 2 == 0) ? 1 : -1;
            cell.addLambda(lambda * sign);
            if (cell.getTraffic() == 0 && degeneracy && cell != worstDelta) {
                cell.removeTraffic();
                degeneracy = false; // План больше не вырожден
            }
        }
    }
}