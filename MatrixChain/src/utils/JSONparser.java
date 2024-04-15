package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

/**
 * <h2>Парсер JSON обьектов</h2>
 * @param <T>
 */
public class JSONparser<T> {
    public JSONparser() {};
    public T[] parseJsonArray(File file, Class<T> clazz)
            throws IOException, ClassNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + clazz.getName() + ";");
        return mapper.readValue(file, arrayClass);
    }
}
