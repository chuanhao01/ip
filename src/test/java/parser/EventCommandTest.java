package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import datahandler.DataHandler;
import exception.FriedbergCommandException;
import exception.FriedbergException;
import exception.FriedbergUserInputException;
import task.ToDo;

/**
 * Characterizes event execution so parsing can be extracted without changing behavior.
 */
class EventCommandTest {
    @TempDir
    Path tempDir;

    private DataHandler dataHandler;
    private CommandContext context;
    private final EventCommand command = new EventCommand();

    @BeforeEach
    void setUp() throws Exception {
        dataHandler = new DataHandler(tempDir, tempDir.resolve("tasks.txt"));
        context = new CommandContext(dataHandler);
        context.addTask(new ToDo("existing task"));
    }

    @Test
    void execute_validEvent_addsAndPersistsOnce() throws Exception {
        String response = command.execute("event meeting /from 2026-09-09 /to 2026-09-10", context);

        assertEquals("Got it. I've added this task:\n"
                + "[E][ ] meeting (from: 09/09/2026 (Wed) to: 10/09/2026 (Thu))\n"
                + "Now you have 2 tasks in the list.", response);
        assertEquals(2, context.getTasksSize());
        assertEquals("existing task,P,T\nmeeting,P,E,2026-09-09,2026-09-10", dataHandler.read());
        assertEquals(context.renderTasks(), new CommandContext(dataHandler).renderTasks());
    }

    @Test
    void execute_wrongCommandWord_preservesCommandError() throws Exception {
        assertRejectedWithoutChanges("events meeting /from 2026-09-09 /to 2026-09-10",
                new FriedbergCommandException("Expected event command but instead got|userInput: "
                        + "events meeting /from 2026-09-09 /to 2026-09-10", "event"));
    }

    @Test
    void execute_missingFrom_preservesDelimiterError() throws Exception {
        assertRejectedWithoutChanges("event meeting /to 2026-09-10",
                new FriedbergUserInputException("event task expected to have /from"));
    }

    @Test
    void execute_missingTo_preservesDelimiterError() throws Exception {
        assertRejectedWithoutChanges("event meeting /from 2026-09-09",
                new FriedbergUserInputException("event task expected to have /to"));
    }

    @Test
    void execute_invalidStartDate_preservesDateError() throws Exception {
        assertRejectedWithoutChanges("event meeting /from invalid /to 2026-09-10",
                new FriedbergUserInputException("Unable to parse datetime input, please use the yyyy-mm-dd format"));
    }

    @Test
    void execute_invalidEndDate_preservesDateError() throws Exception {
        assertRejectedWithoutChanges("event meeting /from 2026-09-09 /to invalid",
                new FriedbergUserInputException("Unable to parse datetime input, please use the yyyy-mm-dd format"));
    }

    /**
     * Checks that parsing failure leaves both tasks and saved data unchanged.
     *
     * @param input malformed event input
     * @param expectedError expected exception type and message
     * @throws Exception if temporary storage cannot be read
     */
    private void assertRejectedWithoutChanges(String input, FriedbergException expectedError) throws Exception {
        String originalTasks = context.renderTasks();
        String originalData = dataHandler.read();

        FriedbergException actualError = assertThrows(expectedError.getClass(), () -> command.execute(input, context));

        assertEquals(expectedError.getMessage(), actualError.getMessage());
        assertEquals(originalTasks, context.renderTasks());
        assertEquals(originalData, dataHandler.read());
    }
}
