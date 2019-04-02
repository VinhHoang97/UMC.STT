package stt.umc.feature;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MenuItem;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.Calendar;
import java.util.List;

import info.androidhive.barcode.BarcodeReader;
import stt.umc.feature.Request.PatientRequest;
import stt.umc.feature.fragments.HistoryFragment;
import stt.umc.feature.fragments.HomeFragment;
import stt.umc.feature.fragments.ProfileFragment;
import stt.umc.feature.fragments.SearchFragment;
import stt.umc.feature.fragments.SettingFragment;
import stt.umc.feature.receiver.AlarmReceiver;

public class Home extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, HistoryFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener, SearchFragment.OnFragmentInteractionListener, BarcodeReader.BarcodeReaderListener, SettingFragment.OnFragmentInteractionListener {

     public final static Boolean ALREADY_LOGIN = true;
     public final static Boolean NOT_LOGIN = false;
     Fragment homeFragment = new HomeFragment();
     Fragment searchFragment = new SearchFragment();
     Fragment historyFragment = new HistoryFragment();
     Fragment settingFragment = new SettingFragment();
     Fragment profileFragment  = new ProfileFragment();
     FragmentManager mFragmentManager = getSupportFragmentManager();
     Fragment active = homeFragment;

     public static Home HomeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_view);
        //Load bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        mFragmentManager.beginTransaction().add(R.id.frame_container, profileFragment, "5").hide(profileFragment).commit();
        mFragmentManager.beginTransaction().add(R.id.frame_container, historyFragment, "4").hide(historyFragment).commit();
        mFragmentManager.beginTransaction().add(R.id.frame_container, settingFragment, "3").hide(settingFragment).commit();
        mFragmentManager.beginTransaction().add(R.id.frame_container, searchFragment, "2").hide(searchFragment).commit();
        //set homepage

        mFragmentManager.beginTransaction().add(R.id.frame_container,homeFragment, "1").commit();

        //creating and assigning value to alarm manager class
        Calendar Alarm = Calendar.getInstance();
        Alarm.set(Calendar.HOUR_OF_DAY,17);
        Alarm.set(Calendar.MINUTE, 35);
        Intent AlarmIntent = new Intent(Home.this, AlarmReceiver.class);
        AlarmManager AlmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);
        PendingIntent Sender = PendingIntent.getBroadcast(Home.this, 0, AlarmIntent, 0);
        AlmMgr.set(AlarmManager.RTC_WAKEUP, Alarm.getTimeInMillis(), Sender);

        //
        HomeActivity = this;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            if (item.getItemId() == R.id.menu_home) {
                mFragmentManager.beginTransaction().hide(active).show(homeFragment).commit();
                active = homeFragment;
                return true;
            } else if (item.getItemId() == R.id.menu_search) {
                mFragmentManager.beginTransaction().hide(active).show(searchFragment).commit();
                active = searchFragment;
                return true;
            } else if (item.getItemId() == R.id.menu_history) {
                mFragmentManager.beginTransaction().hide(active).show(historyFragment).commit();
                active = historyFragment;
                return true;
            } else if (item.getItemId() == R.id.menu_profile) {
                mFragmentManager.beginTransaction().hide(active).show(profileFragment).commit();
                active = profileFragment;
                return true;
            } else if (item.getItemId() == R.id.menu_setting) {
                mFragmentManager.beginTransaction().hide(active).show(settingFragment).commit();
                active = settingFragment;
                return true;
            }
            return false;
        }
    };


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onScanned(Barcode barcode) {

    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onBackPressed() {
        getApplication().onTerminate();
    }

    public void onReceiveDataHttp(String data) {
        if(data.length() > 0) {
            SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_STATE", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("LOGIN_STATE", ALREADY_LOGIN);
            if(homeFragment != null ) {
                ((HomeFragment)homeFragment).onReceivingData(data);
            }
            if(profileFragment != null ) {
                ((ProfileFragment) profileFragment).onReceivingData(data);
            }
        }
    }

    public void onFailData() {
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_STATE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("LOGIN_STATE", NOT_LOGIN);

        if(homeFragment != null ) {
            ((HomeFragment)homeFragment).onFailed();
        }
    }
}
