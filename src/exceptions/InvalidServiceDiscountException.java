package exceptions;

import Enumerators.Message;

public class InvalidServiceDiscountException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public InvalidServiceDiscountException() {
        super(Message.INVALID_DISCOUNT.get());
    }
}
