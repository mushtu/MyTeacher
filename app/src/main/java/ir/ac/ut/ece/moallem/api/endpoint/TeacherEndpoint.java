package ir.ac.ut.ece.moallem.api.endpoint;

import io.reactivex.Completable;
import ir.ac.ut.ece.moallem.api.model.Course;
import ir.ac.ut.ece.moallem.api.model.Section;
import ir.ac.ut.ece.moallem.api.model.TeachRequest;
import ir.ac.ut.ece.moallem.api.model.Teacher;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mushtu on 7/5/17.
 */

public interface TeacherEndpoint {

    @GET("/teachers")
    Observable<List<Teacher>> getCourseTeachers(@Query("courseId") long courseId);

    @GET("/teachers/{id}/requests")
    Observable<List<TeachRequest>> getTeachingRequests(@Path("id") long teacherId, @Query("courseId") long courseId);

    @GET("/teachers/{id}/requests")
    Observable<List<TeachRequest>> getTeachingRequests(@Path("id") long teacherId);

    @PUT("/teachers/{id}/courses")
    Completable addCourse(@Body Course course);

    @GET("/teachers/{id}/sections")
    Observable<List<Section>> getCourseSections(@Path("id") long teacherId, @Query("courseId") long courseId);
}
