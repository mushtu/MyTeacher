package ir.ac.ut.ece.moallem.api.model;

/**
 * Created by mushtu on 7/7/17.
 */

public class Section extends BaseModel {

    private Teacher teacher;
    private Student student;
    private Course course;
    private SectionState state;

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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public SectionState getState() {
        return state;
    }

    public void setState(SectionState state) {
        this.state = state;
    }
}
