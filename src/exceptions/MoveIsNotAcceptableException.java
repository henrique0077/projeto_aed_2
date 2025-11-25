package exceptions;

import Enumerators.Message;

public class MoveIsNotAcceptableException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public MoveIsNotAcceptableException() {
        super(Message.MOVE_NOT_ACCEPTABLE.get());
    }
}
