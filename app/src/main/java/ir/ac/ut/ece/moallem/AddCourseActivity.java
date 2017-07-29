package ir.ac.ut.ece.moallem;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.ac.ut.ece.moallem.api.MoallemApi;
import ir.ac.ut.ece.moallem.api.endpoint.TeacherEndpoint;
import ir.ac.ut.ece.moallem.api.endpoint.mocks.MockDataProvider;
import ir.ac.ut.ece.moallem.api.model.Course;

public class AddCourseActivity extends AppCompatActivity {


    private Spinner spinner;
    private List<Course> courses = new ArrayList<>();
    private Disposable addCourseDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courses.addAll(MockDataProvider.createMockCourses());
        spinner = (Spinner) findViewById(R.id.spinner_courses);
        //TODO get courses
        findViewById(R.id.btn_add_course).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSelectedCourseToTeacher();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        }
    }

    private void addSelectedCourseToTeacher() {

        if (spinner.getSelectedItemPosition() != AdapterView.INVALID_POSITION) {
            MoallemApi.getInstance()
                    .endpoint(TeacherEndpoint.class)
                    .addCourse(courses.get(spinner.getSelectedItemPosition()))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            addCourseDisposable = d;
                        }

                        @Override
                        public void onComplete() {
                            Intent intent = new Intent();
                            intent.putExtra("course", courses.get(spinner.getSelectedItemPosition()));
                            setResult(RESULT_OK, intent);
                            finish();
                            addCourseDisposable = null;
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            e.printStackTrace();
                            addCourseDisposable = null;
                        }
                    });

        }
    }
}
