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
                String[] words = userInput.split("\\s+");
                // Check if its mark/unmark no.
                if (words.length == 2) {
                    command = words[0];
                    if (command.equals("mark")) {
                        this.markTask(Integer.parseInt(words[1]) - 1);
                    } else if (command.equals("unmark")) {
                        this.unmarkTask(Integer.parseInt(words[1]) - 1);
                    } else {
                        this.addTask(userInput);
                    }
                } else {
                    this.addTask(userInput);
                }
            }
            System.out.println("____________________________________________________________");
            System.out.println();
        }
        stdin.close();
    }

    public void markTask(int taskIndex) {
        if (this.checkValidTaskIndex(taskIndex)) {
            Task task = this.tasks[taskIndex];
            task.mark();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(task.renderTask());
        } else {
            System.out.println("Unable to mark this task");
        }
    }

    public void unmarkTask(int taskIndex){
        if (this.checkValidTaskIndex(taskIndex)) {
            Task task = this.tasks[taskIndex];
            task.unmark();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println(task.renderTask());
        } else {
            System.out.println("Unable to unmark this task");
        }
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

    public void addTask(String userInput) {
        Task task = new Task(userInput);
        this.tasks[this.totalNumOfTasks] = task;
        this.totalNumOfTasks += 1;
        System.out.println(String.format("added: %s", task.renderTask()));
    }
}
