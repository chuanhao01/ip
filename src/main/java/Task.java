public class Task {
    private String name;
    // True is done, false is not done
    private Boolean status;

    public Task(String name) {
        this.name = name;
        this.status = false;
    }

    /**
     * Marks the Task as done
     */
    public void mark() {
        this.status = true;
    }

    /**
     * Unmarks the Task
     */
    public void unmark(){
        this.status = false;
    }

    /**
     * Returns the formatted string to render this task on stdout
     *
     */
    public String renderTask() {
        return String.format("%s %s", this.status ? "[X]" : "[ ]", this.name);
    }
}
