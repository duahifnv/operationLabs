package transport;

import java.util.List;

public class CycleFinder {
    public static boolean FindCycle(List<Cell> cycle, Cell[][] cells) {
        return FindNextStep(cycle, null, cells);
    }
    private static boolean FindNextStep(List<Cell> cycle, Direction prevDir, Cell[][] cells) {
        Cell cell = cycle.getLast();
        // Ищем соседей по всем направлениям
        for (Direction dir : Direction.values()) {
            Cell nearestCell = NearestCell(cell, cells, dir);
            if (nearestCell != null) {
                // Если в цикле уже есть ячейка
                if (cycle.contains(nearestCell)) {
                    // Если пришли в начальную ячейку
                    if (nearestCell == cycle.getFirst()) {
                        // Цикл существует
                        if (cycle.size() % 2 == 0 && cycle.size() >= 4) {
                            cycle.add(nearestCell);
                            return true;
                        }
                        // Цикл невозможен
                        continue;
                    }
                    continue;
                }
                // Если есть прошлое направление
                if (prevDir != null) {
                    // Если направление совпадает с прошлым направлением (не ломанная)
                    if (dir == prevDir) {
                        cycle.remove(cell);
                        cycle.add(nearestCell);
                        if (FindNextStep(cycle, dir, cells)) return true;
                        continue;
                    }
                    // Если направление противоположно прошлому направлению (не ломанная)
                    if (dir.getCode() == prevDir.getCode()) continue;
                }
                cycle.add(nearestCell);
                if (FindNextStep(cycle, dir, cells)) return true;
            }
        }
        cycle.remove(cell);
        return false;
    }
    private static Cell NearestCell(Cell cell, Cell[][] cells, Direction dir) {
        int x = cell.getX();    // X - индекс строки
        int y = cell.getY();    // Y - индекс столбца
        switch (dir) {
            case UP -> {
                for(int i = x - 1; i >= 0; i--) {
                    if(cells[i][y].isHasTraffic()) {
                        return cells[i][y];
                    }
                }
                return null;
            }
            case DOWN -> {
                for(int i = x + 1; i <= cells.length - 1; i++) {
                    if(cells[i][y].isHasTraffic()) {
                        return cells[i][y];
                    }
                }
                return null;
            }
            case RIGHT -> {
                for(int i = y + 1; i <= cells[0].length - 1; i++) {
                    if(cells[x][i].isHasTraffic()) {
                        return cells[x][i];
                    }
                }
                return null;
            }

            case LEFT -> {
                for(int i = y - 1; i >= 0; i--) {
                    if(cells[x][i].isHasTraffic()) {
                        return cells[x][i];
                    }
                }
                return null;
            }
        }
        return null;
    }
}
enum Direction {
    UP(0), RIGHT(1), DOWN(0), LEFT(1);
    private final int code;
    Direction(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
