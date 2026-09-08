package parser;

public class ListCommand implements Command{
    @Override
    public boolean isCommand(String userInput) {
        return userInput.equals("list");
    }

    @Override
    public void execute(CommandContext commandContext) {
        commandContext.listTasks();
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
