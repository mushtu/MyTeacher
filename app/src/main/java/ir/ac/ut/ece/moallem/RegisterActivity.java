package ir.ac.ut.ece.moallem;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import ir.ac.ut.ece.moallem.adapter.RegistrationStepperAdapter;
import ir.ac.ut.ece.moallem.api.model.UserMode;
import ir.ac.ut.ece.moallem.config.AppConfig;

public class RegisterActivity extends AppCompatActivity implements StepperLayout.StepperListener {

    private StepperLayout mStepperLayout;
    private String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle("ثبت نام");
        mStepperLayout = (StepperLayout) findViewById(R.id.stepper_layout);
        mStepperLayout.setAdapter(new RegistrationStepperAdapter(getSupportFragmentManager(), this));

        if (AppConfig.getInstance(this).isMobileVerified())
            mStepperLayout.setCurrentStepPosition(2);
        mStepperLayout.setTabNavigationEnabled(false);
        mStepperLayout.setListener(this);
        if (AppConfig.getInstance(this).isRegistrationCompleted())
            gotToHome();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        }

    }

    @Override
    public void onBackPressed() {

        if (mStepperLayout.getCurrentStepPosition() > 0 && mStepperLayout.getCurrentStepPosition() != 2)
            mStepperLayout.onBackClicked();
        else super.onBackPressed();
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }


    @Override
    public void onCompleted(View completeButton) {
        AppConfig.getInstance(this).setRegistrationCompleted(true);
        gotToHome();
    }

    private void gotToHome() {
        Intent intent = null;
        if (AppConfig.getInstance(this).getUserMode() == UserMode.STUDENT.value())
            intent = new Intent(this, StudentHomeActivity.class);
        else
            intent = new Intent(this, TeacherHomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, verificationError.getErrorMessage(), Toast.LENGTH_LONG);
    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {

    }
}
