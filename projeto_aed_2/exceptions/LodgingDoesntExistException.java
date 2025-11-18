package exceptions;

import Enumerators.Message;

public class LodgingDoesntExistException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public LodgingDoesntExistException() {
        super(Message.LODGING_DOES_NOT_EXIST.get());
    }
}
