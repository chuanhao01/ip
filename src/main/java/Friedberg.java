import java.util.Scanner;

public class Friedberg {
    private static final String name = "Friedberg";
    private String[] items;
    private int totalNumOfItems;

    public static void main(String[] args) {
        Friedberg chatbot = new Friedberg();
        chatbot.greet();
        chatbot.command();
    }

    public Friedberg() {
        this.items = new String[100];
        this.totalNumOfItems = 0;
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
                for (int i = 0; i < this.totalNumOfItems; i++) {
                    System.out.println(String.format("%d. %s", i + 1, this.items[i]));
                }
            } else {
                addToList(userInput);
            }
            System.out.println("____________________________________________________________");
            System.out.println();
        }
        stdin.close();
    }

    public void addToList(String userInput) {
        this.items[this.totalNumOfItems] = userInput;
        this.totalNumOfItems += 1;
        System.out.println(String.format("added: %s", userInput));
    }
}
