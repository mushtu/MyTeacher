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

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.squareup.picasso.Callback;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import ir.ac.ut.ece.moallem.api.model.Teacher;

public class TeacherDetailActivity extends AppCompatActivity {

    private static final String EXTRA_IMAGE = "ir.ac.ut.ece.moallem.extraImage";
    private static final String EXTRA_TITLE = "ir.ac.ut.ece.moallem.extraTitle";
    private static final String EXTRA_TEACHER = "ir.ac.ut.ece.moallem.extraTeacher";
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private NestedScrollView scrollView;

    public static void navigate(AppCompatActivity activity, View transitionImage, Teacher teacher) {
        Intent intent = new Intent(activity, TeacherDetailActivity.class);
        intent.putExtra(EXTRA_TEACHER, teacher);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.activity_detail);
        Log.d("Mushtu", "onCreate: " + getClass().getSimpleName());

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        supportPostponeEnterTransition();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Teacher teacher = (Teacher) getIntent().getSerializableExtra(EXTRA_TEACHER);
        String itemTitle = teacher.getFirstName() + " " + teacher.getLastName();
        String avatarUrl = teacher.getAvatarUrl();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(itemTitle);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        final ImageView image = (ImageView) findViewById(R.id.image);
        int imageResource = R.drawable.default_profile_image;

        if (avatarUrl.equals("mushtu.jpg"))
            imageResource = R.drawable.mushtu;
        else if (avatarUrl.equals("iraj.jpg"))
            imageResource = R.drawable.iraj;
        else if (avatarUrl.equals("sajad.jpg"))
            imageResource = R.drawable.sajad;
        /*if (image.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                public void onGenerated(Palette palette) {
                    applyPalette(palette);
                }
            });
        }*/


        Picasso.with(this).load(imageResource).into(image, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        applyPalette(palette);
                    }
                });
            }

            @Override
            public void onError() {

            }
        });

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(itemTitle);

        RatingBar rating = (RatingBar) findViewById(R.id.rating);
        rating.setRating(teacher.getRating());

        TextView txtDescription = (TextView) findViewById(R.id.txt_description);
        txtDescription.setText(teacher.getShortDescription());

        TextView txtExperience = (TextView) findViewById(R.id.txt_experience);
        int sectoins = teacher.getSections() == null ? 0 : teacher.getSections().size();
        txtExperience.setText("تجربه تدریس " + sectoins + " دانش آموز را دارم.");

        Button btnRef = (Button) findViewById(R.id.btn_references);
        btnRef.setText(teacher.getReferences().size() + " مرجع");
        btnRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReferencesView();
            }
        });

        TextView txtTeachingCourses = (TextView) findViewById(R.id.txt_teaching_courses);
        StringBuilder sb = new StringBuilder();
        int teachingCoursesSize = teacher.getTeachingCourses() == null ? 0 : teacher.getTeachingCourses().size();
        int i = 0;
        for (i = 0; i < teachingCoursesSize - 1; i++)
            sb.append(teacher.getTeachingCourses().get(i).getName() + "، ");
        sb.append(teacher.getTeachingCourses().get(i).getName());
        txtTeachingCourses.setText(sb.toString());

        TextView txtAddress = (TextView) findViewById(R.id.txt_address);
        txtAddress.setText(teacher.getAddress());

        TextView txtMobile = (TextView) findViewById(R.id.txt_mobile_number);
        txtMobile.setText(teacher.getMobile());

        ((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDialog dialog = new MaterialDialog.Builder(TeacherDetailActivity.this)
                        .content("علاقه مندی شما به مدرس برای همکاری ارسال شود؟")
                        .positiveText("بلی")
                        .negativeText("خیر")
                        .title("ارسال درخواست")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                //TODO call api
                                //on success
                                Snackbar.make(findViewById(R.id.content), "درخواست شما ارسال شد.", Snackbar.LENGTH_LONG).show();
                            }
                        })
                        .build();

                dialog.show();

            }
        });
    }

    private void showReferencesView() {
        //TODO
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.primary_dark);
        int primary = getResources().getColor(R.color.primary);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        updateBackground((FloatingActionButton) findViewById(R.id.fab), palette);
        supportStartPostponedEnterTransition();
    }

    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(getResources().getColor(android.R.color.white));
        int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.accent));

        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }

}
