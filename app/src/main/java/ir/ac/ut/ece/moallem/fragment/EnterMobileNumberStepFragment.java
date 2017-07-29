package ir.ac.ut.ece.moallem.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import ir.ac.ut.ece.moallem.R;
import ir.ac.ut.ece.moallem.RegisterActivity;

/**
 * Created by mushtu on 7/11/17.
 */

public class EnterMobileNumberStepFragment extends Fragment implements BlockingStep {

    private EditText txtMobile;
    private RegisterActivity activity;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_mobile, container, false);
        txtMobile = (EditText) view.findViewById(R.id.txt_mobile_number);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (RegisterActivity) context;
    }

    @Override
    public VerificationError verifyStep() {
        if (txtMobile.getText().toString().length() < 10)
            return new VerificationError("شماره همراه معتبر نیست!");
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {
        txtMobile.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.shake_error));
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        activity.setMobileNumber(txtMobile.getText().toString());
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        callback.complete();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }
}
