package ir.ac.ut.ece.moallem.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import ir.ac.ut.ece.moallem.R;
import ir.ac.ut.ece.moallem.api.model.UserMode;
import ir.ac.ut.ece.moallem.config.AppConfig;

/**
 * Created by mushtu on 7/13/17.
 */

public class UserModeSelectionStepFragment extends Fragment implements BlockingStep {

    private RadioGroup userModeRadioGrp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_mode_selection_step, container, false);
        userModeRadioGrp = (RadioGroup) view.findViewById(R.id.radio_grp_user_mode);
        userModeRadioGrp.check(R.id.radio_btn_student);
        return view;
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        UserMode mode = userModeRadioGrp.getCheckedRadioButtonId() == R.id.radio_btn_student ? UserMode.STUDENT :
                userModeRadioGrp.getCheckedRadioButtonId() == R.id.radio_btn_teacher ? UserMode.TEACHER : UserMode.UNKNOWN;
        AppConfig.getInstance(getContext())
                .setUserMode(mode.value());
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

    @Override
    public VerificationError verifyStep() {
        if (userModeRadioGrp.getCheckedRadioButtonId() < 0)
            return new VerificationError("نوع کاربری انتخاب نشده است!");
        return null;
    }

    @Override
    public void onSelected() {
        int mode = AppConfig.getInstance(getContext())
                .getUserMode();
        if (UserMode.fromValue(mode).equals(UserMode.STUDENT))
            userModeRadioGrp.check(R.id.radio_btn_student);
        else if (UserMode.fromValue(mode).equals(UserMode.TEACHER))
            userModeRadioGrp.check(R.id.radio_btn_teacher);
        else
            userModeRadioGrp.clearCheck();
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
