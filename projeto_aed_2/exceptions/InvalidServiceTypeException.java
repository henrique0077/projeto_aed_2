package exceptions;

import Enumerators.Message;

/*
Exception thrown when given an invalid service type is provided.
 */
public class InvalidServiceTypeException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public InvalidServiceTypeException() {
        super(Message.INVALID_SERVICE_TYPE.get());
    }
}
