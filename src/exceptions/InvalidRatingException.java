package exceptions;

import Enumerators.Message;

public class InvalidRatingException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public InvalidRatingException() {
        super(Message.INVALID_EVALUATION.get());
    }
}
