package ir.ac.ut.ece.moallem.adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import ir.ac.ut.ece.moallem.fragment.BasicInfoStepFragment;
import ir.ac.ut.ece.moallem.fragment.EducationProfileStepFragment;
import ir.ac.ut.ece.moallem.fragment.EnterMobileNumberStepFragment;
import ir.ac.ut.ece.moallem.fragment.MobileNumberVerificationStepFragment;
import ir.ac.ut.ece.moallem.fragment.UserModeSelectionStepFragment;

/**
 * Created by mushtu on 7/11/17.
 */

public class RegistrationStepperAdapter extends AbstractFragmentStepAdapter {
    public RegistrationStepperAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(@IntRange(from = 0L) int position) {
        Step step = null;
        switch (position) {
            case 0:
                step = new EnterMobileNumberStepFragment();
                break;
            case 1:
                step = new MobileNumberVerificationStepFragment();
                break;
            case 2:
                step = new BasicInfoStepFragment();
                break;
            case 3:
                step = new UserModeSelectionStepFragment();
                break;
            case 4:
                step = new EducationProfileStepFragment();
                break;
        }
        return step;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0L) int position) {

        StepViewModel model = null;
        switch (position) {
            case 0:
                model = new StepViewModel.Builder(context)
                        .setTitle("شماره همراه")
                        .create();
                break;
            case 1:
                model = new StepViewModel.Builder(context)
                        .setTitle("فعال سازی")
                        .create();
                break;
            case 2:

                model = new StepViewModel.Builder(context)
                        .setTitle("پروفایل شخصی")
                        .create();
                break;
            case 3:
                model = new StepViewModel.Builder(context)
                        .setTitle("انتخاب کاربری")
                        .create();
                break;
            case 4:
                model = new StepViewModel.Builder(context)
                        .setTitle("تحصیلات")
                        .create();
                break;


        }

        return model;


    }
}
