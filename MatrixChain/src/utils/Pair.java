package utils;

/**
 * <h2>Пара элементов</h2>
 * @param <K>
 * @param <V>
 */
public class Pair<K, V> {
    public K first;
    public V second;
    private String label = "Pair";
    // Конструктор без названия пары
    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }
    // Конструктор с названием пары
    public Pair(K first, V second, String label) {
        this.first = first;
        this.second = second;
        this.label = label;
    }
    // Статический метод создания новой пары
    public static <K,V> Pair<K,V> make_pair(K first, V second) {
        return new Pair<>(first, second);
    }
    // Сеттер названия пары
    public void setLabel(String label) {
        this.label = label;
    }
    // Геттер названия пары
    public String getLabel() {
        return label;
    }
    // Вывод информации о типах пары
    @Override
    public String toString() {
        return String.format("%s: {%s, %s}",
                label,
                first.toString(),
                second.toString());
    }
}
