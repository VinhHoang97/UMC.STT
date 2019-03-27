package stt.umc.feature;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import javax.net.ssl.HttpsURLConnection;

public class PatientRunnable implements Runnable {
    private String urlString;
    private StringBuilder patientRequest;

    public StringBuilder getPatientRequest() {
        return patientRequest;
    }

    public PatientRunnable(String urlString) {
        this.urlString = urlString;
    }

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
    }
}
