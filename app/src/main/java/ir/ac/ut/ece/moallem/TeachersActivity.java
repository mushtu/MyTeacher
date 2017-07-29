package ir.ac.ut.ece.moallem;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.ac.ut.ece.moallem.adapter.TeachersRecyclerAdapter;
import ir.ac.ut.ece.moallem.api.MoallemApi;
import ir.ac.ut.ece.moallem.api.endpoint.TeacherEndpoint;
import ir.ac.ut.ece.moallem.api.model.Teacher;
import ir.ac.ut.ece.moallem.utils.DialogUtil;

public class TeachersActivity extends AppCompatActivity implements TeachersRecyclerAdapter.OnItemClickListener {

    public final static String EXTRA_COURSE_ID = "ir.ac.ut.ece.moallem.extraCourseId";
    public final static String EXTRA_COURSE_NAME = "ir.ac.ut.ece.moallem.extraCourseName";
    private Long courseId;
    private String courseName;
    private TeachersRecyclerAdapter teachersAdapter;
    private Disposable teachersDisposable;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);
        Log.d("Mushtu", "onCreate: " + getClass().getSimpleName());
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        teachersAdapter = new TeachersRecyclerAdapter();
        recyclerView.setAdapter(teachersAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        teachersAdapter.setOnItemClickListener(this);
        courseId = getIntent().getLongExtra(EXTRA_COURSE_ID, 1);
        courseName = getIntent().getStringExtra(EXTRA_COURSE_NAME);
        getSupportActionBar().setTitle("مدرسین " + courseName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        }
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        loadTeachers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing() && teachersDisposable != null && !teachersDisposable.isDisposed())
            teachersDisposable.dispose();
    }

    private void loadTeachers() {

        progressBar.setVisibility(View.VISIBLE);
        MoallemApi.getInstance()
                .endpoint(TeacherEndpoint.class)
                .getCourseTeachers(courseId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Teacher>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        teachersDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull List<Teacher> teachers) {
                        teachersAdapter.addItems(teachers);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        teachersDisposable = null;
                        showNetErrorMsg();
                    }

                    @Override
                    public void onComplete() {
                        teachersDisposable = null;
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }

    private void showNetErrorMsg() {
        DialogUtil.networkProblemDialogBuilder(this)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@android.support.annotation.NonNull MaterialDialog dialog, @android.support.annotation.NonNull DialogAction which) {
                        loadTeachers();
                    }
                }).build().show();
    }

    @Override
    public void onItemClick(View view, Teacher teacher) {
        TeacherDetailActivity.navigate(this, view.findViewById(R.id.img_profile), teacher);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
