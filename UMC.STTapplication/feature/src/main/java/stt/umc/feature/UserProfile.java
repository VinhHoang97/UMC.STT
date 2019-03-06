package stt.umc.feature;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UserProfile extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String BUTTON_STATE = "Button_State";

    RadioGroup radioGroupTime;
    RadioButton radioBtn5, radioBtn15, radioBtn30;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        radioGroupTime = (RadioGroup)findViewById(R.id.radioGrTime);
        radioBtn5 = (RadioButton)findViewById(R.id.radioBtn5);
        radioBtn15 = (RadioButton)findViewById(R.id.radioBtn15);
        radioBtn30 = (RadioButton)findViewById(R.id.radioBtn30);
        // helper method to open up the file.
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        // grab the last saved state here on each activity start


        radioGroupTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedRadioBtn = group.getCheckedRadioButtonId();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if (checkedRadioBtn == R.id.radioBtn5){
                    Toast.makeText(UserProfile.this, "Hiện thông báo trước khi khám 5 phút", Toast.LENGTH_LONG).show();
                } else if(checkedRadioBtn == R.id.radioBtn15){
                    Toast.makeText(UserProfile.this, "Hiện thông báo trước khi khám 15 phút", Toast.LENGTH_LONG).show();
                } else if(checkedRadioBtn == R.id.radioBtn30){
                    Toast.makeText(UserProfile.this, "Hiện thông báo trước khi khám 30 phút", Toast.LENGTH_LONG).show();
                }
                editor.putInt(BUTTON_STATE,checkedRadioBtn);
                editor.apply();
            }
        });
        if(sharedpreferences.getInt(BUTTON_STATE,0) == R.id.radioBtn5){
            radioBtn5.setChecked(true);
        } else if(sharedpreferences.getInt(BUTTON_STATE,0) == R.id.radioBtn15) {
            radioBtn15.setChecked(true);
        } else if(sharedpreferences.getInt(BUTTON_STATE,0) == R.id.radioBtn30) {
            radioBtn30.setChecked(true);
        }
        findViewById(R.id.searchFooter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, ScanOtherPatient.class));

            }
        });
        findViewById(R.id.homeFooter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, Home.class));

            }
        });
        findViewById(R.id.profileFooter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this,UserProfile.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

}
