package exceptions;

import Enumerators.Message;

public class LocationIsntValidPlaceException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public LocationIsntValidPlaceException() {
        super(Message.LOCATION_ISNT_VALID_SERVICE.get());
    }
}
