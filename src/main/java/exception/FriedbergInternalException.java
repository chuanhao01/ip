package exception;

public class FriedbergInternalException extends FriedbergException{
    public FriedbergInternalException(String message){
        super(String.format("internal_error|%s", message));
    }

}
