package stt.umc.feature;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;
import stt.umc.feature.Utils.GlobalUtils;

public class ScanActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
    public static final int ALREADY_LOGIN = 001;
    public static final int NOT_LOGIN = 000;
    BarcodeReader barcodeReader;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
        progressBar  = findViewById(R.id.pb);
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
