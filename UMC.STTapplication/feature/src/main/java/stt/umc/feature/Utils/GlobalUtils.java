package stt.umc.feature.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import stt.umc.feature.CustomView.CustomDialogAdapter;
import stt.umc.feature.R;
import stt.umc.feature.interfaces.DialogCallback;

public class GlobalUtils {
    //Show rating dialog
    public static void showRatingDialog(Context context, final DialogCallback dialogCallback){
        //create dialog
        final CustomDialogAdapter customDialogAdapter = new CustomDialogAdapter(context, R.style.customRatingDialog);
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.ratingdialog,null);
        customDialogAdapter.setContentView(v);
        Button ratingBtn = (Button)customDialogAdapter.findViewById(R.id.btnRatingDone);
        ratingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogCallback!=null)
                    dialogCallback.callback(0);
                customDialogAdapter.dismiss();
            }
        });
        customDialogAdapter.show();
    }
}
