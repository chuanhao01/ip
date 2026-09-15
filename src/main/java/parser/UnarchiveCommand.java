package parser;

import exception.FriedbergCommandException;
import exception.FriedbergException;
import task.Task;

/**
 * Handles the 'unarchive' command for Friedberg.
 * Moves a task from the archive list back into the normal task list.
 */
public class UnarchiveCommand implements Command {
    @Override
    public boolean isCommand(String userInput) {
        return userInput.startsWith("unarchive");
    }

    @Override
    public String execute(String userInput, CommandContext commandContext) throws FriedbergException {
        String[] words = userInput.split("\\s+");
        if (words.length != 2) {
            throw new FriedbergCommandException(
                    String.format("Unknown unarchive command given|bad unarchive input: %s", userInput),
                    "unarchive");
        }
        if (!words[0].equals("unarchive")) {
            throw new FriedbergCommandException(
                    String.format("Expected unarchive command but instead got|userInput: %s", userInput),
                    "unarchive");
        }
        int taskIndex = ParserUtil.parseInt(words[1]) - 1;
        Task unarchivedTask = commandContext.unarchiveTask(taskIndex);
        return String.format("Unarchived this task:\n%s\nNow you have %d tasks in the list and %d archived tasks.",
                unarchivedTask.renderTask(), commandContext.getTasksSize(), commandContext.getArchivedTasksSize());
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
