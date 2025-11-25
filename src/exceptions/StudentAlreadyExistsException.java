package exceptions;

import Enumerators.Message;

public class StudentAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public StudentAlreadyExistsException() {
        super(Message.STUDENT_ALREADY_EXISTS.get());
    }
}
