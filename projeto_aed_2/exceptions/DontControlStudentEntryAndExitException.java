package exceptions;

import Enumerators.Message;

public class DontControlStudentEntryAndExitException extends RuntimeException {
    public DontControlStudentEntryAndExitException() {
        super(Message.DONT_CONTROL_STUDENT_ENTRY_AND_EXIT.get());
    }
}
