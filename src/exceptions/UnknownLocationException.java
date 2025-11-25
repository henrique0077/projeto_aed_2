package exceptions;

import Enumerators.Message;

public class UnknownLocationException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public UnknownLocationException() {
        super(Message.UNKNOWN_LOCATION.get());
    }
}
