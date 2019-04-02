package stt.umc.feature.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;

import javax.net.ssl.HttpsURLConnection;

import stt.umc.feature.CustomView.CustomDialogAdapter;
import stt.umc.feature.R;
import stt.umc.feature.PatientRunnable;
import stt.umc.feature.interfaces.DialogCallback;


public class GlobalUtils {

    //Show rating dialog
    public static void showRatingDialog(Context context, final DialogCallback dialogCallback) {
        //create dialog
        final CustomDialogAdapter customDialogAdapter = new CustomDialogAdapter(context, R.style.customRatingDialog);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.ratingdialog, null);
        customDialogAdapter.setContentView(v);
        Button ratingBtn = (Button) customDialogAdapter.findViewById(R.id.btnRatingDone);
        ratingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogCallback != null)
                    dialogCallback.callback(0);
                customDialogAdapter.dismiss();
            }
        });
        customDialogAdapter.show();
    }


    public static void getPatientHttpMethod(final String urlString , ConnectionAsync.httpRequestListener listener) {
        ConnectionAsync task = new ConnectionAsync();
        task.setHttpRequestListener(listener);
        String[] params = new String[2];
        params[0] = urlString;
        params[1] = "";
        task.execute(params);
    }



    public static Date stringToDate(String string) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate;
        try {
            startDate = (Date) df.parse(string);
            startDate = Date.valueOf(df.format(startDate));
            return startDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
