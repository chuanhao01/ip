package task;
public abstract class Task {
    private String name;
    // True is done, false is not done
    private TaskStatus status;

    private enum TaskStatus{
        IN_PROGRESS,
        DONE;

        public String renderTaskStatus(){
            if(this == IN_PROGRESS){
                return "[ ]";
            } else if(this == DONE){
                return "[X]";
            } else {
                throw new RuntimeException("Unknown Task Status should not be possible");
            }
        }
    }

    public Task(String name) {
        this.name = name;
        this.status = TaskStatus.IN_PROGRESS;
    }

    /**
     * Marks the Task as done
     */
    public void mark() {
        this.status = TaskStatus.DONE;
    }

    /**
     * Unmarks the Task
     */
    public void unmark(){
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
