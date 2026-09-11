package parser;

import exception.FriedbergException;

/**
 * Common interface to be implemented by all commands used in Friedberg
 */
public interface Command {
    /**
     * Checks whether this command can handle the given user input.
     *
     * @param userInput input entered by the user
     * @return true if this command should handle the input
     */
    boolean isCommand(String userInput);

    /**
     * Executes the command, calling follow-up actions on the command context if needed.
     *
     * @param userInput input entered by the user
     * @param commandContext context containing task and storage operations
     * @throws FriedbergException if the command cannot be executed
     */
    void execute(String userInput, CommandContext commandContext) throws FriedbergException;

    /**
     * Checks whether this command exits the chatbot.
     *
     * @return true if this command exits the chatbot
     */
    boolean isBye();
}
