package task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import common.Constants;
import exception.FriedbergException;
import exception.FriedbergInternalException;
import exception.FriedbergUserInputException;


public class Event extends Task {
    private LocalDate fromDatetime;
    private LocalDate toDatetime;

    public Event(String name, String fromDatetime, String toDatetime) throws FriedbergException {
        super(name);
        this.parseAndSetDatetimes(fromDatetime, toDatetime);
    }

    public Event(String name, TaskStatus status, String[] tokens) throws FriedbergException {
        super(name, status);
        if (tokens.length != 2) {
            throw new FriedbergInternalException("expected tokens to have length 2");
        }
        this.parseAndSetDatetimes(tokens[0], tokens[1]);
    }

    private void parseAndSetDatetimes(String fromDatetime, String toDatetime) throws FriedbergException {
        try {
            this.fromDatetime = LocalDate.parse(fromDatetime);
            this.toDatetime = LocalDate.parse(toDatetime);
        } catch (DateTimeParseException e) {
            throw new FriedbergUserInputException("Unable to parse datetime input, please use the yyyy-mm-dd format");
        }
    }

    @Override
    public String renderTask() {
        return String.format("[E]%s (from: %s to: %s)", super.renderTask(),
                this.fromDatetime.format(Constants.DATETIME_RENDER_FORMATTER),
                this.toDatetime.format(Constants.DATETIME_RENDER_FORMATTER));
    }

    @Override
    public String serialize() {
        return String.format("%s,E,%s,%s", super.serialize(), this.fromDatetime.toString(), this.toDatetime.toString());
    }
}
