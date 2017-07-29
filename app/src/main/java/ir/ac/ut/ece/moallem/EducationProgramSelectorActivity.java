package ir.ac.ut.ece.moallem;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ProgressBar;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ir.ac.ut.ece.moallem.adapter.EducationProgramAdapter;
import ir.ac.ut.ece.moallem.api.endpoint.mocks.MockDataProvider;
import ir.ac.ut.ece.moallem.api.model.EducationProgram;

/**
 * Created by mushtu on 7/13/17.
 */

public class EducationProgramSelectorActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, EducationProgramAdapter.OnItemClickListener, SortedListAdapter.Callback {

    private static final Comparator<EducationProgram> COMPARATOR = new SortedListAdapter.ComparatorBuilder<EducationProgram>()
            .setOrderForModel(EducationProgram.class, new Comparator<EducationProgram>() {
                @Override
                public int compare(EducationProgram a, EducationProgram b) {
                    return a.getId() > b.getId() ? 1 : a.getId() == b.getId() ? 0 : -1;
                }
            })
            .build();
    private EducationProgramAdapter adapter;
    private List<EducationProgram> programs;
    private ProgressBar editProgressBar;
    private Animator mAnimator;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_program_selector);
        editProgressBar = (ProgressBar) findViewById(R.id.edit_progress_bar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        adapter = new EducationProgramAdapter(this, COMPARATOR, this);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter.addCallback(this);
        programs = MockDataProvider.createMockedBscPrograms();
        adapter.edit()
                .replaceAll(programs)
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_education_program, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.edit()
                .replaceAll(filter(programs, newText))
                .commit();
        return true;
    }

    @Override
    public void onItemClick(EducationProgram program) {

    }

    private static List<EducationProgram> filter(List<EducationProgram> models, String query) {

        final List<EducationProgram> filteredModelList = new ArrayList<>();
        for (EducationProgram model : models) {
            if (model.getName().contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public void onEditStarted() {
        if (editProgressBar.getVisibility() != View.VISIBLE) {
            editProgressBar.setVisibility(View.VISIBLE);
            editProgressBar.setAlpha(0.0f);
        }

        if (mAnimator != null) {
            mAnimator.cancel();
        }

        mAnimator = ObjectAnimator.ofFloat(editProgressBar, View.ALPHA, 1.0f);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.start();

        recyclerView.animate().alpha(0.5f);
    }

    @Override
    public void onEditFinished() {
        recyclerView.scrollToPosition(0);
        recyclerView.animate().alpha(1.0f);

        if (mAnimator != null) {
            mAnimator.cancel();
        }

        mAnimator = ObjectAnimator.ofFloat(editProgressBar, View.ALPHA, 0.0f);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {

            private boolean mCanceled = false;

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mCanceled = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!mCanceled) {
                    editProgressBar.setVisibility(View.GONE);
                }
            }
        });
        mAnimator.start();
    }
}
