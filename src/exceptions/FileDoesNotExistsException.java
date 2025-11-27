package exceptions;

import Enumerators.Message;

public class FileDoesNotExistsException extends Throwable {
    public FileDoesNotExistsException() {
        super(Message.BOUND_NAME_DOES_NOT_EXISTS.get());
    }
}
