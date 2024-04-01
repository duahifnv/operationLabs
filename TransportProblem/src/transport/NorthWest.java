package transport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NorthWest {
    /** Создание исходного опорного плана задачи */
    public static Plan CreateSourcePlan(Table table) {
        int[] srcWeights = table.getSrcWeights();
        int[] dstWeights = table.getDstWeights();
        int[] srcTemp = Arrays.copyOf(srcWeights, srcWeights.length);
        int[] dstTemp = Arrays.copyOf(dstWeights, dstWeights.length);
        CellDistribute(table, srcTemp, dstTemp, 0, 0);
        List<Integer> base = new ArrayList<>();     // Список перевозок
        int totalCost = 0;    // Стоимость всех перевозок
        for (Cell[] cells : table.getCells()) {
            for (Cell cell : cells) {
                if (cell.isHasTraffic()) {
                    base.add(cell.getTraffic());
                    totalCost += cell.getTraffic() * cell.getCost();
                }
            }
        }
        if (isDegeneracy(base.size(), table)) {
            throw new Error("Вырожденный план: m + n - 1 != " + base.size());
        }
        return new Plan(table, base, totalCost);
    }
    /** Рекурсивная функция распределения поставок по ячейкам */
    private static void CellDistribute(Table table, int[] srcTemp, int[] dstTemp, int src, int dst) {
        // Вышли за пределы таблицы, но груз не распределен
        if (src == srcTemp.length || dst == dstTemp.length) {
            throw new Error("Невозможно распределить груз методом СЗ");
        }
        // У поставщика еще остался груз
        if (srcTemp[src] > dstTemp[dst]) {
            table.getCells()[src][dst].setTraffic(dstTemp[dst]);
            srcTemp[src] -= dstTemp[dst];
            dstTemp[dst] = 0;
            CellDistribute(table, srcTemp, dstTemp, src, dst + 1);
        }
        // У поставщика и потребителя удовлетворены потребности
        else if (srcTemp[src] == dstTemp[dst]) {
            table.getCells()[src][dst].setTraffic(dstTemp[dst]);
            srcTemp[src] = dstTemp[dst] = 0;
            // Конец распределния
            if (src == srcTemp.length - 1 || dst == dstTemp.length - 1) return;
                // Добавление нулевого трафика в рядом стоящую ячейку (Невырожденный план)
            else table.getCells()[src][dst + 1].setTraffic(0);
            CellDistribute(table, srcTemp, dstTemp, src + 1, dst + 1);
        }
        // Потребителю нужно еще груза
        else {
            table.getCells()[src][dst].setTraffic(srcTemp[src]);
            dstTemp[dst] -= srcTemp[src];
            srcTemp[src] = 0;
            CellDistribute(table, srcTemp, dstTemp, src + 1, dst);
        }
    }
    /** Проверка плана на вырожденность */
    private static boolean isDegeneracy(int baseN, Table table) {
        // m + n - 1 = N
        int requiredN = table.getSrcWeights().length + table.getDstWeights().length - 1;
        return (requiredN != baseN);
    }
}
