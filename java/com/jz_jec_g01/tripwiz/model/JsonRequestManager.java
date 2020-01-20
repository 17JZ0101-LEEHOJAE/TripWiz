package com.jz_jec_g01.tripwiz.model;

import android.util.Log;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class JsonRequestManager {
    /// constant ///
    private static final String URL_GET_MYPAGE = "http://10.210.20.161/MyPageController.php";
    //private static final String URL_POST = "ここにURLを記述";

    private static final String TAG = "DEBUG";

    /// variable ///
    private static JsonRequestManager self;
    private OkHttpClient client;

    /// constructor & getter ///
    private JsonRequestManager(){
        client = new OkHttpClient.Builder().build();
    }
    public static JsonRequestManager getInstance() {
        if (self == null) {
            self = new JsonRequestManager();
        }
        return self;
    }

    /// method ///
    /**
     * 非同期でJSON取得する
     * @param callback 通信スレッドに渡す処理
     */
    public void asyncGetMyPage(Callback callback) {
        final Request request = new Request.Builder().url(URL_GET_MYPAGE).build();
        client.newCall(request).enqueue(callback);
    }

//    /**
//     * 非同期でJSON POSTする
//     * @param body POSTするJSONデータ
//     * @param callback 通信スレッドに渡す処理
//     */
//    public void asyncPostJson(String body, Callback callback) {
//        Log.d(TAG, "asyncPostJson: " + body);
//        //final MediaType mediaTypeJson = MediaType.get("application/x-www-form-urlencoded; charset=utf-8");    // urlencodedで送りたいときはこっち
//        final MediaType mediaTypeJson = MediaType.get("application/json; charset=utf-8");
//        final RequestBody requestBody = RequestBody.create(body, mediaTypeJson);
//        try{
//            Log.d(TAG, "asyncPostJson: " + requestBody.contentLength());
//        }catch (IOException e){}
//        final Request request = new Request.Builder()
//                .url(URL_POST)
//                .post(requestBody)
//                .build();
//
//        client.newCall(request).enqueue(callback);
//    }
}
