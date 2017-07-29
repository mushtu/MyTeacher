package ir.ac.ut.ece.moallem.config;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ir.ac.ut.ece.moallem.api.MoallemApi;

/**
 * Created by mushtu on 10/16/16.
 */
public class AppConfig {

    public final static String KEY_LOCALE = "locale";
    private static final String KEY_FIRST_LAUNCH = "firstlaunch";

    private static final String KEY_USER_MOBILE = "user.mobile";
    private static final String KEY_USER_FIRST_NAME = "user.first.name";
    private static final String KEY_USER_LAST_NAME = "user.last.name";
    private static final String KEY_USER_IMAGE_PATH = "user.image.path";
    private static final String KEY_USER_MODE = "user.mode";
    private static final String KEY_MOBILE_IS_VERIFIED = "user.mobile.verified";
    private static final String KEY_REGISTRATION_COMPLETED = "user.registration.completed";
    private static final String KEY_REGISTRATION_STEP = "user.registration.step";
    private static final String KEY_USER_EDUCATION_LEVEL = "user.education.level";
    private static final String KEY_USER_EDUCATION_PROGRAM = "user.education.program";
    private static final String KEY_USER_ADDRESS = "user.address";
    private SharedPreferences preferences;
    private static volatile AppConfig instance;

    private AppConfig(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public static AppConfig getInstance(Context context) {
        if (instance == null) {
            synchronized (MoallemApi.class) {
                if (instance == null) {
                    instance = new AppConfig(context.getSharedPreferences("app.config", Context.MODE_PRIVATE));
                }
            }
        }
        return instance;
    }

    public void setUserFirstName(String userFirstName) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USER_FIRST_NAME, userFirstName);
        editor.commit();
    }

    public String getUserFirstName() {
        return preferences.getString(KEY_USER_FIRST_NAME, "");
    }

    public void setUserLastName(String userLastName) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USER_LAST_NAME, userLastName);
        editor.commit();
    }

    public String getUserLastName() {
        return preferences.getString(KEY_USER_LAST_NAME, "");
    }

    public void setUserMode(int userMode) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_USER_MODE, userMode);
        editor.commit();
    }

    public int getUserMode() {
        return preferences.getInt(KEY_USER_MODE, 0);
    }


    public void setMobileVerified(boolean verified) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_MOBILE_IS_VERIFIED, verified);
        editor.commit();
    }

    public boolean isMobileVerified() {
        return preferences.getBoolean(KEY_MOBILE_IS_VERIFIED, false);
    }

    public void setRegistrationCompleted(boolean completed) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_REGISTRATION_COMPLETED, completed);
        editor.commit();
    }

    public boolean isRegistrationCompleted() {
        return preferences.getBoolean(KEY_REGISTRATION_COMPLETED, false);
    }

    public void setRegistrationStepPosition(int stepPosition) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_REGISTRATION_STEP, stepPosition);
        editor.commit();
    }

    public int getRegistraionStepPosition() {
        return preferences.getInt(KEY_REGISTRATION_STEP, 0);
    }

    public void setUserEducationLevel(String level) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USER_EDUCATION_LEVEL, level);
        editor.commit();
    }


    public void setUserEducationProgram(String program) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USER_EDUCATION_PROGRAM, program);
        editor.commit();
    }

    public String getUserEducationLevel() {
        return preferences.getString(KEY_USER_EDUCATION_LEVEL, "");
    }

    public String getUserEducationProgram() {
        return preferences.getString(KEY_USER_EDUCATION_PROGRAM, "");
    }

    public void setUserAddress(String address) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USER_ADDRESS, address);
        editor.commit();
    }

    public String getUserAddress() {
        return preferences.getString(KEY_USER_ADDRESS, "");
    }

    public void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

    }
}
