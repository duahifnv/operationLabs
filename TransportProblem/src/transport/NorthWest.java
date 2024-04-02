package transport;

import java.util.Arrays;

public class NorthWest {
    /** Создание исходного опорного плана задачи */
    public static Plan CreateSourcePlan(Table table) {
        int[] srcWeights = table.getSrcWeights();
        int[] dstWeights = table.getDstWeights();
        int[] srcTemp = Arrays.copyOf(srcWeights, srcWeights.length);
        int[] dstTemp = Arrays.copyOf(dstWeights, dstWeights.length);
        CellDistribute(table, srcTemp, dstTemp, 0, 0);
        return new Plan(table);
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

}
