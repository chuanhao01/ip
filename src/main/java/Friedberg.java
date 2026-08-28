import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import common.Constants;
import exception.FriedbergCommandException;
import exception.FriedbergException;
import exception.FriedbergInternalException;
import exception.FriedbergUserInputException;
import task.Deadline;
import task.Event;
import task.Task;
import task.TaskStringParser;
import task.ToDo;

public class Friedberg {
    private static final String name = "Friedberg";
    private List<Task> tasks;
    private DataHandler dataHandler;

    public static void main(String[] args) {
        try {
            Friedberg chatbot = new Friedberg();
            chatbot.run();
        } catch (FriedbergException e) {
            System.out.println(e.getMessage());
        }
    }

    public Friedberg() throws FriedbergException {
        this.tasks = new ArrayList<Task>();
        try {
            this.dataHandler = new DataHandler(Constants.PROJECT_DATA_DIR_PATH, Constants.FRIEDBERG_DATA_FILE_PATH);
        } catch (Exception e) {
            throw new FriedbergInternalException(e.getMessage());
        }
        this.loadTasksFromData();
    }

    /**
     * Runs the chatbot Friedberg
     */
    public void run() {
        this.greet();
        this.command();
    }

    public void greet() {
        String banner = "______     _          _ _                   \n"
                + "|  ___|   (_)        | | |                  \n"
                + "| |_ _ __ _  ___  __| | |__   ___ _ __ __ _ \n"
                + "|  _| '__| |/ _ \\/ _` | '_ \\ / _ \\ '__/ _` |\n"
                + "| | | |   | |  __/ (_| | |_) |  __/ | | (_| |\n"
                + "\\_| |_|   |_|\\___|\\__,_|_.__/ \\___|_|  \\__, |\n"
                + "                                        __/ |\n"
                + "                                       |___/ \n";
        System.out.println(banner);
        System.out.println(String.format("Hello! I'm %s.", Friedberg.name));
        System.out.println("I am a chatbot beep boop, what can I do for you?");
        System.out.println("____________________________________________________________");
        System.out.println();
    }

    public void command() {
        Scanner stdin = new Scanner(System.in); // Create a Scanner object
        boolean isBye = false;
        while (!isBye) {
            String userInput = stdin.nextLine();
            userInput = userInput.strip();
            String command = userInput;
            System.out.println("____________________________________________________________");
            try {
                if (command.equals("bye")) {
                    System.out.println("Bye bye, see you again next time.");
                    isBye = true;
                } else if (command.equals("list")) {
                    this.listTasks();
                } else {
                    try {
                        if (userInput.startsWith("mark") || userInput.startsWith("unmark")) {
                            this.markCommand(userInput);
                        } else if (userInput.startsWith("deadline") || userInput.startsWith("todo")
                                || userInput.startsWith("event")) {
                            this.addTaskCommand(userInput);
                        } else if (userInput.startsWith("delete")) {
                            this.deleteCommand(userInput);
                        } else {
                            throw new FriedbergCommandException("Unknown command given by user", userInput);
                        }
                    } catch (FriedbergException e) {
                        System.out.println(String.format("User Error using Friedberg: %s", e.getMessage()));
                    }
                }
            } catch (Exception e) {
                System.out.println(String.format("Unknown Exception|exception: %s", e.getMessage()));
            }
            System.out.println("____________________________________________________________");
            System.out.println();
        }
        stdin.close();
    }

    public void markCommand(String userInput) throws FriedbergException {
        String[] words = userInput.split("\\s+");
        if (words.length != 2) {
            throw new FriedbergCommandException(
                    String.format("Unknown mark command given|bad mark input: %s", userInput),
                    "markCommand");
        }
        String command = words[0];
        int taskIndex = Integer.parseInt(words[1]) - 1;
        if (!this.checkValidTaskIndex(taskIndex)) {
            throw new FriedbergCommandException(
                    String.format("expected taskIndex to be in range of %d items", this.tasks.size()), "markCommand");
        }
        if (command.equals("mark")) {
            this.markTask(taskIndex);
        } else if (command.equals("unmark")) {
            this.unmarkTask(taskIndex);
        } else {
            throw new FriedbergCommandException(String.format("Unknown mark command given|command: %s", command),
                    "markCommand");
        }
        this.saveTasksToData();
    }

    public void markTask(int taskIndex) {
        Task task = this.tasks.get(taskIndex);
        task.mark();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task.renderTask());
    }

    public void unmarkTask(int taskIndex) {
        Task task = this.tasks.get(taskIndex);
        task.unmark();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task.renderTask());
    }

    /**
     * Returns true, if its a valid index, false otherwirse
     *
     * @param taskIndex task 0-index to check
     */
    private boolean checkValidTaskIndex(int taskIndex) {
        return 0 <= taskIndex && taskIndex < this.tasks.size();
    }

    public void listTasks() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, this.tasks.get(i).renderTask()));
        }
    }

    public Task createDeadline(String userInput) throws FriedbergException {
        userInput = userInput.replace("deadline ", "");
        String[] words = userInput.split("/by ");
        // For later error level
        if (words.length != 2) {
            throw new FriedbergUserInputException("deadline task expected to have /by");
        }
        String taskName = words[0].strip();
        String byDatetime = words[1].strip();
        Task task = new Deadline(taskName, byDatetime);
        this.tasks.add(task);
        return task;
    }

    public Task createTodo(String userInput) {
        userInput = userInput.replace("todo ", "");
        String taskName = userInput.strip();
        Task task = new ToDo(taskName);
        this.tasks.add(task);
        return task;
    }

    public Task createEvent(String userInput) throws FriedbergException {
        userInput = userInput.replace("event ", "");
        String[] words = userInput.split("/from ");
        if (words.length != 2) {
            System.out.println("Error adding event");
            throw new FriedbergUserInputException("event task expected to have /from");
        }
        String taskName = words[0].strip();
        words = words[1].split("/to ");
        if (words.length != 2) {
            System.out.println("Error adding event");
            throw new FriedbergUserInputException("event task expected to have /to");
        }
        String fromDatetime = words[0].strip();
        String toDatetime = words[1].strip();
        Task task = new Event(taskName, fromDatetime, toDatetime);
        this.tasks.add(task);
        return task;
    }

    public void addTaskCommand(String userInput) throws FriedbergException {
        String[] words = userInput.split(" ");
        String command = words[0];
        Task task;
        try {
            if (command.equals("deadline")) {
                task = this.createDeadline(userInput);
            } else if (command.equals("todo")) {
                task = this.createTodo(userInput);
            } else if (command.equals("event")) {
                task = this.createEvent(userInput);
            } else {
                throw new FriedbergCommandException(String.format("Unknown task type given|task: %s", command),
                        "addTask");
            }
        } catch (FriedbergException e) {
            throw e;
        }
        this.saveTasksToData();
        System.out.println("Got it. I've added this task:");
        System.out.println(task.renderTask());
        System.out.println(String.format("Now you have %d tasks in the list.", this.tasks.size()));
    }

    public void deleteCommand(String userInput) throws FriedbergException {
        String userInputTaskIndex = userInput.replace("delete ", "");
        try {
            int taskIndex = Integer.parseInt(userInputTaskIndex) - 1;
            if (!this.checkValidTaskIndex(taskIndex)) {
                throw new FriedbergCommandException(
                        String.format("expected taskIndex to be in range of %d items", this.tasks.size()),
                        "delete");
            }
            Task removedTask = this.tasks.remove(taskIndex);
            System.out.println("Noted. I've removed this task:");
            System.out.println(removedTask.renderTask());
            System.out.println(String.format("Now you have %d tasks in the list.", this.tasks.size()));

        } catch (FriedbergException e) {
            throw e;
        } catch (Exception e) {
            throw new FriedbergCommandException(String.format("Unkown delete command|userInput: %s", userInput),
                    "delete");
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
