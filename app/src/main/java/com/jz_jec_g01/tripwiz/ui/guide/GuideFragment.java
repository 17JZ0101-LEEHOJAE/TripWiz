package com.jz_jec_g01.tripwiz.ui.guide;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.jz_jec_g01.tripwiz.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//AndroidX
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


public class GuideFragment extends Fragment {
//    final String url = "http://10.210.20.161";
    final String url = "http://www.jz.jec.ac.jp/17jzg01";
    final Request request = new Request.Builder().url(url).build();
    final OkHttpClient client = new OkHttpClient.Builder().build();


//    // 表示する画像の名前（拡張子無し）
//    private static final String[] imageNames = {
//            "katakuriko", "mai",
//            "miki", "nagi", "saya",
//            "toko"
//    };

    private View v;

    // RecyclerViewとAdapter
    private RecyclerView recyclerView = null;
    private GuideAdapter rAdapter = null;
    private Activity mActivity = null;

//    // それぞれの画像ファイルをdarawableに入れます
//    // ArrayListにコピーするためintからInteger型にしました
//    private static final Integer[] photos = {
//            R.drawable.katakuriko, R.drawable.mai, R.drawable.miki,
//            R.drawable.nagi, R.drawable.saya, R.drawable.tokyo
//    };
    // Resource IDを格納するarray
    private List<Integer> imgList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }


      //JSONの作成
//    final MediaType JSON = MediaType.get("application/json; charset=utf-8");
//    String json = "{\"mailAddress\":\"" + mailAddress + "\"}";
//
//    RequestBody body = RequestBody.create(JSON, json);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_guide, container, false);

        // RecyclerViewの参照を取得
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        // レイアウトマネージャを設定(ここで縦方向の標準リストであることを指定)
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        imagesList();
        return v;
    }

    public static GuideFragment newInstance() {
        return new GuideFragment();
    }

    public void imagesList (){
        client.newCall(request).enqueue(new Callback() {
            final Handler mHandler = new Handler(Looper.getMainLooper());

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("データベース接続", "接続失敗");
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("データベース接続", "接続成功");

                        String guideUrl = url + "/GuideDisplay.php";
                        Request request = new Request.Builder()
                                .url(guideUrl)
                                .get()
                                .build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("JSON取得", "取得失敗");
                                        e.printStackTrace();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            String jsonData = response.body().string();
                                            Log.d("Json", jsonData);
                                            JSONArray jArray = new JSONArray(jsonData);
                                            List<String> itemNames = new ArrayList<>();
                                            List<String> itemImages = new ArrayList<>();

                                            for(int i = 0; i < jArray.length(); i++) {
                                                itemNames.add(jArray.getJSONObject(i).getString("name"));
                                                itemImages.add(jArray.getJSONObject(i).getString("profile"));
                                            }
                                            // use this setting to improve performance if you know that changes
                                            // in content do not change the layout size of the RecyclerView
                                            recyclerView.setHasFixedSize(true);

                                            // use a linear layout manager
                                            RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity());

                                            recyclerView.setLayoutManager(rLayoutManager);

                                            // specify an adapter (see also next example)
                                            RecyclerView.Adapter rAdapter = new GuideAdapter(itemImages, itemNames);
                                            recyclerView.setAdapter(rAdapter);

                                            LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                                            manager.setOrientation(LinearLayoutManager.HORIZONTAL); // ここで横方向に設定
                                            recyclerView.setLayoutManager(manager);

                                            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                                        } catch(IOException e) {
                                            e.printStackTrace();
                                        } catch(JSONException e) {
                                            e.printStackTrace();
                                        }
                                        response.body().close();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
}