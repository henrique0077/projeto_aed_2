package exceptions;

import Enumerators.Message;

public class BoundNameDoesntExistException extends RuntimeException {
   private static final long serialVersionUID = 0L;
    public BoundNameDoesntExistException() {
        super(Message.BOUND_NAME_DOES_NOT_EXISTS.get());
    }

}
