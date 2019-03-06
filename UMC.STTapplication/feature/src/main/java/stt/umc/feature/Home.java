package stt.umc.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import java.util.zip.Inflater;

public class Home extends AppCompatActivity {

    private String[] clsRoom ={"201","203"};
    private String[] clsName={"Xét nghiệm","Siêu âm"};
    private Integer[] clsCurrentNumber={200,201};
    private Integer[] clsYourNumber={202,215};
    private String[] clsTime={"11:30","12:30"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        GridView gridView = findViewById(R.id.homeGrid);
        CustomCLSGridViewAdapter customCLSGridViewAdapter=new CustomCLSGridViewAdapter(this,clsRoom,clsName,clsCurrentNumber,clsYourNumber,clsTime);
        gridView.setAdapter(customCLSGridViewAdapter);
        findViewById(R.id.searchFooter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ScanOtherPatient.class));

            }
        });
        findViewById(R.id.homeFooter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Home.class));
                finish();
            }
        });
        findViewById(R.id.profileFooter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,UserProfile.class));

            }
        });

    }
}
