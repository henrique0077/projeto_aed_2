package exceptions;

import Enumerators.Message;

public class InvalidServiceCapacityException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public InvalidServiceCapacityException() {super(Message.INVALID_CAPACITY.get());}
}
