package stt.umc.feature.Utils;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class ConnectionAsync extends AsyncTask<String, Void, String> {

    public interface httpRequestListener {
        void onRecevie(final String data);

        void onFailed();
    }

    private static httpRequestListener mHttpListener;

    public void setHttpRequestListener(httpRequestListener listener) {
        mHttpListener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpsURLConnection mHttpsURLConnection = (HttpsURLConnection) url.openConnection();
            mHttpsURLConnection.setDoInput(true);
            mHttpsURLConnection.setRequestMethod("GET");

            if (mHttpsURLConnection.getResponseCode() == 200) {
                final StringBuilder patientRequest = new StringBuilder();
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
                return patientRequest.toString();
                //latch.countDown();
            } else {
                Log.d("Error ", "");
                return "";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        // result is what you got from your connection
        if(result.length() > 0) {
            if (mHttpListener != null) {
                mHttpListener.onRecevie(result);
            }
        }
        else {
            if (mHttpListener != null) {
                mHttpListener.onFailed();
            }
        }
    }
}

