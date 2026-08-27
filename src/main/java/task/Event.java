package task;
public class Event extends Task{
    private String fromDatetime;
    private String toDatetime;
    public Event(String name, String fromDatetime, String toDatetime){
        super(name);
        this.fromDatetime = fromDatetime;
        this.toDatetime = toDatetime;
    }
    @Override
    public String renderTask() {
        return String.format("[E]%s (from: %s to: %s)", super.renderTask(), this.fromDatetime, this.toDatetime);
    }

    @Override
    public String serialize() {
        return String.format("E,%s,%s,", null)
    }
}
