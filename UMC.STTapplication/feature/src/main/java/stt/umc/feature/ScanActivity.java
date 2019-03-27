package stt.umc.feature;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import info.androidhive.barcode.BarcodeReader;
import stt.umc.feature.Request.PatientRequest;
import stt.umc.feature.Utils.GlobalUtils;

public class ScanActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
    public static final int ALREADY_LOGIN = 001;
    public static final int NOT_LOGIN = 000;
    BarcodeReader barcodeReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
    }

    @Override
    public void onScanned(Barcode barcode) {
        barcodeReader.playBeep();
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_STATE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StringBuilder patientRequest = GlobalUtils.getPatientHttpMethod("https://fit-umc-stt.azurewebsites.net/patient/" + barcode.displayValue);
        if (patientRequest != null) {
            Intent intent = new Intent(ScanActivity.this, Home.class);
            intent.putExtra("patient", patientRequest.toString());
            editor.putInt("LOGIN_STATE", ALREADY_LOGIN);
            startActivity(intent);
            finish();
        }else{
            Looper.prepare();
            Toast.makeText(this,"ERROR UNABLE TO FOUND THIS PATIENT",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {
        Toast.makeText(getApplicationContext(), "Error occurred while scanning " + errorMessage, Toast.LENGTH_SHORT).show();
    }
}
