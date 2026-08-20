import java.util.Scanner;

public class Friedberg {
    private static final String name = "Friedberg";
    public static void main(String[] args) {
        Friedberg chatbot = new Friedberg();
        chatbot.greet();
        chatbot.echo();
    }
    public void greet(){
        String banner =
              "______     _          _ _                   \n"
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
    }
    public void echo(){
        Scanner stdin = new Scanner(System.in);  // Create a Scanner object
        boolean isBye = false;
        while (!isBye){
            String userInput = stdin.nextLine();
            userInput = userInput.strip();
            String output = userInput;
            if (userInput.equals("bye")){
                output = "Bye bye, see you again next time.";
                isBye = true;
            }
            System.out.println("____________________________________________________________");
            System.out.println(output);
            System.out.println("____________________________________________________________");
        }
        stdin.close();
    }
}
