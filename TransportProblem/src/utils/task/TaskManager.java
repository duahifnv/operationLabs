package utils.task;

import taskList.TaskList;
import java.util.Arrays;

public class TaskManager {
    public static void Parse(String... args) {
        String args0;
        try {
            args0 = args[0];
        }
        catch (Exception ArrayIndexOutOfBoundsException) {
            throw new Error("Не указан номер задачи");
        }
        String[] argsP = Arrays.copyOfRange(args, 1, args.length);
        if (args.length > 1) {
            try {
                int taskIndex = Integer.parseInt(args0);
                TaskBoot.Run(TaskList.class, taskIndex, argsP);
            }
            catch(Exception NumberFormatException) {
                throw new Error("Указан нечисловой аргумент для номера задачи");
            }
        }
        else {
            try {
                int taskIndex = Integer.parseInt(args0);
                TaskBoot.Run(TaskList.class, taskIndex);
            }
            catch(Exception NumberFormatException) {
                throw new Error("Указан нечисловой аргумент для номера задачи");
            }
        }
    }
}
