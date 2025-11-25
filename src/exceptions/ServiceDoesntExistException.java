package exceptions;

import Enumerators.Message;

public class ServiceDoesntExistException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public ServiceDoesntExistException() {
        super(Message.SERVICE_DOES_NOT_EXIST.get());
    }
}
