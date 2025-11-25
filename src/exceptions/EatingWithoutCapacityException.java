package exceptions;

import Enumerators.Message;

public class EatingWithoutCapacityException extends RuntimeException {
    public EatingWithoutCapacityException() {
        super(Message.EATING_IS_FULL.get());
    }
}
