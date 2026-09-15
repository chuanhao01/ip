package parser;

import exception.FriedbergCommandException;
import exception.FriedbergException;
import task.Task;

/**
 * Handles the 'archive' command for Friedberg.
 * Moves a task from the normal task list into the archive list.
 */
public class ArchiveCommand implements Command {
    @Override
    public boolean isCommand(String userInput) {
        return userInput.startsWith("archive");
    }

    @Override
    public String execute(String userInput, CommandContext commandContext) throws FriedbergException {
        String[] words = userInput.split("\\s+");
        if (words.length != 2) {
            throw new FriedbergCommandException(
                    String.format("Unknown archive command given|bad archive input: %s", userInput),
                    "archive");
        }
        if (!words[0].equals("archive")) {
            throw new FriedbergCommandException(
                    String.format("Expected archive command but instead got|userInput: %s", userInput),
                    "archive");
        }
        int taskIndex = ParserUtil.parseInt(words[1]) - 1;
        Task archivedTask = commandContext.archiveTask(taskIndex);
        return String.format("Archived this task:\n%s\nNow you have %d tasks in the list and %d archived tasks.",
                archivedTask.renderTask(), commandContext.getTasksSize(), commandContext.getArchivedTasksSize());
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
