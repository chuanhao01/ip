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

/**
 * To be passed when executing a Command, so as to allow the command to act on
 * the context
 */
public class CommandContext {
    private List<Task> tasks;
    private final DataHandler dataHandler;

    /**
     * Initialise a new CommandContext without any arguments
     * Defaults to using constants set in the project
     *
     * @throws FriedbergException Wraps any Datahandler Exception thrown
     */
    public CommandContext() throws FriedbergException {
        try {
            this.dataHandler = new DataHandler(Constants.PROJECT_DATA_DIR_PATH, Constants.FRIEDBERG_DATA_FILE_PATH);
        } catch (Exception e) {
            throw new FriedbergInternalException(e.getMessage());
        }
        this.loadTasksFromDataHandler();
    }

    /**
     * Returns the number of tasks in the current task list.
     *
     * @return number of tasks in the current task list
     */
    public int getTasksSize() {
        return this.tasks.size();
    }

    /**
     * Lists all current tasks.
     */
    public void listTasks() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, this.tasks.get(i).renderTask());
        }
    }

    /**
     * Marks the task at the given task index as done.
     *
     * @param taskIndex zero-based index of the task to mark
     * @throws FriedbergException if the task index is invalid
     */
    public void markTask(int taskIndex) throws FriedbergException {
        this.validateTaskIndex(taskIndex);
        this.tasks.get(taskIndex).mark();
    }

    /**
     * Unmarks the task at the given task index as not done.
     *
     * @param taskIndex zero-based index of the task to unmark
     * @throws FriedbergException if the task index is invalid
     */
    public void unmarkTask(int taskIndex) throws FriedbergException {
        this.validateTaskIndex(taskIndex);
        this.tasks.get(taskIndex).unmark();
    }

    /**
     * Returns the string to render the task for display.
     *
     * @param taskIndex zero-based index of the task to render
     * @return formatted task string for display
     */
    public String renderTask(int taskIndex) {
        return this.tasks.get(taskIndex).renderTask();
    }

    /**
     * Filters the tasks by the given name filter.
     *
     * @param nameFilter text that matching task names should contain
     * @return list of tasks whose names contain the filter text
     */
    public List<Task> filterTasks(String nameFilter) {
        return this.tasks.stream().filter(task -> task.getName().contains(nameFilter))
                .collect(Collectors.toList());
    }

    /**
     * Adds a new task.
     *
     * @param task task to add
     * @throws FriedbergException if the task cannot be saved
     */
    public void addTask(Task task) throws FriedbergException {
        this.tasks.add(task);
        this.saveTasksToDataHandler();
    }

    /**
     * Removes the task at the given task index.
     *
     * @param taskIndex zero-based index of the task to remove
     * @return the task that was removed
     * @throws FriedbergException if the task index is invalid or the updated list
     *                            cannot be saved
     */
    public Task removeTask(int taskIndex) throws FriedbergException {
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
     * Loads the tasks from the data handler.
     *
     * @throws FriedbergException if tasks cannot be loaded or deserialized
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
     * Saves the tasks to disk using the data handler.
     *
     * @throws FriedbergInternalException if tasks cannot be saved
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
