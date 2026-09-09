import java.util.Scanner;
import exception.FriedbergException;
import parser.Command;
import parser.CommandContext;
import parser.CommandParser;

public class Friedberg {
    private static final String name = "Friedberg";
    private CommandContext commandContext;

    public static void main(String[] args) {
        try {
            Friedberg chatbot = new Friedberg();
            chatbot.run();
        } catch (FriedbergException e) {
            System.out.println(e.getMessage());
        }
    }

    public Friedberg() throws FriedbergException {
        this.commandContext = new CommandContext();
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
            System.out.println("____________________________________________________________");
            try {
                Command command = CommandParser.parse(userInput);
                command.execute(userInput, this.commandContext);
                if (command.isBye()) {
                    break;
                }
            } catch (FriedbergException e) {
                System.out.println(String.format("User Error using Friedberg: %s", e.getMessage()));
            }
            System.out.println("____________________________________________________________");
            System.out.println();
        }
        stdin.close();
    }
}
