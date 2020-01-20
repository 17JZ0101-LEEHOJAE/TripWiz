package com.jz_jec_g01.tripwiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jz_jec_g01.tripwiz.ui.guide.GuideFragment;
import com.jz_jec_g01.tripwiz.ui.home.HomeFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class TamplateActivity extends AppCompatActivity {

    private static final String TAG = "DEB";

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
                    HomeFragment homeFragment =  HomeFragment.newInstance();

                    Log.d(TAG, "Homeボタンクリック");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.activity_main, homeFragment)
                            .commit();
                    return true;
                case R.id.navigation_guide:
                    GuideFragment guideFragment = GuideFragment.newInstance();

                    Log.d(TAG, "Guideボタンクリック");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.activity_main, guideFragment)
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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
//    @Override
//    public void onStart(){
//        super.onActivityResult(requestCode, resultCode, data)
//        Intent intent = getIntent();
//        String value = intent.getStringExtra("returndata");
//        Log.d(" "+ value + " ガイド検索値 取得", "onStart: ");
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        switch (requestCode) {
            case 123 :
                Log.d("" + requestCode, "requestCode　ガイド　値　取得");
                Log.d("" + resultCode, "resultCode　ガイド値　取得");
                Log.d("" + bundle.getString("key.StringAreas"), "エリア　ガイド　値　取得");
                Log.d("" + bundle.getString("key.StringDays"), "日付　ガイド　値　取得");
                Log.d("" + bundle.getString("key.StringLangs"), "言語　ガイド　値　取得");
                Log.d("" + bundle.getString("key.StringLangs"), "年齢　ガイド　値　取得");
                Log.d("" + bundle.getString("key.StringGenders"), "性別　ガイド　値　取得");
                Log.d("" + bundle.getString("key.StringCountrys"), "国籍　ガイド　値　取得");
                break;
            default:
                break;
        }
    }
}
