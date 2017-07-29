package ir.ac.ut.ece.moallem;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import ir.ac.ut.ece.moallem.adapter.TeachRequestsAdapter;
import ir.ac.ut.ece.moallem.api.MoallemApi;
import ir.ac.ut.ece.moallem.api.endpoint.TeacherEndpoint;
import ir.ac.ut.ece.moallem.api.endpoint.mocks.MockDataProvider;
import ir.ac.ut.ece.moallem.api.model.Course;
import ir.ac.ut.ece.moallem.api.model.TeachRequest;
import ir.ac.ut.ece.moallem.api.model.Teacher;
import ir.ac.ut.ece.moallem.utils.DialogUtil;

public class TeachRequestsActivity extends AppCompatActivity implements TeachRequestsAdapter.OnItemClickListener {

    private final int ACCEPT_TEACH_REQUEST = 100;
    private Disposable teachRequestsDisposable;
    private Teacher currentTeacher = MockDataProvider.teacherMojtaba(); //TODO
    private TeachRequestsAdapter requestsAdapter;
    private Course selectedCourse;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_requests);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle("درخواست های آموزش");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        requestsAdapter = new TeachRequestsAdapter();
        recyclerView.setAdapter(requestsAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        requestsAdapter.setOnItemClickListener(this);
        //selectedCourse = (Course) getIntent().getSerializableExtra("course");
        getTeachRequests();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        }
    }

    private void getTeachRequests() {
        progressBar.setVisibility(View.VISIBLE);
        MoallemApi.getInstance()
                .endpoint(TeacherEndpoint.class)
                .getTeachingRequests(currentTeacher.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TeachRequest>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        teachRequestsDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull List<TeachRequest> teachRequests) {
                        requestsAdapter.addItems(teachRequests);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        teachRequestsDisposable = null;
                        showNetErrorMsg();
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                        teachRequestsDisposable = null;
                    }
                });
    }

    private void showNetErrorMsg() {
        DialogUtil.networkProblemDialogBuilder(this)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@android.support.annotation.NonNull MaterialDialog dialog, @android.support.annotation.NonNull DialogAction which) {
                        getTeachRequests();
                    }
                }).build().show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing() && teachRequestsDisposable != null && !teachRequestsDisposable.isDisposed())
            teachRequestsDisposable.dispose();
    }


    @Override
    public void onItemClick(View view, TeachRequest request) {
        TeachRequestDetailActivity.navigateForResult(this, view.findViewById(R.id.img_profile), request, ACCEPT_TEACH_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ACCEPT_TEACH_REQUEST) {
            requestsAdapter.removeItem(data.getLongExtra("request_id", 0));
        } else super.onActivityResult(requestCode, resultCode, data);
    }
}
