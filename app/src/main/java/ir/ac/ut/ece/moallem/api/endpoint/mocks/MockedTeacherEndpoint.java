package ir.ac.ut.ece.moallem.api.endpoint.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import ir.ac.ut.ece.moallem.api.endpoint.TeacherEndpoint;
import ir.ac.ut.ece.moallem.api.model.Course;
import ir.ac.ut.ece.moallem.api.model.Section;
import ir.ac.ut.ece.moallem.api.model.SectionState;
import ir.ac.ut.ece.moallem.api.model.Student;
import ir.ac.ut.ece.moallem.api.model.TeachRequest;
import ir.ac.ut.ece.moallem.api.model.Teacher;
import ir.ac.ut.ece.moallem.api.model.UserReference;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.mock.BehaviorDelegate;

/**
 * Created by mushtu on 7/5/17.
 */

public class MockedTeacherEndpoint implements TeacherEndpoint {

    private BehaviorDelegate<TeacherEndpoint> delegate;

    public MockedTeacherEndpoint(BehaviorDelegate<TeacherEndpoint> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Observable<List<Teacher>> getCourseTeachers(@Query("courseId") long courseId) {
        return delegate.returningResponse(MockDataProvider.createTeachers(courseId)).getCourseTeachers(courseId);
    }

    @Override
    public Observable<List<TeachRequest>> getTeachingRequests(@Path("id") long teacherId, @Query("courseId") long courseId) {
        List<TeachRequest> requests = new ArrayList<>();
        if (teacherId == 1) {
            TeachRequest requestOne = new TeachRequest();
            requestOne.setCourse(MockDataProvider.findCourse(courseId));
            if (teacherId == 1)
                requestOne.setTeacher(MockDataProvider.teacherMojtaba());
            requestOne.setStudent(MockDataProvider.studentReza());
            requestOne.setId(1L);
            requestOne.setRequestTime(new Date().getTime());
            requestOne.setDescription("درخواست برای آموزش درس حسابان را دارم. شنبه دوشنبه ها ۳ بعدازظهر وقتم آزاد است.");
            requests.add(requestOne);
        }

        return delegate.returningResponse(requests).getTeachingRequests(teacherId, courseId);
    }

    @Override
    public Observable<List<TeachRequest>> getTeachingRequests(@Path("id") long teacherId) {
        List<TeachRequest> requests = new ArrayList<>();
        if (teacherId == 1) {
            TeachRequest requestOne = new TeachRequest();
            requestOne.setCourse(MockDataProvider.hesaban());
            if (teacherId == 1)
                requestOne.setTeacher(MockDataProvider.teacherMojtaba());
            requestOne.setStudent(MockDataProvider.studentReza());
            requestOne.setId(1L);
            requestOne.setRequestTime(new Date().getTime());
            requestOne.setDescription("درخواست برای آموزش درس حسابان را دارم. شنبه دوشنبه ها ۳ بعدازظهر وقتم آزاد است.");
            requests.add(requestOne);
        }

        return delegate.returningResponse(requests).getTeachingRequests(teacherId);
    }

    @Override
    public Completable addCourse(@Body Course course) {
        return delegate.returningResponse(null).addCourse(course);
    }

    @Override
    public Observable<List<Section>> getCourseSections(@Path("id") long teacherId, @Query("courseId") long courseId) {

        Section section = new Section();
        section.setStudent(MockDataProvider.studentReza());
        section.setCourse(MockDataProvider.findCourse(courseId));
        if (teacherId == MockDataProvider.teacherMojtaba().getId())
            section.setTeacher(MockDataProvider.teacherMojtaba());
        else if (teacherId == MockDataProvider.teacherAli().getId())
            section.setTeacher(MockDataProvider.teacherAli());
        else
            section.setTeacher(MockDataProvider.teacherSajad());
        section.setId(1L);
        section.setState(SectionState.ACCEPTED);

        List<Section> sections = new ArrayList<>();
        sections.add(section);
        return delegate.returningResponse(sections).getCourseSections(teacherId, courseId);
    }




 /*   private Course hesaban() {
        Course hesaban = new Course("حسابان", "hesaban.jpg");
        hesaban.setId(1L);
        return hesaban;
    }

    private Course physics3() {
        Course physics3 = new Course("فیزیک ۳", "physics_3.jpg");
        physics3.setId(3L);
        return physics3;
    }

    private Course jabr() {
        Course jabr = new Course("جبر و احتمال", "jabr.jpg");
        jabr.setId(2L);
        return jabr;
    }

    private Course physics2() {
        Course physics2 = new Course("فیزیک ۲", "physics_2.jpg");
        physics2.setId(4L);
        return physics2;
    }
*/
 /*   private List<Course> createMockCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(hesaban());
        courses.add(jabr());
        courses.add(physics2());
        courses.add(physics3());
        return courses;
    }*/


}
