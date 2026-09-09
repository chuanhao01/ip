package parser;

import exception.FriedbergCommandException;
import exception.FriedbergException;
import task.Task;
import task.ToDo;

public class TodoCommand implements Command {
    @Override
    public boolean isCommand(String userInput) {
        return userInput.startsWith("todo");
    }

    @Override
    public void execute(String userInput, CommandContext commandContext) throws FriedbergException {
        String[] words = userInput.split(" ");
        if (!words[0].equals("todo")) {
            throw new FriedbergCommandException(
                    String.format("Expected todo command but instead got|userInput: %s", userInput), "todo");
        }
        String taskName = userInput.replace("todo ", "").strip();
        Task task = new ToDo(taskName);
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
