package ir.ac.ut.ece.moallem.api.model;

/**
 * Created by mushtu on 7/9/17.
 */

public class SectionApply extends BaseModel {

    private Section section;
    private Student student;
    private Long applyTime;

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Long getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Long applyTime) {
        this.applyTime = applyTime;
    }
}
