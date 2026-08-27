package task;
public class ToDo extends Task{
    public ToDo(String name){
        super(name);
    }
    @Override
    public String renderTask(){
        return String.format("[T]%s", super.renderTask());
    }
}
