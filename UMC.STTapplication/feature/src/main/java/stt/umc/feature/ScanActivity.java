package stt.umc.feature;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;
import stt.umc.feature.Utils.ConnectionAsync;
import stt.umc.feature.Utils.GlobalUtils;

public class ScanActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    BarcodeReader barcodeReader;
    AppCompatActivity mActivity;
    boolean mAlreadyScanned = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
        mActivity = this;
    }

    @Override
    public void onScanned(Barcode barcode) {
        barcodeReader.playBeep();
if(mAlreadyScanned)
            return;
        if(barcode != null && barcode.displayValue.length() > 0 )
        {
            mAlreadyScanned = true;
        }
        else {
            return;
        }

        GlobalUtils.getPatientHttpMethod("https://fit-umc-stt.azurewebsites.net/patient/" + barcode.displayValue, new ConnectionAsync.httpRequestListener() {
            @Override
            public void onRecevie(String data) {
                if(Home.HomeActivity != null ) {
                    ((Home) Home.HomeActivity).onReceiveDataHttp(data);
                }
            }
        }            }

            @Override
            public void onFailed() {
                if(Home.HomeActivity != null ) {
                    ((Home) Home.HomeActivity).onFailData();
                }
            }
        });

        Intent intent = new Intent(ScanActivity.this, Home.class);
        startActivity(intent);
        finish();
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
