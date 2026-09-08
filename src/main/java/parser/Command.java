package parser;

public interface Command {
    /**
     * Given the user input, return true if the command is the one it should run
     * @param userInput
     * @return
     */
    boolean isCommand(String userInput);

    /**
     * Executes the command, calling further follow up actions on the commandContext if needed
     * @param commandContext
     */
    void execute(CommandContext commandContext);

    /**
     * Needed to indicate that the bye command ran to exit the chatbot
     * @return
     */
    boolean isBye();
}
