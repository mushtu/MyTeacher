/*
 * Copyright (C) 2015 Antonio Leiva
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ir.ac.ut.ece.moallem;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.ac.ut.ece.moallem.adapter.CourseRecyclerViewAdapter;
import ir.ac.ut.ece.moallem.api.MoallemApi;
import ir.ac.ut.ece.moallem.api.endpoint.CourseEndpoint;
import ir.ac.ut.ece.moallem.api.model.Course;
import ir.ac.ut.ece.moallem.config.AppConfig;
import ir.ac.ut.ece.moallem.picasso.CircleTransform;
import ir.ac.ut.ece.moallem.utils.DialogUtil;


public class TeacherHomeActivity extends AppCompatActivity implements CourseRecyclerViewAdapter.OnItemClickListener {

    private final String TAG = "TeacherHomeActivity";
    private final int ADD_COURSE_REQ = 100;
    public static final String AVATAR_URL = "http://lorempixel.com/200/200/people/1/";
    private CourseRecyclerViewAdapter coursesAdapter = new CourseRecyclerViewAdapter();
    private Disposable courcesDisposable;
    private DrawerLayout drawerLayout;
    private View content;
    private RecyclerView recyclerView;
    private NavigationView navigationView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        initRecyclerView();
        //initFab();
        initToolbar();
        setupDrawerLayout();

        content = findViewById(R.id.content);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        final ImageView avatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.avatar);
        Picasso.with(this).load(AVATAR_URL).transform(new CircleTransform()).into(avatar);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setRecyclerAdapter(recyclerView);
        }

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCourseSelectionView();
            }
        });

        getMyCourses();
    }

    private void showCourseSelectionView() {
        Intent intent = new Intent(this, CategoriesActivity.class);
        intent.putExtra("prev.activity", getClass().getSimpleName());
        startActivityForResult(intent, ADD_COURSE_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_COURSE_REQ && resultCode == RESULT_OK) {
            Course course = (Course) data.getSerializableExtra("course");
            coursesAdapter.addItem(course);

        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        setRecyclerAdapter(recyclerView);
        recyclerView.scheduleLayoutAnimation();
    }


    private void getMyCourses() {
        if (courcesDisposable == null)
            progressBar.setVisibility(View.VISIBLE);
        MoallemApi.getInstance().endpoint(CourseEndpoint.class)
                .getMyCourse()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Course>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        courcesDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull List<Course> courses) {
                        coursesAdapter.addItems(courses);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        courcesDisposable = null;
                        showNetErrorMsg();
                    }

                    @Override
                    public void onComplete() {
                        courcesDisposable = null;
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void showNetErrorMsg() {
        DialogUtil.networkProblemDialogBuilder(this)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@android.support.annotation.NonNull MaterialDialog dialog, @android.support.annotation.NonNull DialogAction which) {
                        getMyCourses();
                    }
                })
                .build()
                .show();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void setRecyclerAdapter(RecyclerView recyclerView) {
        coursesAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(coursesAdapter);
    }

/*    private void initFab() {
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(content, "FAB Clicked", Snackbar.LENGTH_SHORT).show();
            }
        });
    }*/

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.drawer_requests:
                        showTeachRequestsView();
                        break;
                    case R.id.drawer_logout:
                        performLogout();
                        break;
                }
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void performLogout() {
        AppConfig.getInstance(this)
                .clear();
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();

    }

    private void showTeachRequestsView() {
        Intent intent = new Intent(this, TeachRequestsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, Course course) {
        Intent intent = new Intent(this, CourseActivity.class);
        intent.putExtra(CourseActivity.EXTRA_COURSE_ID, course.getId());
        intent.putExtra(CourseActivity.EXTRA_COURSE_NAME, course.getName());
        intent.putExtra(CourseActivity.EXTRA_IS_STUDENT, true);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause...");
        if (isFinishing()) {
            Log.d(TAG, "onPause: is finishing...");
            releaseResources();
        }
        super.onPause();
    }

    private void releaseResources() {
        if (courcesDisposable != null && !courcesDisposable.isDisposed())
            courcesDisposable.dispose();
    }
}
