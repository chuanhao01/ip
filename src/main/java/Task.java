public class Task {
    private String name;
    // True is done, false is not done
    private Boolean status;

    public Task(String name) {
        this.name = name;
        this.status = false;
    }

    public void flipStatus() {
        this.status = !this.status;
    }

    /**
     * Returns the formatted string to render this task on stdout
     *
     */
    public String renderTask() {
        return String.format("%s %s", this.status ? "[X]" : "[ ]", this.name);
    }
}
