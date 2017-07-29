package ir.ac.ut.ece.moallem;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import ir.ac.ut.ece.moallem.adapter.SectionsAdapter;
import ir.ac.ut.ece.moallem.api.MoallemApi;
import ir.ac.ut.ece.moallem.api.endpoint.StudentEndpoint;
import ir.ac.ut.ece.moallem.api.endpoint.TeacherEndpoint;
import ir.ac.ut.ece.moallem.api.endpoint.mocks.MockDataProvider;
import ir.ac.ut.ece.moallem.api.model.Section;
import ir.ac.ut.ece.moallem.api.model.Teacher;
import ir.ac.ut.ece.moallem.utils.DialogUtil;

public class CourseActivity extends AppCompatActivity implements SectionsAdapter.OnCloseSectionListener {

    public final static String EXTRA_COURSE_ID = "ir.ac.ut.ece.moallem.extraCourseId";
    public final static String EXTRA_COURSE_NAME = "ir.ac.ut.ece.moallem.extraCourseName";
    public final static String EXTRA_IS_STUDENT = "ir.ac.ut.ece.moallem.extraIsStudent";
    private Long courseId;
    private String courseName;
    private SectionsAdapter sectionsAdapter;
    private Disposable loadSectionsDisposable;
    private Teacher currentTeacher = MockDataProvider.teacherMojtaba(); //TODO
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        sectionsAdapter = new SectionsAdapter();
        recyclerView.setAdapter(sectionsAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        sectionsAdapter.setOnCloseSectionListener(this);
        courseId = getIntent().getLongExtra(EXTRA_COURSE_ID, 1);
        courseName = getIntent().getStringExtra(EXTRA_COURSE_NAME);
        setTitle("کلاس های درس " + courseName);
        loadSections();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing() && loadSectionsDisposable != null && !loadSectionsDisposable.isDisposed())
            loadSectionsDisposable.dispose();
    }

    private void loadSections() {

        progressBar.setVisibility(View.VISIBLE);
        if (getIntent().getBooleanExtra(EXTRA_IS_STUDENT, false)) {

            MoallemApi.getInstance()
                    .endpoint(StudentEndpoint.class)
                    .getCourseSections(currentTeacher.getId(), courseId)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Section>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            loadSectionsDisposable = d;
                        }

                        @Override
                        public void onNext(@NonNull List<Section> sections) {
                            sectionsAdapter.addItems(sections);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            loadSectionsDisposable = null;
                            showNetErrorMsg();

                        }

                        @Override
                        public void onComplete() {
                            loadSectionsDisposable = null;
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        } else {

            MoallemApi.getInstance()
                    .endpoint(TeacherEndpoint.class)
                    .getCourseSections(currentTeacher.getId(), courseId)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Section>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            loadSectionsDisposable = d;
                        }

                        @Override
                        public void onNext(@NonNull List<Section> sections) {
                            sectionsAdapter.addItems(sections);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            loadSectionsDisposable = null;
                            showNetErrorMsg();

                        }

                        @Override
                        public void onComplete() {
                            loadSectionsDisposable = null;
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }


    }

    private void showNetErrorMsg() {
        DialogUtil.networkProblemDialogBuilder(this)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@android.support.annotation.NonNull MaterialDialog dialog, @android.support.annotation.NonNull DialogAction which) {
                        loadSections();
                    }
                }).build().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCloseSectionClick(final Section section) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("پایان کلاس")
                .negativeText("خیر")
                .positiveText("بلی")
                .content("در صورت اتمام کلاس این مورد از لیست پاک می شود و به دانش آموز پایان کلاس اطلاع داده می شود. آیا کلاس به پایان رسیده است؟ ")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@android.support.annotation.NonNull MaterialDialog dialog, @android.support.annotation.NonNull DialogAction which) {
                        sectionsAdapter.removeItem(section);
                    }
                })
                .build();
        dialog.show();

    }
}
