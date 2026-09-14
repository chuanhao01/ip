package parser;

/**
 * Handles the 'bye' Command for Friedberg.
 */
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
    public String execute(String userInput, CommandContext commandContext) {
        return "Bye bye, see you again next time.";
    }
}
