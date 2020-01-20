package com.jz_jec_g01.tripwiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class SplashActivity extends AppCompatActivity {
    final Handler mHandler = new Handler();
    private WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // WebView呼び出し
        myWebView = (WebView) findViewById(R.id.webView);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("/***指定ファイル****/"); // ローカルのhtmlファイルを指定

// WebView内のJavaScriptの実行を許可
        myWebView.getSettings().setJavaScriptEnabled(true);

        setContentView(R.layout.splash);
        mHandler.postDelayed(mSplashTask, 6000);


    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mSplashTask);
    }

    private Runnable mSplashTask = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);//画面遷移のためのIntentを準備
            startActivity(intent);//実際の画面遷移を開始
            finish();//現在のActivityを削除
        }
    };
}