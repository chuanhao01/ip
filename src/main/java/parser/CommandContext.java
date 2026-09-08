package parser;

import java.util.List;

import datahandler.DataHandler;
import exception.FriedbergException;
import exception.FriedbergInternalException;
import task.Task;
import task.TaskStringParser;

public class CommandContext {
    private List<Task> tasks;
    private DataHandler dataHandler;

    /**
     * Called to list all the current tasks
     */
    public void listTasks() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, this.tasks.get(i).renderTask()));
        }
    }

    public void loadTasksFromData() throws FriedbergException {
        String tasksDataString;
        try {

            tasksDataString = this.dataHandler.read();
        } catch (Exception e) {
            throw new FriedbergInternalException(String.format("Unable to load data, e: %s", e.getMessage()));
        }
        this.tasks = TaskStringParser.deserializeTasks(tasksDataString);
    }

    public void saveTasksToData() throws FriedbergInternalException {
        String tasksDataString = TaskStringParser.serializeTasks(this.tasks);
        try {
            this.dataHandler.write(tasksDataString);
        } catch (Exception e) {
            throw new FriedbergInternalException(String.format("Unable to save data, e: %s", e.getMessage()));
        }
    }
}
