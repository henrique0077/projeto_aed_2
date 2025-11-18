package exceptions;

import Enumerators.Message;

public class InvalidStudentTypeException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public InvalidStudentTypeException() {
        super(Message.INVALID_STUDENT_TYPE.get());
    }
}
