/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Enumerators;

import exceptions.InvalidStudentTypeException;

public enum StudentType {
    OUTGOING("OUTGOING"),
    BOOKISH("BOOKISH"),
    THRIFTY("THRIFTY"),;

    private String studentType;

    public static StudentType fromString(String type) throws InvalidStudentTypeException {
        for (StudentType t : values()) {
            if (t.name().equalsIgnoreCase(type)) {
                return t;
            }
        }
        throw new InvalidStudentTypeException();
    }

    StudentType(String studentType) {
        this.studentType = studentType;
    }

    public String get() {
        return studentType;
    }
}
