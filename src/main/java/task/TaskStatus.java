package task;

public enum TaskStatus {
    IN_PROGRESS,
    DONE;

    public String renderTaskStatus() {
        if (this == IN_PROGRESS) {
            return "[ ]";
        } else if (this == DONE) {
            return "[X]";
        } else {
            throw new RuntimeException("Unknown Task Status should not be possible");
        }
    }

    public String serialize() {
        if (this == IN_PROGRESS) {
            return "P";
        } else if (this == DONE) {
            return "D";
        } else {
            throw new RuntimeException("Unknown Task Status should not be possible");
        }
    }

    public TaskStatus deserialize(String taskStatusString) {
        if (taskStatusString == "P") {
            return IN_PROGRESS;
        } else if (taskStatusString == "D") {
            return DONE;
        } else {
            throw new RuntimeException("Unknown Task Status should not be possible");
        }
    }

}
