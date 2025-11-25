package exceptions;

import Enumerators.Message;

public class ItsStudentHomeException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public ItsStudentHomeException() {
        super(Message.ITS_STUDENT_HOME.get());
    }
}
