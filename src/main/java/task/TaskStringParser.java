package task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import exception.FriedbergInternalException;

/**
 * Responsible for converting a given list of Tasks into a serializable string
 * Will also be able to convert a string into a list of Tasks
 */
public class TaskStringParser {
    public static String serializeTasks(List<Task> tasks) {
        String data = "";
        for (Task task : tasks) {
            data += task.serialize();
            data += "\n";
        }
        return data;
    }

    /**
     * Deserializes the output from a serializeTasks call back into an
     * ArrayList<Task>
     *
     * @param dataString String as formatted by the serializeTasks funciton call
     * @return ArrayList of the original serialized Tasks
     */
    public static ArrayList<Task> deserializeTasks(String dataString) throws FriedbergInternalException {
        ArrayList<Task> tasks = new ArrayList<Task>();
        String[] taskStrings = dataString.equals("") ? new String[0] : dataString.split("\n");
        for (String taskString : taskStrings) {
            tasks.add(Task.deserialize(taskString));
        }
        return tasks;
    }
}
