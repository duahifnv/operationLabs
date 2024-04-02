package transport;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Optimize {
    public static void Optimizer(Plan plan) {
        Table table = plan.getTable();
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
    }
}
