package parser;

import exception.FriedbergCommandException;
import exception.FriedbergException;
import exception.FriedbergUserInputException;
import task.Event;
import task.Task;

/**
 * Handles the 'event' command for Friedberg
 * To add an event to the list of tasks
 */
public class EventCommand implements Command {
    @Override
    public boolean isCommand(String userInput) {
        return userInput.startsWith("event");
    }

    @Override
    public void execute(String userInput, CommandContext commandContext) throws FriedbergException {
        String[] words = userInput.split(" ");
        if (!words[0].equals("event")) {
            throw new FriedbergCommandException(
                    String.format("Expected event command but instead got|userInput: %s", userInput), "event");
        }
        words = userInput.replace("event ", "").split("/from ");
        if (words.length != 2) {
            System.out.println("Error adding event");
            throw new FriedbergUserInputException("event task expected to have /from");
        }
        String taskName = words[0].strip();
        words = words[1].split("/to ");
        if (words.length != 2) {
            System.out.println("Error adding event");
            throw new FriedbergUserInputException("event task expected to have /to");
        }
        String fromDatetime = words[0].strip();
        String toDatetime = words[1].strip();
        Task task = new Event(taskName, fromDatetime, toDatetime);
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
