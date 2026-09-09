package parser;

import exception.FriedbergException;
import task.Task;

public class DeleteCommand implements Command{
    @Override
    public boolean isCommand(String userInput) {
        return userInput.startsWith("delete");
    }

    @Override
    public void execute(String userInput, CommandContext commandContext) throws FriedbergException {
        int taskIndex = ParserUtil.parseInt(userInput.replace("delete ", "")) - 1;
        Task removedTask = commandContext.removeTask(taskIndex) ;
        System.out.println("Noted. I've removed this task:");
        System.out.println(removedTask.renderTask());
        System.out.println(String.format("Now you have %d tasks in the list.", commandContext.getTasksSize()));
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
