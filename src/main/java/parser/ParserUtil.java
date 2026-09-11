package parser;

import exception.FriedbergUserInputException;

/**
 * For shared methods used by the different commands
 */
public class ParserUtil {
    private ParserUtil() {
        // Prevent instantiation.
    }

    /**
     * Parses a string as an integer and wraps invalid input in a Friedberg user
     * input exception.
     *
     * @param str string to parse
     * @return parsed integer value
     * @throws FriedbergUserInputException if the string is not a valid integer
     */
    public static int parseInt(String str) throws FriedbergUserInputException {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new FriedbergUserInputException(String.format("Expected a valid integer input instead of %s", str));
        }
    }
}
