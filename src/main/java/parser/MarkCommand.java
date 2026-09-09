package parser;

import exception.FriedbergCommandException;
import exception.FriedbergException;

public class MarkCommand implements Command {
    @Override
    public boolean isCommand(String userInput) {
        return userInput.startsWith("mark");
    }

    @Override
    public void execute(String userInput, CommandContext commandContext) throws FriedbergException {
        String[] words = userInput.split("\\s+");
        if (words.length != 2) {
            throw new FriedbergCommandException(
                    String.format("Unknown mark command given|bad mark input: %s", userInput),
                    "mark");
        }
        if (!words[0].equals("mark")) {
            throw new FriedbergCommandException(String.format("Expected mark command but instead got|userInput: %s", userInput), "mark");
        }
        int taskIndex = ParserUtil.parseInt(words[1]) - 1;
        commandContext.markTask(taskIndex);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(commandContext.renderTask(taskIndex));
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
