package stt.umc.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class ScanActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener{

    BarcodeReader barcodeReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
        Button btnStorage = findViewById(R.id.btnStorage);
        btnStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScanActivity.this, GalleryActivity.class));
            }
        });
    }

    @Override
    public void onScanned(Barcode barcode) {
        barcodeReader.playBeep();

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
