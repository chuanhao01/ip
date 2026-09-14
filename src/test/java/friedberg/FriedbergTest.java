package friedberg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import datahandler.DataHandler;
import parser.CommandContext;

/**
 * Tests for processing one input through the shared Friedberg backend.
 */
class FriedbergTest {
    /** Temporary directory supplied by JUnit for safe storage testing. */
    @TempDir
    Path tempDir;

    @Test
    void processInput_successiveCommands_shareTaskState() throws Exception {
        Friedberg friedberg = createFriedbergWithTempStorage();

        CommandResult addResult = friedberg.processInput("  todo read book  ");
        CommandResult listResult = friedberg.processInput("list");

        assertFalse(addResult.shouldExit());
        assertEquals("Got it. I've added this task:\n[T][ ] read book\nNow you have 1 tasks in the list.",
                addResult.message());
        assertEquals("Here are the tasks in your list:\n1. [T][ ] read book", listResult.message());
    }

    @Test
    void processInput_findMarkUnmarkDelete_returnExpectedMessages() throws Exception {
        Friedberg friedberg = createFriedbergWithTempStorage();
        friedberg.processInput("todo read book");
        friedberg.processInput("todo write code");

        assertEquals("Here are the matching tasks in your list:\n[T][ ] read book",
                friedberg.processInput("find read").message());
        assertEquals("Nice! I've marked this task as done:\n[T][X] read book",
                friedberg.processInput("mark 1").message());
        assertEquals("OK, I've marked this task as not done yet:\n[T][ ] read book",
                friedberg.processInput("unmark 1").message());
        assertEquals("Noted. I've removed this task:\n[T][ ] read book\nNow you have 1 tasks in the list.",
                friedberg.processInput("delete 1").message());
    }

    @Test
    void processInput_errorAndBye_returnExpectedFlags() throws Exception {
        Friedberg friedberg = createFriedbergWithTempStorage();

        CommandResult errorResult = friedberg.processInput("unknown");
        CommandResult byeResult = friedberg.processInput("bye");

        assertFalse(errorResult.shouldExit());
        assertTrue(errorResult.message().startsWith("User Error using Friedberg:"));
        assertTrue(byeResult.shouldExit());
        assertEquals("Bye bye, see you again next time.", byeResult.message());
    }

    @Test
    void processInput_validCommand_doesNotPrintToStdout() throws Exception {
        Friedberg friedberg = createFriedbergWithTempStorage();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stdout));
        try {
            friedberg.processInput("todo quiet task");
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("", stdout.toString());
    }

    private Friedberg createFriedbergWithTempStorage() throws Exception {
        Path dataFolderPath = tempDir.resolve("data");
        Path dataFilePath = dataFolderPath.resolve("friedberg.txt");
        DataHandler dataHandler = new DataHandler(dataFolderPath, dataFilePath);
        return new Friedberg(new CommandContext(dataHandler));
    }
}
