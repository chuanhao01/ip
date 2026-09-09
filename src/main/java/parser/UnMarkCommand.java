package parser;

import exception.FriedbergCommandException;
import exception.FriedbergException;

public class UnMarkCommand implements Command{
    @Override
    public boolean isCommand(String userInput) {
        return userInput.startsWith("unmark");
    }

    @Override
    public void execute(String userInput, CommandContext commandContext) throws FriedbergException {
        String[] words = userInput.split("\\s+");
        if (words.length != 2) {
            throw new FriedbergCommandException(
                    String.format("Unknown unmark command given|bad mark input: %s", userInput),
                    "unmark");
        }
        if(!words[0].equals("unmark")
        ){
            throw new FriedbergCommandException(String.format("Expected unmark command but instead got|userInput: %s", userInput), "unmark");
        }
        int taskIndex = ParserUtil.parseInt(words[1]) - 1;
        commandContext.unmarkTask(taskIndex);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(commandContext.renderTask(taskIndex));
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
