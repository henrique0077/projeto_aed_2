package exceptions;

import Enumerators.Message;

public class InvalidOrderException extends RuntimeException {
    private static final long serialVersionUID = 0L;
    public InvalidOrderException() {
        super(Message.INVALID_ORDER.get());
    }
}
