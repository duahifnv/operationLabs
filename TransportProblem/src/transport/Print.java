package transport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 *  Класс вывода элементов на консоль
 */
public class Print {
    /** Сборка и вывод таблицы в консоль */
    public static void PrintTable(Table table) {
        int[] srcWeights = table.getSrcWeights();
        int[] dstWeights = table.getDstWeights();
        Cell[][] cells = table.getCells();
        List<String> labels = new ArrayList<>(IntStream.range(0, dstWeights.length)
                .mapToObj(i -> "B" + (i + 1) + "/" + dstWeights[i]).toList());
        labels.add(0, "Пост/Маг");
        List<List<String>> params = new ArrayList<>();
        for (int i = 0; i < srcWeights.length; i++) {
            List<String> row = new ArrayList<>(Collections.singleton("A" + (i + 1) + "/" + srcWeights[i]));
            for (Cell cell : cells[i]) {
                String value = (cell.isHasTraffic()) ? String.valueOf(cell.getTraffic()) : "-";
                row.add(value + "|" + cell.getCost());
            }
            params.add(row);
        }
        utils.Table.PrintTable(labels.size(), labels, params,
                "Исходный опорный план", Collections.nCopies(labels.size(), 10));
    }
}
