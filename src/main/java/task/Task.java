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

        public String serialize(){
            if(this == IN_PROGRESS){
                return "P";
            } else if(this == DONE){
                return "D";
            } else {
                throw new RuntimeException("Unknown Task Status should not be possible");
            }
        }
        public TaskStatus deserialize(String taskStatusString){
            if (taskStatusString == "P"){
                return IN_PROGRESS;
            } else if(taskStatusString == "D"){
                return DONE;
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
     * Alternative initializer for Task when name and status is known
     * @param name
     * @param status
     */
    public Task(String name, TaskStatus status){
        this.name = name;
        this.status = status;
    }

    // Getters
    public String getName(){
        return this.name;
    }
    public TaskStatus getStatus(){
        return this.status;
    }

    public String serialize(){
        // name,status
        return String.format("%s,%s", this.name, this.status);
    };
    public static Task deserialize(String taskString){
        return new ToDo("test");
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
