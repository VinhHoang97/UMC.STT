package stt.umc.feature.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

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
import stt.umc.feature.interfaces.DialogCallback;


public class GlobalUtils {

    //Show rating dialog
    public static void showRatingDialog(Context context, final DialogCallback dialogCallback) {
        //create dialog
        final CustomDialogAdapter customDialogAdapter = new CustomDialogAdapter(context, R.style.customRatingDialog);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.rating_dialog, null);
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

    public static void getHistoryHttpMethod(final String urlString , ConnectionAsync.httpRequestListener listener) {
        ConnectionAsync task = new ConnectionAsync();
        task.setHttpRequestListener(listener);
        String[] params = new String[2];
        params[0] = urlString;
        params[1] = "";
        task.execute(params);
    }

    public static StringBuilder getTicket(final String urlString) {
        //PatientRunnable mPatientRunnable =  new PatientRunnable(urlString);
        //Thread mThread = new Thread(mPatientRunnable);
        //mThread.start();
        final CountDownLatch latch = new CountDownLatch(1);
        final StringBuilder roomRequest = new StringBuilder();
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                            roomRequest.append(line);
                        }
                        //JSONObject json = new JSONObject(sb.toString().substring(1,sb.length()-1));
                        //PatientRequest patientRequest = new PatientRequest(json);
                        mHttpsURLConnection.disconnect();
                        latch.countDown();
                    } else {
                        Log.d("Error ", "");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //return mPatientRunnable.getPatientRequest();
            }
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return roomRequest;
    }

    public static StringBuilder getHistory(final String urlString) {
        //PatientRunnable mPatientRunnable =  new PatientRunnable(urlString);
        //Thread mThread = new Thread(mPatientRunnable);
        //mThread.start();
        final CountDownLatch latch = new CountDownLatch(1);
        final StringBuilder roomRequest = new StringBuilder();
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                            roomRequest.append(line);
                        }
                        //JSONObject json = new JSONObject(sb.toString().substring(1,sb.length()-1));
                        //PatientRequest patientRequest = new PatientRequest(json);
                        mHttpsURLConnection.disconnect();
                        latch.countDown();
                    } else {
                        Log.d("Error ", "");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //return mPatientRunnable.getPatientRequest();
            }
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return roomRequest;
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
