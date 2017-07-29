package ir.ac.ut.ece.moallem.api.endpoint.mocks;

import ir.ac.ut.ece.moallem.api.endpoint.CourseEndpoint;
import ir.ac.ut.ece.moallem.api.model.Category;
import ir.ac.ut.ece.moallem.api.model.Course;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Query;
import retrofit2.mock.BehaviorDelegate;

/**
 * Created by mushtu on 7/4/17.
 */

public class MockedCourseEndpoint implements CourseEndpoint {

    private BehaviorDelegate<CourseEndpoint> delegate;

    public MockedCourseEndpoint(BehaviorDelegate<CourseEndpoint> delegate) {
        this.delegate = delegate;

    }

    @Override
    public Observable<List<Course>> getAll() {
        return delegate.returningResponse(MockDataProvider.createMockCourses()).getAll();
    }

    @Override
    public Observable<List<Course>> getMyCourse() {
        return delegate.returningResponse(new ArrayList<Course>()).getMyCourse();
    }

    @Override
    public Observable<List<Course>> getByCategory(@Query("category") String categoryName) {
        List<Course> courses = MockDataProvider.createMockCourses();
        List<Course> filtered = new ArrayList<>();
        for (Course course : courses) {
            if (course.getCategory() != null) {
                if (course.getCategory().getName().equals(categoryName))
                    filtered.add(course);
                else {
                    Category current = course.getCategory().getParent();
                    while (current != null) {
                        if (current.getName().equals(categoryName)) {
                            filtered.add(course);
                            break;
                        }
                        current = current.getParent();
                    }
                }
            }
        }

        return delegate.returningResponse(filtered).getByCategory(categoryName);
    }



/*    private List<Course> createMockCourses() {
        List<Course> courses = new ArrayList<>();
        Course hesaban = new Course("حسابان", "hesaban.jpg");
        hesaban.setId(1L);
        Course jabr = new Course("جبر و احتمال", "jabr.jpg");
        jabr.setId(2L);
        Course physics3 = new Course("فیزیک ۳", "physics_3.jpg");
        physics3.setId(3L);
        Course physics2 = new Course("فیزیک ۲", "physics_2.jpg");
        physics2.setId(4L);
        courses.add(hesaban);
        courses.add(jabr);
        courses.add(physics2);
        courses.add(physics3);
        return courses;
    }*/
}
