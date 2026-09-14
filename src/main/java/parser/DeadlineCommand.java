package parser;

import exception.FriedbergCommandException;
import exception.FriedbergException;
import exception.FriedbergUserInputException;
import task.Deadline;
import task.Task;

/**
 * Handles the 'deadline' command for Friedberg.
 * Adds a deadline to the list of tasks.
 */
public class DeadlineCommand implements Command {
    @Override
    public boolean isCommand(String userInput) {
        return userInput.startsWith("deadline");
    }

    @Override
    public String execute(String userInput, CommandContext commandContext) throws FriedbergException {
        String[] words = userInput.split(" ");
        if (!words[0].equals("deadline")) {
            throw new FriedbergCommandException(
                    String.format("Expected deadline command but instead got|userInput: %s", userInput), "deadline");
        }
        String[] descriptionAndDate = userInput.replace("deadline ", "").split("/by ");
        if (descriptionAndDate.length != 2) {
            throw new FriedbergUserInputException("deadline task expected to have /by");
        }
        String taskName = descriptionAndDate[0].strip();
        String byDatetime = descriptionAndDate[1].strip();
        Task task = new Deadline(taskName, byDatetime);
        commandContext.addTask(task);
        return TaskResponseFormatter.formatAddition(task, commandContext.getTasksSize());
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
