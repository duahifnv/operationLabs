package transport;

import java.util.Arrays;

/**
 *  Класс опорного плана
 */
class Plan extends Table{
    private Cell[][] plan;
    /** Конструктор опорного плана */
    public Plan(int[] srcWeights, int[] dstWeights, int[][] costs, Cell[][] table) {
        super(srcWeights, dstWeights, costs);
        this.plan = table;
    }
    /** Создание исходного опорного плана задачи */
    public void CreateSourcePlan() {
        int[] srcTemp = Arrays.copyOf(srcWeights, srcWeights.length);
        int[] dstTemp = Arrays.copyOf(dstWeights, dstWeights.length);
        CellDistribute(srcTemp, dstTemp, 0, 0);
    }
    /** Рекурсивная функция распределения поставок по ячейкам */
    private void CellDistribute(int[] srcTemp, int[] dstTemp, int src, int dst) {
        // Вышли за пределы таблицы, но груз не распределен
        if (src == srcTemp.length || dst == dstTemp.length) {
            throw new Error("Невозможно распределить груз методом СЗ");
        }
        // У поставщика еще остался груз
        if (srcTemp[src] > dstTemp[dst]) {
            plan[src][dst].setTraffic(dstTemp[dst]);
            srcTemp[src] -= dstTemp[dst];
            dstTemp[dst] = 0;
            CellDistribute(srcTemp, dstTemp, src, dst + 1);
        }
        // У поставщика и потребителя удовлетворены потребности
        else if (srcTemp[src] == dstTemp[dst]) {
            plan[src][dst].setTraffic(dstTemp[dst]);
            srcTemp[src] = dstTemp[dst] = 0;
            // Конец распределния
            if (src == srcTemp.length - 1 || dst == dstTemp.length - 1) return;
                // Добавление нулевого трафика в рядом стоящую ячейку (Невырожденный план)
            else plan[src][dst + 1].setTraffic(0);
            CellDistribute(srcTemp, dstTemp, src + 1, dst + 1);
        }
        // Потребителю нужно еще груза
        else {
            plan[src][dst].setTraffic(srcTemp[src]);
            dstTemp[dst] -= srcTemp[src];
            srcTemp[src] = 0;
            CellDistribute(srcTemp, dstTemp, src + 1, dst);
        }
    }
}
