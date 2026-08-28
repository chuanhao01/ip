package task;
public class Event extends Task{
    private String fromDatetime;
    private String toDatetime;
    public Event(String name, String fromDatetime, String toDatetime){
        super(name);
        this.fromDatetime = fromDatetime;
        this.toDatetime = toDatetime;
    }
    public Event(String name, TaskStatus status, String fromDatetime, String toDatetime){
        this(name, fromDatetime, toDatetime);
        super(name, status);
    }
    @Override
    public String renderTask() {
        return String.format("[E]%s (from: %s to: %s)", super.renderTask(), this.fromDatetime, this.toDatetime);
    }

    @Override
    public String serialize() {
        return String.format("%s,E,%s,%s,", super.serialize(), this.fromDatetime, this.toDatetime);
    }
}
