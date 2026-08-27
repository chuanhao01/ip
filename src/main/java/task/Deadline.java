package task;
public class Deadline extends Task{
    private String byDatetime;
    public Deadline(String name, String byDatetime){
        super(name);
        this.byDatetime = byDatetime;
    }

    @Override
    public String renderTask() {
        return String.format("[D]%s (by: %s)", super.renderTask(), this.byDatetime);
    }
}
