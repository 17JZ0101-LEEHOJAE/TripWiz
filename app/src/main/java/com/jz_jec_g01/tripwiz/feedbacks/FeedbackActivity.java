package com.jz_jec_g01.tripwiz.feedbacks;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_jec_g01.tripwiz.R;
import com.jz_jec_g01.tripwiz.SignupActivity;
import com.jz_jec_g01.tripwiz.TamplateActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FeedbackActivity extends AppCompatActivity implements LocationListener {
    private Button returnBtn;
    private Button entryBtn;
    private LocationManager manager;
    private TextView textView;
    private TextView locationText;
    private RecyclerView recyclerView = null;

    private static final String[] names = {
            "katakuriko", "mai",
            "miki", "nagi", "saya",
            "toko"
    };
    private static final Integer[] photos = {
            R.drawable.katakuriko, R.drawable.mai, R.drawable.miki,
            R.drawable.nagi, R.drawable.saya, R.drawable.toko,
    };
//    private static final FeedbackActivity ourInstance = new FeedbackActivity();

//    public static FeedbackActivity getInstance() {
//        return ourInstance;
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        recyclerView = findViewById(R.id.recycler_view);
        // レイアウトマネージャを設定(ここで縦方向の標準リストであることを指定)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imagesList();
        returnBtn = findViewById(R.id.buttonReturn);
        entryBtn = findViewById(R.id.buttonEntry);
        returnBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        entryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showMyDialog();
            }
        });
        //座標取得
        textView = (TextView) findViewById(R.id.textView);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationText = findViewById(R.id.textViewlocation);

    }
    public void imagesList (){
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(rLayoutManager);


        // 配列をArrayListにコピー
        List<String> itemNames = new ArrayList<String>(Arrays.asList(names));
        List<Integer> itemImages = new ArrayList<Integer>(Arrays.asList(photos));

        // specify an adapter (see also next example)
        RecyclerView.Adapter rAdapter = new com.jz_jec_g01.tripwiz.feedbacks.FeedbackAdapter(itemImages, itemNames);
        recyclerView.setAdapter(rAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL); // ここで横方向に設定
        recyclerView.setLayoutManager(manager);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

    }
    // ダイアログが生成された時に呼ばれるメソッド ※必須
    //ダイアログを作る。
    public void showMyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialog_view = inflater.inflate(R.layout.my_dialog, null);
        RatingBar ratingBar = dialog_view.findViewById(R.id.ratingBar2);
        ratingBar.setIsIndicator(false);
        ratingBar.setMax(5);
        //レイアウトファイルからビューを取得

        //レイアウト、題名、OKボタンとキャンセルボタンをつけてダイアログ作成
        builder.setView(dialog_view)
                .setTitle("ガイド評価")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /*OKのときの処理内容*/
                            }
                        })
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        /*キャンセルされたときの処理*/
                    }
                });

        AlertDialog myDialog = builder.create();
        myDialog.setCanceledOnTouchOutside(false);
        //ダイアログ画面外をタッチされても消えないようにする。
        myDialog.show();
        //ダイアログ表示
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

//        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, this);
//        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (manager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            manager.removeUpdates(this);
        }
    }
    //座標取得
    @Override
    public void onLocationChanged(Location location) {
        String text = "緯度：" + location.getLatitude() + "経度：" + location.getLongitude();
        textView.setText(text);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        StringBuilder result = new StringBuilder();
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        for (Address address : addresses) {
//            int idx = address.getMaxAddressLineIndex();
//            // 1番目のレコードは国名のため省略
//            for (int i = 1; i<=idx; i++) {
//                result.append(address.getAddressLine(i));
//            }
//        }

        locationText.setText(result.toString());
        Log.d("" + locationText, "住所取得テスト2: ");
    }
    /**
     * 緯度・経度から住所を取得する。
     * @param context
     * @param latitude
     * @param longitude
     * @return 住所
     */
    public static String getAddress(
            Context context, double latitude, double longitude) {



        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        StringBuilder result = new StringBuilder();

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        }
        catch (IOException e) {
            return "";
        }

        for (Address address : addresses) {
            int idx = address.getMaxAddressLineIndex();
            // 1番目のレコードは国名のため省略
            for (int i = 1; i<=idx; i++) {
                result.append(address.getAddressLine(i));
            }
        }

        return result.toString();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}