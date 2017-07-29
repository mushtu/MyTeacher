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
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import ir.ac.ut.ece.moallem.api.model.TeachRequest;
import ir.ac.ut.ece.moallem.api.model.Teacher;

public class TeachRequestDetailActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE = "ir.ac.ut.ece.moallem.extraImage";
    public static final String EXTRA_TITLE = "ir.ac.ut.ece.moallem.extraTitle";
    public static final String EXTRA_TEACHE_REQUEST = "ir.ac.ut.ece.moallem.extraTeachRequest";
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private NestedScrollView scrollView;
    private TeachRequest teachRequest;

    public static void navigateForResult(AppCompatActivity activity, View transitionImage, TeachRequest request, int requestCode) {
        Intent intent = new Intent(activity, TeachRequestDetailActivity.class);
        intent.putExtra(EXTRA_TEACHE_REQUEST, request);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
        ActivityCompat.startActivityForResult(activity, intent, requestCode, options.toBundle());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.activity_teach_request_detail);
        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        supportPostponeEnterTransition();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        teachRequest = (TeachRequest) getIntent().getSerializableExtra(EXTRA_TEACHE_REQUEST);
        String itemTitle = teachRequest.getStudent().getFirstName() + " " + teachRequest.getStudent().getLastName();
        String avatarUrl = teachRequest.getStudent().getAvatarUrl();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(itemTitle);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        final ImageView image = (ImageView) findViewById(R.id.image);
        if (avatarUrl.equals("reza.jpg"))
            image.setImageResource(R.drawable.reza);
        if (image.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                public void onGenerated(Palette palette) {
                    applyPalette(palette);
                }
            });
        }


  /*      Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).into(image, new Callback() {
            @Override public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        applyPalette(palette);
                    }
                });
            }

            @Override public void onError() {

            }
        });*/

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(itemTitle);

        TextView txtDescription = (TextView) findViewById(R.id.txt_description);
        txtDescription.setText(teachRequest.getDescription());

        TextView txtAddress = (TextView) findViewById(R.id.txt_address);
        txtAddress.setText(teachRequest.getStudent().getAddress());

        TextView txtMobile = (TextView) findViewById(R.id.txt_mobile_number);
        txtMobile.setText(teachRequest.getStudent().getMobile());

        ((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDialog dialog = new MaterialDialog.Builder(TeachRequestDetailActivity.this)
                        .content("درخواست دانش آموز را قبول میکنم؟")
                        .positiveText("بلی")
                        .negativeText("خیر")
                        .title("قبول درخواست")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Intent dataIntent = new Intent();
                                dataIntent.putExtra("request_id", teachRequest.getId());
                                setResult(RESULT_OK, dataIntent);
                                finish();
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
