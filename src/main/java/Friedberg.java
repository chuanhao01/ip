import java.util.Scanner;

public class Friedberg {
    private static final String name = "Friedberg";
    private Task[] tasks;
    private int totalNumOfTasks;

    public static void main(String[] args) {
        Friedberg chatbot = new Friedberg();
        chatbot.run();
    }

    public Friedberg() {
        this.tasks = new Task[100];
        this.totalNumOfTasks = 0;
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
            if (command.equals("bye")) {
                System.out.println("Bye bye, see you again next time.");
                isBye = true;
            } else if (command.equals("list")) {
                this.listTasks();
            } else {
                if (userInput.startsWith("mark") || userInput.startsWith("unmark")) {
                    this.markCommand(userInput);
                } else {
                    this.addTaskCommand(userInput);
                }
            }
            System.out.println("____________________________________________________________");
            System.out.println();
        }
        stdin.close();
    }

    public void markCommand(String userInput) {
        String[] words = userInput.split("\\s+");
        if (words.length != 2) {
            System.out.println("Unable to mark this task");
            return;
        }
        String command = words[0];
        int taskIndex = Integer.parseInt(words[1]) - 1;
        if (!this.checkValidTaskIndex(taskIndex)) {
            System.out.println("Unable to mark this task");
            return;
        }
        if (command.equals("mark")) {
            this.markTask(taskIndex);
        } else if (command.equals("unmark")) {
            this.unmarkTask(taskIndex);
        }
    }

    public void markTask(int taskIndex) {
        Task task = this.tasks[taskIndex];
        task.mark();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task.renderTask());
    }

    public void unmarkTask(int taskIndex) {
        Task task = this.tasks[taskIndex];
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
        return taskIndex < this.totalNumOfTasks;
    }

    public void listTasks() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < this.totalNumOfTasks; i++) {
            System.out.println(String.format("%d. %s", i + 1, this.tasks[i].renderTask()));
        }
    }

    public Task createDeadline(String userInput){
        userInput = userInput.replace("deadline ", "");
        String[] words = userInput.split("/by ");
        // For later error level
        if (words.length != 2){
            System.out.println("Error adding deadline");
            return null;
        }
        String taskName = words[0].strip();
        String byDatetime = words[1].strip();
        Task task = new Deadline(taskName, byDatetime);
        this.tasks[this.totalNumOfTasks] = task;
        this.totalNumOfTasks += 1;
        return task;
    }

    public Task createTodo(String userInput){
        userInput = userInput.replace("todo  ", "");
        String taskName = userInput.strip();
        Task task = new ToDo(taskName);
        this.tasks[this.totalNumOfTasks] = task;
        this.totalNumOfTasks += 1;
        return task;
    }
    public Task createEvent(String userInput){
        userInput = userInput.replace("event  ", "");
        String[] words = userInput.split("/from ");
        if (words.length != 2){
            System.out.println("Error adding event");
            return null;
        }
        String taskName = words[0].strip();
        words = words[1].split("/to ");
        if (words.length != 2){
            System.out.println("Error adding event");
            return null;
        }
        String fromDatetime = words[0].strip();
        String toDatetime = words[1].strip();
        Task task = new Event(taskName, fromDatetime, toDatetime);
        this.tasks[this.totalNumOfTasks] = task;
        this.totalNumOfTasks += 1;
        return task;
    }

    public void addTaskCommand(String userInput) {
        String[] words = userInput.split(" ");
        String command = words[0];
        Task task;
        if (command.equals("deadline")){
            task = this.createDeadline(userInput);
        } else if (command.equals("todo")){
            task = this.createTodo(userInput);
        } else if (command.equals("event")){
            task = this.createEvent(userInput);
        } else {
            System.out.println("Error adding task");
            return;
        }
        if (task == null){
            System.out.println("Error adding task");
            return;
        }
        System.out.println("Got it. I've added this task:");
        System.out.println(task.renderTask());
        System.out.println(String.format("Now you have %d tasks in the list.", this.totalNumOfTasks));
    }
}
