package ir.ac.ut.ece.moallem;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.ac.ut.ece.moallem.adapter.CategoryAdapter;
import ir.ac.ut.ece.moallem.adapter.CourseRecyclerViewAdapter;
import ir.ac.ut.ece.moallem.api.MoallemApi;
import ir.ac.ut.ece.moallem.api.endpoint.CourseEndpoint;
import ir.ac.ut.ece.moallem.api.endpoint.mocks.MockDataProvider;
import ir.ac.ut.ece.moallem.api.model.Category;
import ir.ac.ut.ece.moallem.api.model.Course;
import ir.ac.ut.ece.moallem.fragment.EducationsFragment;
import ir.ac.ut.ece.moallem.widgets.GridRecyclerView;

public class EducationsActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener, CourseRecyclerViewAdapter.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educations);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle("تحصیلی");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        }

        Fragment educationRootFragment = new EducationsFragment();
        Bundle args = new Bundle();
        args.putSerializable(EducationsFragment.ARG_CATEGORY, MockDataProvider.educationCategory());
        educationRootFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, educationRootFragment)
                .commit();

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
    public void onCategoryClick(Category category) {

        EducationsFragment fragment = new EducationsFragment();
        Bundle args = new Bundle();
        args.putSerializable(EducationsFragment.ARG_CATEGORY, category);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else
            super.onBackPressed();
    }

    @Override
    public void onItemClick(View view, final Course course) {
        if (getIntent().getBooleanExtra("REQUIRED_RESULT", false)) {
            String content = "آیا قصد تدریس درس " + course.getName() + " را دارید؟";
            new MaterialDialog.Builder(this)
                    .content(content)
                    .title("انتخاب درس")
                    .contentGravity(GravityEnum.START)
                    .titleGravity(GravityEnum.START)
                    .positiveText("بله")
                    .negativeText("خیر")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@android.support.annotation.NonNull MaterialDialog dialog, @android.support.annotation.NonNull DialogAction which) {

                            Intent intent = new Intent();
                            intent.putExtra("course", course);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    })
                    .build()
                    .show();
        } else {
            Intent teachers = new Intent(this, TeachersActivity.class);
            teachers.putExtra(TeachersActivity.EXTRA_COURSE_ID, course.getId());
            teachers.putExtra(TeachersActivity.EXTRA_COURSE_NAME, course.getName());
            startActivity(teachers);
        }

    }
}
