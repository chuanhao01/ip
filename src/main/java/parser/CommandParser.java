package parser;

import exception.FriedbergException;

/**
 * Parses user input into commands supported by Friedberg.
 */
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
        // Edit
        new MarkCommand(),
        new UnMarkCommand(),
        // Delete
        new DeleteCommand()
    };

    /**
     * Returns the command that handles the given user input.
     *
     * @param userInput input entered by the user
     * @return command that handles the user input
     * @throws FriedbergException if no supported command matches the input
     */
    public static Command parse(String userInput) throws FriedbergException {
        for (Command command : CommandParser.commands) {
            if (command.isCommand(userInput)) {
                return command;
            }
        }
        // Unknown command encountered
        throw new FriedbergException(String.format("Unknown command encountered from %s", userInput));
    }
}
