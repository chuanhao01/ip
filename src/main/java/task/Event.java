package task;

import exception.FriedbergInternalException;

public class Event extends Task {
    private String fromDatetime;
    private String toDatetime;

    public Event(String name, String fromDatetime, String toDatetime) {
        super(name);
        this.fromDatetime = fromDatetime;
        this.toDatetime = toDatetime;
    }

    public Event(String name, TaskStatus status, String[] tokens) throws FriedbergInternalException {
        super(name, status);
        if (tokens.length != 2) {
            throw new FriedbergInternalException("expected tokens to have length 2");
        }
        this.fromDatetime = tokens[0];
        this.toDatetime = tokens[1];
    }

    @Override
    public String renderTask() {
        return String.format("[E]%s (from: %s to: %s)", super.renderTask(), this.fromDatetime, this.toDatetime);
    }

    @Override
    public String serialize() {
        return String.format("%s,E,%s,%s", super.serialize(), this.fromDatetime, this.toDatetime);
    }
}
