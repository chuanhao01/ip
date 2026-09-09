package parser;

import java.util.List;
import java.util.stream.Collectors;

import common.Constants;
import datahandler.DataHandler;
import exception.FriedbergException;
import exception.FriedbergInternalException;
import exception.FriedbergUserInputException;
import task.Task;
import task.TaskStringParser;

public class CommandContext {
    private List<Task> tasks;
    private DataHandler dataHandler;

    public CommandContext() throws FriedbergException {
        try {
            this.dataHandler = new DataHandler(Constants.PROJECT_DATA_DIR_PATH, Constants.FRIEDBERG_DATA_FILE_PATH);
        } catch (Exception e) {
            throw new FriedbergInternalException(e.getMessage());
        }
        this.loadTasksFromDataHandler();
    }

    /**
     * Getter for tasks.size()
     *
     * @return
     */
    public int getTasksSize() {
        return this.tasks.size();
    }

    /**
     * Called to list all the current tasks
     */
    public void listTasks() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, this.tasks.get(i).renderTask()));
        }
    }

    /**
     * Marks the given task index in tasks as done
     *
     * @param taskIndex
     */
    public void markTask(int taskIndex) throws FriedbergException {
        this.validateTaskIndex(taskIndex);
        this.tasks.get(taskIndex).mark();
    }

    /**
     * UnMarks the given task index in tasks as not done
     *
     * @param taskIndex
     */
    public void unmarkTask(int taskIndex) throws FriedbergException{
        this.validateTaskIndex(taskIndex);
        this.tasks.get(taskIndex).unmark();
    }

    /**
     * Returns the string to render the task for display
     *
     * @param taskIndex
     * @return
     */
    public String renderTask(int taskIndex) {
        return this.tasks.get(taskIndex).renderTask();
    }

    /**
     * Filters the tasks by the given nameFilter and returns the list of tasks that
     * matches
     *
     * @param nameFilter
     * @return
     */
    public List<Task> filterTasks(String nameFilter) {
        return this.tasks.stream().filter(task -> task.getName().contains(nameFilter))
                .collect(Collectors.toList());
    }

    /**
     * Adds a new task
     *
     * @param task
     */
    public void addTask(Task task) throws FriedbergException {
        this.tasks.add(task);
        this.saveTasksToDataHandler();
    }

    /**
     * Removes the task from tasks based on the given taskIndex
     * @param taskIndex
     * @return the task that was removed
     * @throws FriedbergException
     */
    public Task removeTask(int taskIndex) throws FriedbergException{
        this.validateTaskIndex(taskIndex);
        Task removedTask = this.tasks.remove(taskIndex);
        this.saveTasksToDataHandler();
        return removedTask;
    }

    private void validateTaskIndex(int taskIndex) throws FriedbergUserInputException {
        if (taskIndex < 0 || taskIndex >= this.tasks.size()) {
            throw new FriedbergUserInputException(
                    String.format("expected taskIndex to be in range of %d items", this.tasks.size()));
        }
    }

    /**
     * Loads the tasks from the given dataHandler
     *
     * @throws FriedbergException
     */
    private void loadTasksFromDataHandler() throws FriedbergException {
        String tasksDataString;
        try {

            tasksDataString = this.dataHandler.read();
        } catch (Exception e) {
            throw new FriedbergInternalException(String.format("Unable to load data, e: %s", e.getMessage()));
        }
        this.tasks = TaskStringParser.deserializeTasks(tasksDataString);
    }

    /**
     * Saves the tasks to disk using the dataHandler
     *
     * @throws FriedbergInternalException
     */
    private void saveTasksToDataHandler() throws FriedbergInternalException {
        String tasksDataString = TaskStringParser.serializeTasks(this.tasks);
        try {
            this.dataHandler.write(tasksDataString);
        } catch (Exception e) {
            throw new FriedbergInternalException(String.format("Unable to save data, e: %s", e.getMessage()));
        }
    }
}
