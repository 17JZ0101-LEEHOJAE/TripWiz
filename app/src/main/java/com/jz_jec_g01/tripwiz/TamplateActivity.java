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

    private Spinner ageSpinner;

    public void tampInit() {
        ageSpinner = findViewById(R.id.ageSpinner);
    }

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
                    // Fragmentを作成します
                    GuideFragment fragment = new GuideFragment();
                    // Fragmentの追加や削除といった変更を行う際は、Transactionを利用します
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    // 新しく追加を行うのでaddを使用します
                    // 他にも、よく使う操作で、replace removeといったメソッドがあります
                    // メソッドの1つ目の引数は対象のViewGroupのID、2つ目の引数は追加するfragment
                    transaction.add(R.id.container, fragment);
                    // 最後にcommitを使用することで変更を反映します
                    transaction.commit();
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

        tampInit();

//        for(int j = 18; i < 60; i++) {
//
//        }
//
//        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(
//                this,
//                android.R.layout.simple_spinner_item,
//
//        );
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        ageSpinner.setAdapter(adapter);
//
//        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            // アイテムが選択されたとき
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ageSpinner = (Spinner) parent;
//                String item = (String) ageSpinner.getSelectedItem();
//            }
//            // アイテムが選択されなかったとき
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }
}
