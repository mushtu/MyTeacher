package ir.ac.ut.ece.moallem.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by mushtu on 7/15/17.
 */

public class DialogUtil {

    public static MaterialDialog.Builder networkProblemDialogBuilder(Context context) {
        return new MaterialDialog.Builder(context)
                .content("لطفا ار اتصال به اینترنت مطمءن شوید و دوباره تلاش کنید.")
                .title("خطای اتصال")
                .positiveText("تلاش مجدد")
                .negativeText("لغو")
                ;

    }
}
