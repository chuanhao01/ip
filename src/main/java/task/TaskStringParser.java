package task;

import java.util.List;

/**
 * Responsible for converting a given list of Tasks into a serializable string
 * Will also be able to convert a string into a list of Tasks
 */
public class TaskStringParser {
    public static String serializeTasks(List<Task> tasks){
        String data = "";
        for (Task task: tasks){
            data += task.serialize();
            data += "\n";
        }
        return data;
    }
}
