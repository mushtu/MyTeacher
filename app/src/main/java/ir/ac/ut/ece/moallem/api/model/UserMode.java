package ir.ac.ut.ece.moallem.api.model;

/**
 * Created by mushtu on 7/13/17.
 */

public enum UserMode {

    STUDENT(1),
    TEACHER(2),
    UNKNOWN(10);
    int value;

    UserMode(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static UserMode fromValue(int value) {
        if (value == 1)
            return STUDENT;
        else if (value == 2)
            return TEACHER;
        else return UNKNOWN;
    }
}
