package exceptions;

import Enumerators.Message;

public class ServiceAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public ServiceAlreadyExistsException() {
        super(Message.ALREADY_EXISTS.get());
    }
}
