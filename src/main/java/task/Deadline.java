package task;

import exception.FriedbergInternalException;

public class Deadline extends Task {
    private String byDatetime;

    public Deadline(String name, String byDatetime) {
        super(name);
        this.byDatetime = byDatetime;
    }

    /**
     * Constructor to be used by Task.deserialize
     */
    public Deadline(String name, TaskStatus status, String[] tokens) throws FriedbergInternalException {
        super(name, status);
        if (tokens.length != 1) {
            throw new FriedbergInternalException("expected tokens to have length 1");
        }

        this.byDatetime = tokens[0];
    }

    @Override
    public String renderTask() {
        return String.format("[D]%s (by: %s)", super.renderTask(), this.byDatetime);
    }

    @Override
    public String serialize() {
        return String.format("%s,D,%s", super.serialize(), this.byDatetime);
    }
}
