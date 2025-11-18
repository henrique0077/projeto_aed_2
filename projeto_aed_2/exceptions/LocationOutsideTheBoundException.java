package exceptions;

import Enumerators.Message;

public class LocationOutsideTheBoundException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public LocationOutsideTheBoundException() {
        super(Message.INVALID_BOUNDS.get());
    }
}
