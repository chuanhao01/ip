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
    public void execute(String userInput, CommandContext commandContext) throws FriedbergException {
        String[] words = userInput.split(" ");
        if (!words[0].equals("deadline")) {
            throw new FriedbergCommandException(
                    String.format("Expected deadline command but instead got|userInput: %s", userInput), "deadline");
        }
        words = userInput.replace("deadline ", "").split("/by ");
        // For later error level
        if (words.length != 2) {
            throw new FriedbergUserInputException("deadline task expected to have /by");
        }
        String taskName = words[0].strip();
        String byDatetime = words[1].strip();
        Task task = new Deadline(taskName, byDatetime);
        commandContext.addTask(task);
        System.out.println("Got it. I've added this task:");
        System.out.println(task.renderTask());
        System.out.printf("Now you have %d tasks in the list.%n", commandContext.getTasksSize());
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
