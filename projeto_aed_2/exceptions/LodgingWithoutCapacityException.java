package exceptions;

import Enumerators.Message;

public class LodgingWithoutCapacityException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public LodgingWithoutCapacityException() {
        super(Message.LODGING_IS_FULL.get());
    }
}
