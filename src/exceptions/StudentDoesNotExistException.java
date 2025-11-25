package exceptions;

import Enumerators.Message;

public class StudentDoesNotExistException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public StudentDoesNotExistException() {
        super(Message.STUDENT_DOES_NOT_EXIST.get());
    }
}
