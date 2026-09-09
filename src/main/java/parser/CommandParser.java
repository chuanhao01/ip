package parser;

import exception.FriedbergException;

public class CommandParser {
    private static Command[] commands = {
        // Misc
        new ByeCommand(),
        // Tasks
        // Create
        new TodoCommand(),
        new DeadlineCommand(),
        new EventCommand(),
        // Read
        new ListCommand(),
        new FindCommand(),
        //Edit
        new MarkCommand(),
        new UnMarkCommand(),
        // Delete
        new DeleteCommand()
    };

    public static Command parse(String userInput) throws FriedbergException{
        for(Command command : CommandParser.commands){
            if (command.isCommand(userInput)){
                return command;
            }
        }
        // Unknown command encountered
        throw new FriedbergException(String.format("Unknown command encountered from %s", userInput));
    }
}
