package exceptions;

import Enumerators.Message;

public class InvalidStarsException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public InvalidStarsException() {
        super(Message.INVALID_STARS.get());
    }
}
