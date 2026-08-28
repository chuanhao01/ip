package task;

import java.util.Arrays;

import exception.FriedbergInternalException;

public abstract class Task {
    private String name;
    // True is done, false is not done
    private TaskStatus status;

    public Task(String name) {
        this.name = name;
        this.status = TaskStatus.IN_PROGRESS;
    }

    /**
     * Alternative initializer for Task when name and status is known
     *
     * @param name
     * @param status
     */
    public Task(String name, TaskStatus status) {
        this.name = name;
        this.status = status;
    }

    // Getters
    public String getName() {
        return this.name;
    }

    public TaskStatus getStatus() {
        return this.status;
    }

    public String serialize() {
        // name,status
        return String.format("%s,%s", this.name, this.status.serialize());
    };

    public static Task deserialize(String taskString) throws FriedbergInternalException {
        // csv format from the way a Task is serialize
        String[] tokens = taskString.split(",");
        if (tokens.length < 2) {
            throw new FriedbergInternalException("Expected deserialize task to have atleast 2 tokens");
        }
        String name = tokens[0];
        TaskStatus status = TaskStatus.deserialize(tokens[1]);
        String taskType = tokens[2];
        String[] otherTokens = Arrays.copyOfRange(tokens, 3, tokens.length);
        if (taskType.equals("T")) {
            return new ToDo(name, status);
        } else if (taskType.equals("D")) {
            return new Deadline(name, status, otherTokens);
        } else if (taskType.equals("E")) {
            return new Event(name, status, otherTokens);
        } else {
            throw new FriedbergInternalException(String.format("Unknown taskType: %s", taskType));
        }
    };

    /**
     * Marks the Task as done
     */
    public void mark() {
        this.status = TaskStatus.DONE;
    }

    /**
     * Unmarks the Task
     */
    public void unmark() {
        this.status = TaskStatus.IN_PROGRESS;
    }

    /**
     * Returns the formatted string to render this task on stdout
     *
     */
    public String renderTask() {
        return String.format("%s %s", this.status.renderTaskStatus(), this.name);
    }
}
