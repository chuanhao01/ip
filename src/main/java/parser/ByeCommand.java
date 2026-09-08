package parser;

public class ByeCommand implements Command {
    @Override
    public boolean isCommand(String userInput) {
        return userInput.equals("bye");
    }

    @Override
    public boolean isBye() {
        return true;
    }

    @Override
    public void execute(CommandContext commandContext) {
        System.out.println("Bye bye, see you again next time.");
    }
}
