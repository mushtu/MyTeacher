package ir.ac.ut.ece.moallem.api.endpoint;

import java.util.List;

import io.reactivex.Observable;
import ir.ac.ut.ece.moallem.api.model.Course;
import ir.ac.ut.ece.moallem.api.model.Section;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mushtu on 7/16/17.
 */

public interface StudentEndpoint {

    @GET("/students/{id}/sections")
    Observable<List<Section>> getCourseSections(@Path("id") long studentId, @Query("courseId") long courseId);

    @GET("/students/{id}/courses")
    Observable<List<Course>> getCourses(@Path("id") long studentId);
}
