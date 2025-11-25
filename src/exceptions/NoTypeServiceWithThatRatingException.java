package exceptions;

import Enumerators.Message;

public class NoTypeServiceWithThatRatingException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public NoTypeServiceWithThatRatingException() {
        super(Message.NO_SERVICES_WITH_AVERAGE.get());
    }
}
