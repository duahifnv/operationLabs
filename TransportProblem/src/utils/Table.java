package utils;

import java.util.List;

public class Table {
    public static void PrintTable(int n_columns, List<String> labels,
                                  List<List<String>> params, String title, List<Integer> fieldSizes) {
        System.out.println(title);
        if (labels.size() != n_columns || fieldSizes.size() != n_columns) {
            System.out.println("Количество столбцов не совпадает с количеством параметров");
            return;
        }
        String dashStroke = "+";
        for (Integer size : fieldSizes) {
            dashStroke += "-".repeat(size);
            dashStroke += "+";
        }
        // Заголовки
        System.out.println(dashStroke);
        System.out.print("|");
        for (int i = 0; i < n_columns; i++) {
            String format = "%-" + fieldSizes.get(i % n_columns) + "s";
            System.out.printf(format, labels.get(i));
            System.out.print("|");
        }
        System.out.println();
        // Значения
        System.out.println(dashStroke);
        for (int i = 0; i < params.size(); i++) {
            System.out.print("|");
            for (int j = 0; j < n_columns; j++) {
                String format = "%-" + fieldSizes.get(j % n_columns) + "s";
                System.out.printf(format, params.get(i).get(j));
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println(dashStroke);
    }
}
