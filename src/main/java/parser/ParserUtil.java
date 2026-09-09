package parser;

import exception.FriedbergUserInputException;

public class ParserUtil {
    /**
     * Wrapper utility method to parse a string to an integer, help to wrap and
     * return the correct FriedbergUserInputException
     *
     * @param str
     * @return
     */
    public static int parseInt(String str) throws FriedbergUserInputException {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new FriedbergUserInputException(String.format("Expected a valid integer input instead of %s", str));
        }
    }
}
