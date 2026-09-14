package parser;

import exception.FriedbergCommandException;
import exception.FriedbergException;
import exception.FriedbergUserInputException;
import task.Event;
import task.Task;

/**
 * Handles the 'event' command for Friedberg.
 * Adds an event to the list of tasks.
 */
public class EventCommand implements Command {
    @Override
    public boolean isCommand(String userInput) {
        return userInput.startsWith("event");
    }

    @Override
    public String execute(String userInput, CommandContext commandContext) throws FriedbergException {
        String[] words = userInput.split(" ");
        if (!words[0].equals("event")) {
            throw new FriedbergCommandException(
                    String.format("Expected event command but instead got|userInput: %s", userInput), "event");
        }
        String[] descriptionAndDates = userInput.replace("event ", "").split("/from ");
        if (descriptionAndDates.length != 2) {
            throw new FriedbergUserInputException("event task expected to have /from");
        }
        String taskName = descriptionAndDates[0].strip();
        String[] startAndEndDates = descriptionAndDates[1].split("/to ");
        if (startAndEndDates.length != 2) {
            throw new FriedbergUserInputException("event task expected to have /to");
        }
        String fromDatetime = startAndEndDates[0].strip();
        String toDatetime = startAndEndDates[1].strip();
        Task task = new Event(taskName, fromDatetime, toDatetime);
        commandContext.addTask(task);
        return String.format("Got it. I've added this task:\n%s\nNow you have %d tasks in the list.",
                task.renderTask(), commandContext.getTasksSize());
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
