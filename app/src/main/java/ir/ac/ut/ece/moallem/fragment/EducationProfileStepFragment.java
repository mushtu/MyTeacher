package ir.ac.ut.ece.moallem.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.List;

import ir.ac.ut.ece.moallem.R;
import ir.ac.ut.ece.moallem.api.model.UserMode;
import ir.ac.ut.ece.moallem.config.AppConfig;

/**
 * Created by mushtu on 7/13/17.
 */

public class EducationProfileStepFragment extends Fragment implements BlockingStep {

    private Spinner spinnerEducationLevel;
    private AutoCompleteTextView txtEduProgram;
    private String[] studentLevels;
    private String[] teacherLevels;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_education_step, container, false);
        spinnerEducationLevel = (Spinner) view.findViewById(R.id.spinner_edu_level);
        txtEduProgram = (AutoCompleteTextView) view.findViewById(R.id.txt_auto_edu_program);
        spinnerEducationLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (AppConfig.getInstance(getContext()).getUserMode() == UserMode.STUDENT.value()) {
                    if (i == 2) {
                        // high school
                        String[] fields = new String[]{"ریاضی و فیزیک", "علوم تجربی", "علوم انسانی", "فنی و حرفه ای"};
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_dropdown_item_1line, fields);
                        txtEduProgram.setAdapter(adapter);
                        txtEduProgram.setEnabled(true);

                    } else if (i == 3) {

                        String[] fields = new String[]{"مهندسی برق", "مهندسی کامپیوتر", "مهندسی مکانیک", "زبان انگلیسی", "ریاضی کاربردی", "فیزیک", "تربیت بدنی"};
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_dropdown_item_1line, fields);
                        txtEduProgram.setAdapter(adapter);
                        txtEduProgram.setEnabled(true);
                        // BSC
                    } else {
                        txtEduProgram.setAdapter(null);
                        txtEduProgram.setText("");
                        txtEduProgram.setEnabled(false);
                    }

                } else {
                    String[] fields = new String[]{"مهندسی برق", "مهندسی کامپیوتر", "مهندسی مکانیک", "زبان انگلیسی", "ریاضی کاربردی", "فیزیک", "تربیت بدنی"};
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_dropdown_item_1line, fields);
                    txtEduProgram.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        if (spinnerEducationLevel.getSelectedItem() != null)
            AppConfig.getInstance(getContext())
                    .setUserEducationLevel(spinnerEducationLevel.getSelectedItem().toString());
        if (txtEduProgram.getText().length() > 0)
            AppConfig.getInstance(getContext())
                    .setUserEducationProgram(txtEduProgram.getText().toString());
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
        if (spinnerEducationLevel.getSelectedItem() == null || txtEduProgram.getText().length() == 0)
            return new VerificationError("لطفا اطلاعات تحصیلی را وارد کنید.");
        return null;
    }

    @Override
    public void onSelected() {
        if (AppConfig.getInstance(getContext()).getUserMode() == UserMode.STUDENT.value()) {
            fillEduLevelSpinnerStudent();
        } else {
            fillEduLevelSpinnerTeacher();
        }
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    private void fillEduLevelSpinnerStudent() {
        studentLevels = new String[]{"ابتدایی", "راهنمایی", "دبیرستان", "کارشناسی"};
        SpinnerAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, studentLevels);
        spinnerEducationLevel.setAdapter(adapter);

    }

    private void fillEduLevelSpinnerTeacher() {
        teacherLevels = new String[]{"کارشناسی", "کارشناسی ارشد", "دکتری"};
        SpinnerAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, teacherLevels);
        spinnerEducationLevel.setAdapter(adapter);
    }
}
