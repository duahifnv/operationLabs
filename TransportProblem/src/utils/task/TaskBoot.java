package utils.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;

public class TaskBoot {
    public static void Run(Class<?> taskList, int taskIndex) throws InvocationTargetException, IllegalAccessException {
        Method[] tasks = taskList.getDeclaredMethods();
        for (Method m : tasks) {
            if (m.getName().substring(0, 5).equals("task" + taskIndex)) {
                if (m.getParameterCount() > 0) {
                    throw new Error(String.format("Для задания %d требуется передать %d аргумента(-ов)",
                            taskIndex, m.getParameterCount()));
                }
                m.invoke(null);
                return;
            }
        }
        throw new NoSuchElementException("Не существует задания под номером " + taskIndex);
    }
    public static void Run(Class<?> taskList, int taskIndex, String[] args) throws InvocationTargetException, IllegalAccessException {
        Method[] tasks = taskList.getDeclaredMethods();
        for (Method m : tasks) {
            if (m.getName().substring(0, 5).equals("task" + taskIndex)) {
                if (m.getParameterCount() != args.length) {
                    throw new Error("Количество параметров не совпадает с требуемым в задаче");
                }
                m.invoke(null, (Object[]) args);
                return;
            }
        }
        throw new NoSuchElementException("Не существует задания под номером " + taskIndex);
    }
}
