package ir.ac.ut.ece.moallem.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.mvc.imagepicker.ImagePicker;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import ir.ac.ut.ece.moallem.R;
import ir.ac.ut.ece.moallem.config.AppConfig;

/**
 * Created by mushtu on 7/13/17.
 */

public class BasicInfoStepFragment extends Fragment implements BlockingStep {

    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtAddress;
    private ImageView imgProfile;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImagePicker.setMinQuality(600, 600);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = ImagePicker.getImageFromResult(getActivity(), requestCode, resultCode, data);
        if (bitmap != null) {
            imgProfile.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_info_step, container, false);
        imgProfile = (ImageView) view.findViewById(R.id.img_profile);
        txtFirstName = (EditText) view.findViewById(R.id.txt_first_name);
        txtLastName = (EditText) view.findViewById(R.id.txt_last_name);
        txtAddress = (EditText) view.findViewById(R.id.txt_address);
        txtFirstName.setText(AppConfig.getInstance(getContext()).getUserFirstName());
        txtLastName.setText(AppConfig.getInstance(getContext()).getUserLastName());
        txtAddress.setText(AppConfig.getInstance(getContext()).getUserAddress());
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.pickImage(BasicInfoStepFragment.this, "انتخاب عکس");
            }
        });
        return view;
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

        AppConfig.getInstance(getContext())
                .setUserFirstName(txtFirstName.getText().toString());

        AppConfig.getInstance(getContext())
                .setUserLastName(txtLastName.getText().toString());

        AppConfig.getInstance(getContext())
                .setUserAddress(txtAddress.getText().toString());
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        callback.complete();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        //callback.goToPrevStep();
    }

    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
