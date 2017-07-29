package ir.ac.ut.ece.moallem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.ac.ut.ece.moallem.R;
import ir.ac.ut.ece.moallem.TeachersActivity;
import ir.ac.ut.ece.moallem.adapter.CategoryAdapter;
import ir.ac.ut.ece.moallem.adapter.CourseRecyclerViewAdapter;
import ir.ac.ut.ece.moallem.api.MoallemApi;
import ir.ac.ut.ece.moallem.api.endpoint.CourseEndpoint;
import ir.ac.ut.ece.moallem.api.endpoint.mocks.MockDataProvider;
import ir.ac.ut.ece.moallem.api.model.Category;
import ir.ac.ut.ece.moallem.api.model.Course;
import ir.ac.ut.ece.moallem.utils.DialogUtil;
import ir.ac.ut.ece.moallem.widgets.GridRecyclerView;

/**
 * Created by mushtu on 7/15/17.
 */

public class EducationsFragment extends Fragment {

    public static final String ARG_CATEGORY = "arg.category";
    private CategoryAdapter categoryAdapter;
    private CourseRecyclerViewAdapter courseRecyclerViewAdapter;
    private Disposable getCoursesDisposable;
    private TextView txtCategoriesTrack;
    private ProgressBar progressBar;
    private Category selectedCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_educations, container, false);
        RecyclerView categoryRecyclerView = (RecyclerView) view.findViewById(R.id.category_recycler);
        txtCategoriesTrack = (TextView) view.findViewById(R.id.txt_categories_track);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter((CategoryAdapter.OnCategoryClickListener) getActivity());
        categoryRecyclerView.setAdapter(categoryAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(categoryRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        categoryRecyclerView.addItemDecoration(dividerItemDecoration);
        GridRecyclerView courseRecyclerView = (GridRecyclerView) view.findViewById(R.id.courses_recycler);
        courseRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        courseRecyclerViewAdapter = new CourseRecyclerViewAdapter();
        courseRecyclerViewAdapter.setOnItemClickListener((CourseRecyclerViewAdapter.OnItemClickListener) getActivity());
        courseRecyclerView.setAdapter(courseRecyclerViewAdapter);

        selectedCategory = (Category) getArguments().getSerializable(ARG_CATEGORY);
        StringBuilder sb = new StringBuilder();
        sb.append(selectedCategory.getName());
        Category category = selectedCategory.getParent();
        while (category != null && !category.getName().equals("root")) {
            sb.insert(0, category.getName() + " > ");
            category = category.getParent();
        }
        txtCategoriesTrack.setText(sb.toString());
        categoryAdapter.addItems(selectedCategory.getSubCategories() != null ? selectedCategory.getSubCategories() : new ArrayList<Category>());
        getEducationItemsByCategory();

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelPrevIfExist();
    }

    private void cancelPrevIfExist() {
        if (getCoursesDisposable != null && !getCoursesDisposable.isDisposed())
            getCoursesDisposable.dispose();
    }

    private void getEducationItemsByCategory() {
        cancelPrevIfExist();
        progressBar.setVisibility(View.VISIBLE);
        MoallemApi.getInstance()
                .endpoint(CourseEndpoint.class)
                .getByCategory(selectedCategory.getName())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Course>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getCoursesDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull List<Course> courses) {
                        courseRecyclerViewAdapter.addItems(courses);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        getCoursesDisposable = null;
                        progressBar.setVisibility(View.GONE);
                        showNetErrorMsg();
                    }

                    @Override
                    public void onComplete() {
                        getCoursesDisposable = null;
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void showNetErrorMsg() {
        DialogUtil.networkProblemDialogBuilder(getContext())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@android.support.annotation.NonNull MaterialDialog dialog, @android.support.annotation.NonNull DialogAction which) {
                        getEducationItemsByCategory();
                    }
                })
                .build()
                .show();
    }
}
