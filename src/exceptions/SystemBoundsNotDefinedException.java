package exceptions;

import Enumerators.Message;

public class SystemBoundsNotDefinedException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public SystemBoundsNotDefinedException() {
        super(Message.SYSTEM_BOUNDS_NOT_DEFINED.get());
    }
}
