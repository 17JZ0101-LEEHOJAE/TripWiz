package com.jz_jec_g01.tripwiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jz_jec_g01.tripwiz.ui.guide.GuideFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class TamplateActivity extends AppCompatActivity {

    private static final String TAG = "DEB";


//    public void tampInit() {
//        ageSpinner = findViewById(R.id.ageSpinner);
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        /**
         *クリック時画面遷移クラス
         * XML bottom_nav_menu　activity_tamplete
         * */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.d(TAG, "ナビゲーション動作確認  " + item.getItemId());
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    Log.d(TAG, "HOMEボタンクリック");
                    return true;

                case R.id.navigation_guide:
                    Log.d(TAG, "Guideボタンクリック");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.activity_main, GuideFragment.newInstance())
                            .commit();
                    return true;

                case R.id.navigation_talk:
                    Log.d(TAG, "Talkボタンクリック");
                    return true;

                case R.id.navigation_myPage:
                    Log.d(TAG, "Mypegeボタンクリック");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamplate);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_guide, R.id.navigation_talk,R.id.navigation_myPage)
                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
    }
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }

}
