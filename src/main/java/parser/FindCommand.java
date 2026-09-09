package parser;

import java.util.List;

import exception.FriedbergException;
import task.Task;

public class FindCommand implements Command {
    @Override
    public boolean isCommand(String userInput) {
        return userInput.startsWith("find");
    }

    @Override
    public void execute(String userInput, CommandContext commandContext) throws FriedbergException {
        String tasksNameFilter = userInput.replace("find ", "");
        List<Task> filteredTasks = commandContext.filterTasks(tasksNameFilter);
        if (filteredTasks.isEmpty()) {
            System.out.println("There are no tasks matching your search.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (Task task : filteredTasks) {
                System.out.println(task.renderTask());
            }
        }
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
