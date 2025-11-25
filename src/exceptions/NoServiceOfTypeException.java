package exceptions;

import Enumerators.Message;

public class NoServiceOfTypeException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public NoServiceOfTypeException() {
        super(Message.NO_SERVICES.get());
    }
}
