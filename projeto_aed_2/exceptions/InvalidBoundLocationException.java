package exceptions;

import Enumerators.Message;

public class InvalidBoundLocationException extends RuntimeException {
    public InvalidBoundLocationException() {
        super(Message.INVALID_BOUNDS.get());
    }
}
