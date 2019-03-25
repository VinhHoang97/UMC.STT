package stt.umc.feature;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MenuItem;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;
import stt.umc.feature.fragments.HistoryFragment;
import stt.umc.feature.fragments.HomeFragment;
import stt.umc.feature.fragments.ProfileFragment;
import stt.umc.feature.fragments.SearchFragment;
import stt.umc.feature.fragments.SettingFragment;

public class Home extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, HistoryFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener,SearchFragment.OnFragmentInteractionListener , BarcodeReader.BarcodeReaderListener, SettingFragment.OnFragmentInteractionListener {

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

        //Load fragment
        loadFragment(new HomeFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            if (item.getItemId() == R.id.menu_home) {
                fragment = new HomeFragment();
                loadFragment(fragment);
                return true;
            } else if (item.getItemId() == R.id.menu_search) {
                fragment = new SearchFragment();
                loadFragment(fragment);
                return true;
            } else if (item.getItemId() == R.id.menu_history) {
                fragment = new HistoryFragment();
                loadFragment(fragment);
                return true;
            } else if (item.getItemId() == R.id.menu_profile) {
                fragment = new ProfileFragment();
                loadFragment(fragment);
                return true;
            } else if (item.getItemId() == R.id.menu_setting) {
                fragment = new SettingFragment();
                loadFragment(fragment);
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
