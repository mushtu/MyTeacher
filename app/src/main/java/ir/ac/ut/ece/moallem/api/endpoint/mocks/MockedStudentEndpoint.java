package ir.ac.ut.ece.moallem.api.endpoint.mocks;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import ir.ac.ut.ece.moallem.api.endpoint.StudentEndpoint;
import ir.ac.ut.ece.moallem.api.model.Course;
import ir.ac.ut.ece.moallem.api.model.Section;
import ir.ac.ut.ece.moallem.api.model.SectionState;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.mock.BehaviorDelegate;

/**
 * Created by mushtu on 7/16/17.
 */

public class MockedStudentEndpoint implements StudentEndpoint {

    private BehaviorDelegate<StudentEndpoint> delegate;

    public MockedStudentEndpoint(BehaviorDelegate<StudentEndpoint> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Observable<List<Section>> getCourseSections(@Path("id") long studentId, @Query("courseId") long courseId) {
        Section section = new Section();
        section.setStudent(MockDataProvider.studentReza());
        section.setCourse(MockDataProvider.findCourse(courseId));
        section.setTeacher(MockDataProvider.teacherMojtaba());
        section.setId(1L);
        section.setState(SectionState.ACCEPTED);
        List<Section> sections = new ArrayList<>();
        sections.add(section);
        return delegate.returningResponse(sections).getCourseSections(studentId, courseId);
    }

    @Override
    public Observable<List<Course>> getCourses(@Path("id") long studentId) {
        List<Course> courses = new ArrayList<>();
        courses.add(MockDataProvider.hesaban());
        return delegate.returningResponse(courses).getCourses(studentId);
    }
}
