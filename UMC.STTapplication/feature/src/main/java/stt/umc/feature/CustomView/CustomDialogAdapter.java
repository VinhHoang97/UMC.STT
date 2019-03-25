package stt.umc.feature.CustomView;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

public class CustomDialogAdapter extends Dialog {
    public CustomDialogAdapter(@NonNull Context context) {
        super(context);
        this.setCancelable(false);
    }

    public CustomDialogAdapter(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.setCancelable(false);
    }


    @Override
    public void onBackPressed() {
        this.dismiss();
    }
}
