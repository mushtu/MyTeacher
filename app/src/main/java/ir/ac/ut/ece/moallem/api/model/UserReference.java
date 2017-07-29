package ir.ac.ut.ece.moallem.api.model;

/**
 * Created by mushtu on 7/7/17.
 */

public class UserReference extends BaseModel {
    private Teacher teacher;
    private Student student;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
