package transport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
        int[] srcPotentials = new int[table.getSrcWeights().length];
        int[] dstPotentials = new int[table.getDstWeights().length];
        List<Integer> unassignedSrcIdx =  new ArrayList<>(IntStream.range(1, srcPotentials.length)
                .boxed().toList());
        List<Integer> unassignedDstIdx = new ArrayList<>(IntStream.range(0, dstPotentials.length)
                .boxed().toList());
        // Проход по строкам таблицы
        for (int i = 0; i < cells.length; i++) {
            // Если все потенциалы столцов и потенциал строки найдены -> пропуск
            if (unassignedDstIdx.size() == 0 && !unassignedSrcIdx.contains(i)) {
                continue;
            }
            // Проход по столбцам таблицы
            for (int j = 0; j < cells[i].length; j++) {
                // Если все потенциалы строки и потенциал столбца найдены -> пропуск
                if (unassignedSrcIdx.size() == 0 && !unassignedDstIdx.contains(j)) {
                    continue;
                }
                // Если в ячейке есть трафик - находим потенциал столбца
                if (cells[i][j].isHasTraffic()) {
                    dstPotentials[j] = costs[i][j] - srcPotentials[i];
                    unassignedDstIdx.remove(Integer.valueOf(j));
                    // Находим потенциалы ненайденных строк по столбцу
                    for(int row : List.copyOf(unassignedSrcIdx)) {
                        if (cells[row][j].isHasTraffic()) {
                            srcPotentials[row] = costs[row][j] - dstPotentials[j];
                            unassignedSrcIdx.remove((Integer)row);
                        }
                    }
                }
            }
        }
        this.srcPotentials = srcPotentials;
        this.dstPotentials = dstPotentials;
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
        Cell nearestLeft = cycle.get(cycle.size() - 2);
        Cell nearestRight = cycle.get(1);
        int lambda = Math.min(nearestLeft.getTraffic(), nearestRight.getTraffic());
        boolean degeneracy = true;  // Флаг вырожденности плана (добавляется новая ячейка)
        for (int i = 0; i < cycle.size() - 1; i++) {
            Cell cell = cycle.get(i);
            int sign = (i % 2 == 0) ? 1 : -1;
            cell.addLambda(lambda * sign);
            if (cell.getTraffic() == 0 && degeneracy) {
                cell.removeTraffic();
                degeneracy = false; // План больше не вырожден
            }
        }
    }
}
