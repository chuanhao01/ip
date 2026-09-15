package parser;

/**
 * Handles the 'alist' command for Friedberg.
 * Displays all tasks in the archive list.
 */
public class ArchiveListCommand implements Command {
    @Override
    public boolean isCommand(String userInput) {
        return userInput.equals("alist");
    }

    @Override
    public String execute(String userInput, CommandContext commandContext) {
        return commandContext.renderArchivedTasks();
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
