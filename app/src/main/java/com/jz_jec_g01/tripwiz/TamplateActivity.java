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
import com.jz_jec_g01.tripwiz.model.User;
import com.jz_jec_g01.tripwiz.ui.guide.GuideFragment;
import com.jz_jec_g01.tripwiz.ui.home.HomeFragment;
import com.jz_jec_g01.tripwiz.ui.myPage.MyPageFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class TamplateActivity extends AppCompatActivity {
    private static final String TAG = "DEB";
    private User user;

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

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        Log.d("ユーザ", user.getName());

        Bundle bundle = new Bundle();
        bundle.putSerializable("userInfo", user);
        Log.d("Bundle情報", String.valueOf(bundle));

        MyPageFragment myPageFragment = new MyPageFragment();
        myPageFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_main, myPageFragment)
                .commit();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_guide, R.id.navigation_talk,R.id.navigation_myPage)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
}
