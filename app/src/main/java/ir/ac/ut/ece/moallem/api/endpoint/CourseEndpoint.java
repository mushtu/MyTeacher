package ir.ac.ut.ece.moallem.api.endpoint;

import io.reactivex.Completable;
import ir.ac.ut.ece.moallem.api.model.Course;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by mushtu on 7/1/17.
 */

public interface CourseEndpoint {

    @GET("/courses")
    Observable<List<Course>> getAll();

    @GET("/courses/me")
    Observable<List<Course>> getMyCourse();


    @GET("/courses")
    Observable<List<Course>> getByCategory(@Query("category") String categoryName);


}
