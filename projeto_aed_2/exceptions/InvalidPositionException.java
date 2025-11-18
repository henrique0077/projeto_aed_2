package exceptions;

import Enumerators.Message;

public class InvalidPositionException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public InvalidPositionException() {
        super(Message.INVALID_LOCATION.get());
    }
}
