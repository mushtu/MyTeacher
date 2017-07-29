package ir.ac.ut.ece.moallem;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle("دسته بندی ها");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        }

        findViewById(R.id.img_academic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEducationCategory();
            }
        });

        findViewById(R.id.img_cooking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotImplementedMessage();
            }
        });

        findViewById(R.id.img_art).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotImplementedMessage();
            }
        });

        findViewById(R.id.img_sports).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotImplementedMessage();
            }
        });

    }

    private void showNotImplementedMessage() {
        Toast.makeText(this, "Coming soon!", Toast.LENGTH_LONG).show();
    }

    private void showEducationCategory() {
        Intent intent = new Intent(this, EducationsActivity.class);
        if (getIntent().hasExtra("prev.activity") &&
                getIntent().getExtras().getString("prev.activity", "").equals(TeacherHomeActivity.class.getSimpleName())) {
            intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            intent.putExtra("REQUIRED_RESULT", true);
        }
        startActivity(intent);
        if (getIntent().hasExtra("prev.activity") && getIntent().getStringExtra("prev.activity").equals(TeacherHomeActivity.class.getSimpleName()))
            finish();


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
}
