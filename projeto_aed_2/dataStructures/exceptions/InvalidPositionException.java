package dataStructures.exceptions;

import Enumerators.Message;

public class InvalidPositionException extends RuntimeException{
    public InvalidPositionException() {
        super(Message.INVALID_LOCATION.get());
    }
}

