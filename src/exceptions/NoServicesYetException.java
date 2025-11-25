package exceptions;

import Enumerators.Message;

public class NoServicesYetException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public NoServicesYetException() {
        super(Message.NO_SERVICES_YET.get());
    }
}
