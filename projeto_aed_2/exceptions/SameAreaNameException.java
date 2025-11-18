package exceptions;

import Enumerators.Message;

public class SameAreaNameException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public SameAreaNameException() {
        super(Message.BOUND_ALREADY_EXISTS.get());
    }
}
