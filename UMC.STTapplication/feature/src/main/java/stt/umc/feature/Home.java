package stt.umc.feature;

import android.content.Intent;
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

import java.util.List;

import info.androidhive.barcode.BarcodeReader;
import stt.umc.feature.Request.PatientRequest;
import stt.umc.feature.fragments.HistoryFragment;
import stt.umc.feature.fragments.HomeFragment;
import stt.umc.feature.fragments.ProfileFragment;
import stt.umc.feature.fragments.SearchFragment;
import stt.umc.feature.fragments.SettingFragment;

public class Home extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, HistoryFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener, SearchFragment.OnFragmentInteractionListener, BarcodeReader.BarcodeReaderListener, SettingFragment.OnFragmentInteractionListener {

    BarcodeReader barcodeReader;
    final Fragment homeFragment = new HomeFragment();
    final Fragment searchFragment = new SearchFragment();
    final Fragment historyFragment = new HistoryFragment();
    final Fragment settingFragment = new SettingFragment();
    final Fragment profileFragment  = new ProfileFragment();
    final FragmentManager mFragmentManager = getSupportFragmentManager();
    Fragment active = homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_view);
        //barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner_2);
        //Load bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        Bundle bundle = new Bundle();
        String sb = getIntent().getStringExtra("patient");
        bundle.putString("patient", sb);
        homeFragment.setArguments(bundle);
        profileFragment.setArguments(bundle);
        mFragmentManager.beginTransaction().add(R.id.frame_container, profileFragment, "5").hide(profileFragment).commit();
        mFragmentManager.beginTransaction().add(R.id.frame_container, historyFragment, "4").hide(historyFragment).commit();
        mFragmentManager.beginTransaction().add(R.id.frame_container, settingFragment, "3").hide(settingFragment).commit();
        mFragmentManager.beginTransaction().add(R.id.frame_container, searchFragment, "2").hide(searchFragment).commit();
        //set homepage

        mFragmentManager.beginTransaction().add(R.id.frame_container,homeFragment, "1").commit();
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

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

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
}
