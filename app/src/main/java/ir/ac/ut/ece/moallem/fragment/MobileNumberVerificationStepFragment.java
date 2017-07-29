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

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import ir.ac.ut.ece.moallem.R;
import ir.ac.ut.ece.moallem.RegisterActivity;
import ir.ac.ut.ece.moallem.config.AppConfig;

/**
 * Created by mushtu on 7/11/17.
 */

public class MobileNumberVerificationStepFragment extends Fragment implements BlockingStep {

    private EditText txtVerificationCode;
    private TextView txtTimer;
    private TextView txtMobile;
    private Disposable counter;
    private RegisterActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mobile_verification, container, false);
        txtTimer = (TextView) view.findViewById(R.id.txt_timer);
        txtMobile = (TextView) view.findViewById(R.id.txt_mobile);
        txtVerificationCode = (EditText) view.findViewById(R.id.txt_verification_code);
        txtTimer.setText("120");
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (counter != null && !counter.isDisposed())
            counter.dispose();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (RegisterActivity) context;
    }

    private void startTimer() {
        Observable.intervalRange(0, 120, 0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        counter = d;
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Long aLong) {
                        if (txtTimer != null)
                            txtTimer.setText((120 - aLong) + "");
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        counter = null;
                    }

                    @Override
                    public void onComplete() {
                        counter = null;
                    }
                });
    }

    @Override
    public VerificationError verifyStep() {
        if (txtVerificationCode.getText().toString().length() == 0)
            return new VerificationError("کد فعال سازی را وارد کنید!");
        return null;
    }

    @Override
    public void onSelected() {
        txtMobile.setText(activity.getMobileNumber());
        startTimer();
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        txtVerificationCode.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.shake_error));
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        if (counter != null && !counter.isDisposed())
            counter.dispose();
        //TODO send to server
        AppConfig.getInstance(getContext())
                .setMobileVerified(true);
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        if (counter != null && !counter.isDisposed())
            counter.dispose();
        callback.complete();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        if (counter != null && !counter.isDisposed())
            counter.dispose();
        callback.goToPrevStep();
    }
}
