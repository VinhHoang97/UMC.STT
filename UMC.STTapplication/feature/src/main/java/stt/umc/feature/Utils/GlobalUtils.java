package stt.umc.feature.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    public static StringBuilder getPatientHttpMethod(final String urlString) {
        //PatientRunnable mPatientRunnable =  new PatientRunnable(urlString);
        //Thread mThread = new Thread(mPatientRunnable);
        //mThread.start();
        StringBuilder patientRequest = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpsURLConnection mHttpsURLConnection = (HttpsURLConnection) url.openConnection();
            mHttpsURLConnection.setDoInput(true);
            mHttpsURLConnection.setRequestMethod("GET");
            if (mHttpsURLConnection.getResponseCode() == 200) {
                mHttpsURLConnection.connect();
                InputStream responseBody = mHttpsURLConnection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(responseBody));
                String line;
                while ((line = rd.readLine()) != null) {
                    patientRequest.append(line);
                }
                //JSONObject json = new JSONObject(sb.toString().substring(1,sb.length()-1));
                //PatientRequest patientRequest = new PatientRequest(json);
                mHttpsURLConnection.disconnect();
            } else {
                Log.d("Error ", "");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return mPatientRunnable.getPatientRequest();
        return patientRequest;
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
