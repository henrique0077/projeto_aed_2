package exceptions;

import Enumerators.Message;

public class AlreadyThereException extends RuntimeException {
    public AlreadyThereException() {
        super(Message.ALREADY_THERE.get());
    }
}
