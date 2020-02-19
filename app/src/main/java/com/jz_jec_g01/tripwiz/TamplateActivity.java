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
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class TamplateActivity extends AppCompatActivity {
    private static final String TAG = "DEB";
    private User user;

    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamplate);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        Log.d("ユーザ", user.getName());
        Log.d("ユーザー２", Integer.toString(user.getUserId()));

        navView = findViewById(R.id.nav_view);

        Bundle bundle = new Bundle();
        bundle.putSerializable("User", user);
        Log.d("Bundle情報", String.valueOf(bundle));

        HomeFragment homeFragment = HomeFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, homeFragment)
                .commit();

        /**
         *クリック時画面遷移メソッド
         * XML bottom_nav_menu　activity_tamplete
         * */
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d(TAG, "ナビゲーション動作確認  " + item.getItemId());
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        HomeFragment homeFragment =  HomeFragment.newInstance();

                        Log.d(TAG, "Homeボタンクリック");
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, homeFragment)
                                .commit();
                        return true;
                    case R.id.navigation_guide:
                        GuideFragment guideFragment = GuideFragment.newInstance();

                        Log.d(TAG, "Guideボタンクリック");
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, guideFragment)
                                .commit();
                        return true;

                    case R.id.navigation_myPage:
                        MyPageFragment myPageFragment = new MyPageFragment();
                        myPageFragment.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, myPageFragment)
                                .commit();
                        Log.d(TAG, "Mypegeボタンクリック");
                        return true;
                }
                return false;
            }
        });
    }

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
