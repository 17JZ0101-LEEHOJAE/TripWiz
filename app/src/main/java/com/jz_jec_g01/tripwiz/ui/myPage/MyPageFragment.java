package com.jz_jec_g01.tripwiz.ui.myPage;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.jz_jec_g01.tripwiz.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.jz_jec_g01.tripwiz.model.User;
import com.jz_jec_g01.tripwiz.chats.ChatActivity;
import com.jz_jec_g01.tripwiz.feedbacks.FeedbackActivity;
import com.jz_jec_g01.tripwiz.feedbacks.FeedbackAdapter;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyPageFragment extends Fragment {
    final String url = "http://10.210.20.161";
    //    final String url = "http://www.jz.jec.ac.jp/17jzg01";
    final Request request = new Request.Builder().url(url).build();
    final OkHttpClient client = new OkHttpClient.Builder().build();
    private View v;
    private LinearLayout selectLnagsBox;
    private LinearLayout selectAreaBox;
    private TextView myName;
    private ImageView myProfile;
    private TextView editSelectedLang;
    private TextView editSelectedArea;
    private TextView editSelectedJob;
    private TextView entSelectedJob;
    private TextView entSelectLang;
    private TextView entSelectArea;
    private TextView textViewRate;
    private TextView entTextProfile;
    private TextView dayTitle;
    private EditText editTextProfile;
    private Button mon_day_btn;
    private Button tues_day_btn;
    private Button wed_day_btn;
    private Button thurs_day_btn;
    private Button fri_day_btn;
    private Button saturs_day_btn;
    private Button sun_day_btn;
    private Button btnEditProfile;
    private Button btnEntryProfile;
    private Button feedbackBtn;
    private TableRow entDayTable;
    private TableRow editDayTable;
    private TableLayout dayTableLayout;
    private String[] job = {"学生", "会社員", "専業主婦", "その他"};
    private String[] langs = {"日本", "アメリカ", "韓国", "台湾", "スペイン", "ドイツ"};
    private String[] areas = {"足立区", "荒川区", "板橋区", "江戸川区", "大田区", "葛飾区", "北区", "江東区", "品川区", "渋谷区", "新宿区", "杉並区",
            "墨田区", "世田谷区", "台東区", "中央区", "練馬区", "文京区", "港区", "目黒区"};
    private Switch SwitchUser;
    private RatingBar ratingBar;
    private User user;

    private Activity mActivity = null;
    int dayBtn = 0;
    int gudieON = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_my_page, container, false);
        myName = v.findViewById(R.id.textViewUserName);
        myProfile = v.findViewById(R.id.imageViewUser);
        //ガイド切り替え
        SwitchUser = v.findViewById(R.id.switchUser);
        //言語とエリアと曜日テーブル
        selectAreaBox = v.findViewById(R.id.selectAreasBox);
        selectLnagsBox = v.findViewById(R.id.selectLangsBox);
        dayTableLayout = v.findViewById(R.id.editDayTableLayout);
        dayTitle = v.findViewById(R.id.textViewDayOfTheWeek);
        //評価レート
        textViewRate = v.findViewById(R.id.textViewRate);
        ratingBar = v.findViewById(R.id.ratingBar);
        //言語とエリア取得
        //入力系
        editSelectedJob = v.findViewById(R.id.edit_selectJob);
        editSelectedLang = v.findViewById(R.id.edit_selectLang);
        editSelectedArea = v.findViewById(R.id.edit_selectArea);
        //出力系
        entSelectedJob =v.findViewById(R.id.entry_selectJob);
        entSelectLang = v.findViewById(R.id.entry_selectLang);
        entSelectArea = v.findViewById(R.id.entry_selectArea);
        //曜日ボタン取得
        mon_day_btn = v.findViewById(R.id.mon_day);
        tues_day_btn = v.findViewById(R.id.tues_day);
        wed_day_btn = v.findViewById(R.id.wed_day);
        thurs_day_btn = v.findViewById(R.id.thurs_day);
        fri_day_btn = v.findViewById(R.id.fri_day);
        saturs_day_btn = v.findViewById(R.id.saturs_day);
        sun_day_btn = v.findViewById(R.id.sun_day);
        //日付テーブル
        editDayTable = v.findViewById(R.id.edit_day_table);
        entDayTable = v.findViewById(R.id.entry_day_table);
        //プロフィールボタン取得
        btnEditProfile = v.findViewById(R.id.buttonEditProfile);
        btnEntryProfile = v.findViewById(R.id.buttonEntryProfile);
        //自己紹介text
        editTextProfile = v.findViewById(R.id.editTextProfile);
        entTextProfile = v.findViewById(R.id.entryTextProfile);
        //フィードバック画面遷移

        //ガイドユーザー切り替え
        // switchButtonのオンオフが切り替わった時の処理を設定
        SwitchUser.setOnCheckedChangeListener(
            new CompoundButton.OnCheckedChangeListener(){
                public void onCheckedChanged(CompoundButton comButton, boolean isChecked){
                    // 表示する文字列をスイッチのオンオフで変える
                    // オンなら
                    if(isChecked){
                        selectAreaBox.setVisibility(View.VISIBLE);
                        dayTableLayout.setVisibility(View.VISIBLE);
                        dayTitle.setVisibility(View.VISIBLE);
                        gudieON = 1;
                    }
                    // オフなら
                    else{
                        selectAreaBox.setVisibility(View.GONE);
                        dayTableLayout.setVisibility(View.GONE);
                        dayTitle.setVisibility(View.GONE);
                        gudieON = 0;
                    }

                }
            }
        );
        btnEditProfile.setOnClickListener(new Visibilitys());
        btnEntryProfile.setOnClickListener(new Visibilitys());
        //国籍と言語とエリア選択
        editSelectedJob.setOnClickListener(new btnselecteds());
        editSelectedLang.setOnClickListener(new btnselecteds());
        editSelectedArea.setOnClickListener(new btnselecteds());
        //曜日選択
        mon_day_btn.setOnClickListener(new BtnAddAlermClickListener());
        tues_day_btn.setOnClickListener(new BtnAddAlermClickListener());
        wed_day_btn.setOnClickListener(new BtnAddAlermClickListener());
        thurs_day_btn.setOnClickListener(new BtnAddAlermClickListener());
        fri_day_btn.setOnClickListener(new BtnAddAlermClickListener());
        saturs_day_btn.setOnClickListener(new BtnAddAlermClickListener());
        sun_day_btn.setOnClickListener(new BtnAddAlermClickListener());

//        feedbackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), FeedbackActivity.class);
//                v.getContext().startActivity(intent);
//            }
//        });

        //データの受け取り（TamplateActivityからの）
        Bundle bundle = getArguments();
        Log.d("Bundle", String.valueOf(bundle));
        if(bundle != null) {
            user = (User) bundle.getSerializable("User");
            Log.d("userId", Integer.toString(user.getUserId()));
            Log.d("userName",user.getName());
        }

        //プロフィール情報取得
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String json = "{\"name\":\"" + user.getName() + "\"}";

        RequestBody body = RequestBody.create(json, JSON);
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

                        String urlS = url + "/Information_User.php";
                        Request request = new Request.Builder()
                                .url(urlS)
                                .post(body)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {

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
                                            for(int i = 0; i < jArray.length(); i++) {
                                                myName.setText(user.getName());
                                                user.setGender(jArray.getJSONObject(i).getInt("gender"));
                                                user.setJob(jArray.getJSONObject(i).getString("job"));
                                                user.setNationality(jArray.getJSONObject(i).getString("nationality"));
                                                user.setIntroduction(jArray.getJSONObject(i).getString("introduction"));
                                                user.setProfile(jArray.getJSONObject(i).getString("profile"));
                                                user.setGuideStatus(jArray.getJSONObject(i).getInt("guide_status"));
                                                user.setRating(jArray.getJSONObject(i).getDouble("rating_rate"));
                                                user.setArea(jArray.getJSONObject(i).getString("information_area"));
                                                user.setWeek(jArray.getJSONObject(i).getString("information_week"));
                                                if(!user.getProfile().equals("")) {
                                                    String imgUrl = url + "/image/" + user.getProfile();
                                                    Log.d("URL", imgUrl);

                                                    Request request = new Request.Builder()
                                                            .url(imgUrl)
                                                            .get()
                                                            .build();

                                                    client.newCall(request).enqueue(new Callback() {
                                                        @Override
                                                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                                        }

                                                        @Override
                                                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                                            if(response.isSuccessful()) {
                                                                final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                                                                mHandler.post(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        try {
                                                                            myProfile.setImageBitmap(bitmap);
                                                                        } catch(NetworkOnMainThreadException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                            setRatingBar();
                                            entSelectedJob.setText(user.getJob());
                                            entTextProfile.setText(user.getIntroduction());
                                        } catch(IOException e) {
                                            e.printStackTrace();
                                        } catch(JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        return v;
    }

    public void setRatingBar() {
        double rating = user.getRating();
        textViewRate.setText(String.valueOf(rating));
        Log.d("" + rating, "setRatingBar: ");
        // 星の数を5に設定
        ratingBar.setNumStars(5);
        // レートの変更を可能にする
//        ratingBar.setIsIndicator(false);
        // レートが加減される時のステップ幅を0.3に設定
//        ratingBar.setStepSize((float) 0.3);
        // レートの設定
        ratingBar.setRating((float) rating);
    }

    private class btnselecteds implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.edit_selectLang) {
                addLangsOnCheckBox();
                Log.d("言語ダイアログ表示" , "onClick: ");
            }else if (v.getId() == R.id.edit_selectArea){
                addAreaOnCheckBox();
                Log.d("エリアダイアログ表示" , "onClick: ");
            }else if (v.getId() == R.id.edit_selectJob)
                addJobOnCheckBox();
            Log.d("国籍ダイアログ表示" , "onClick: ");
        }
    }
    //エリアスピナーダイアログ表示用
    public void addAreaOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(getActivity())
                .setTitle("エリア　選択")
                .setMultiChoiceItems(areas, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) checkedItems.add(which);
                        else checkedItems.remove((Integer) which);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String AreaBox = "";
                        for (Integer i : checkedItems) {
                            int cnt = 0;
                            if(cnt < 4) {
                                cnt++;
                                String Area = String.join(",", areas[i]);

                                AreaBox = AreaBox + " " + Area;
                                Log.d(Area, "代入値Lang: ");
                            } else {
                                Toast.makeText(getApplicationContext(), "選択個数が多すぎます。　4つ以下にしてください！", Toast.LENGTH_LONG).show();
                            }
                            editSelectedArea.setText(AreaBox);

                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    //ダイアログ選択個数判定未完成　01/27　実装予定
    //言語スピナーダイアログ表示用
    public void addLangsOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(getActivity())
                .setTitle("Lang Select")
                .setMultiChoiceItems(langs, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) checkedItems.add(which);
                        else checkedItems.remove((Integer) which);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String LangBox = "";
                        for (Integer i : checkedItems) {
                            int cnt = 0;
                            if(cnt < 4) {
                                cnt++;
                                String Lang = String.join(",", langs[i]);

                                LangBox = LangBox + " "+ Lang;
                            } else {
                                Toast.makeText(getApplicationContext(), "選択個数が多すぎます。　4つ以下にしてください！", Toast.LENGTH_LONG).show();
                            }
                            editSelectedLang.setText(LangBox);

                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    //職種スピナーダイアログ表示用
    public void addJobOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(getActivity())
                .setTitle("職種選択")
                .setMultiChoiceItems(job, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) checkedItems.add(which);
                        else checkedItems.remove((Integer) which);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String JobBox = "";
                        for (Integer i : checkedItems) {
                            int cnt = 0;
                            if(cnt < 4) {
                                cnt++;
                                String job  = String.join(",", MyPageFragment.this.job[i]);

                                JobBox = JobBox + " "+ job
                                ;
                            } else {
                                Toast.makeText(getApplicationContext(), "選択個数が多すぎます。　1つにしてください！", Toast.LENGTH_LONG).show();
                            }
                            editSelectedJob.setText(JobBox);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private class BtnAddAlermClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            /**
             * color and Text decisions
             */
            switch (v.getId()) {
                case R.id.mon_day:
                    if (mon_day_btn.getText().equals("◯")) {
                        mon_day_btn.setText("×");
                        /**色指定**/
                        mon_day_btn.setTextColor(Color.RED);
                        dayBtn = 1;
                    } else if (mon_day_btn.getText().equals("×")) {
                        mon_day_btn.setText("◯");
                        /**色指定**/
                        mon_day_btn.setTextColor(Color.GREEN);
                        dayBtn = 0;
                    }
                    break;
                case R.id.tues_day:
                    if (tues_day_btn.getText().equals("◯")) {
                        tues_day_btn.setText("×");
                        /**色指定**/
                        tues_day_btn.setTextColor(Color.RED);
                        dayBtn = 1;
                    } else if (tues_day_btn.getText().equals("×")) {
                        tues_day_btn.setText("◯");
                        /**色指定**/
                        tues_day_btn.setTextColor(Color.GREEN);
                        dayBtn = 0;
                    }
                    break;
                case R.id.wed_day:
                    if (wed_day_btn.getText().equals("◯")) {
                        wed_day_btn.setText("×");
                        /**色指定**/
                        wed_day_btn.setTextColor(Color.RED);
                        dayBtn = 1;
                    } else if (wed_day_btn.getText().equals("×")) {
                        wed_day_btn.setText("◯");
                        /**色指定**/
                        wed_day_btn.setTextColor(Color.GREEN);
                        dayBtn = 0;
                    }
                    break;
                case R.id.thurs_day:
                    if (thurs_day_btn.getText().equals("◯")) {
                        thurs_day_btn.setText("×");
                        /**色指定**/
                        thurs_day_btn.setTextColor(Color.RED);
                        dayBtn = 1;
                    } else if (thurs_day_btn.getText().equals("×")) {
                        thurs_day_btn.setText("◯");
                        /**色指定**/
                        thurs_day_btn.setTextColor(Color.GREEN);
                        dayBtn = 0;
                    }
                    break;
                case R.id.fri_day:
                    if (fri_day_btn.getText().equals("◯")) {
                        fri_day_btn.setText("×");
                        /**色指定**/
                        fri_day_btn.setTextColor(Color.RED);
                        dayBtn = 1;
                    } else if (fri_day_btn.getText().equals("×")) {
                        fri_day_btn.setText("◯");
                        /**色指定**/
                        fri_day_btn.setTextColor(Color.GREEN);
                        dayBtn = 0;
                    }
                    break;
                case R.id.saturs_day:
                    if (saturs_day_btn.getText().equals("◯")) {
                        saturs_day_btn.setText("×");
                        /**色指定**/
                        saturs_day_btn.setTextColor(Color.RED);
                        dayBtn = 1;
                    } else if (saturs_day_btn.getText().equals( "×")) {
                        saturs_day_btn.setText("◯");
                        /**色指定**/
                        saturs_day_btn.setTextColor(Color.GREEN);
                        dayBtn = 0;
                    }
                    break;
                case R.id.sun_day:
                    if (sun_day_btn.getText().equals("◯")) {
                        sun_day_btn.setText("×");
                        /**色指定**/
                        sun_day_btn.setTextColor(Color.RED);
                        dayBtn = 1;
                    } else if (sun_day_btn.getText().equals("×")) {
                        sun_day_btn.setText("◯");
                        /**色指定**/
                        sun_day_btn.setTextColor(Color.GREEN);
                        dayBtn = 0;
                    }
                    break;
            }
        }
    }
    //表示非表示切り替え
    private class Visibilitys implements View.OnClickListener {
        @Override
        public void onClick(View v){
            //編集モード
            if (v == btnEditProfile) {
                //国籍入力　言語選択　エリア選択　　日付入力　プロフィール選択　編集ボタン
                editSelectedJob.setVisibility(View.GONE);
                editSelectedLang.setVisibility(View.GONE);
                editSelectedArea.setVisibility(View.GONE);
                editDayTable.setVisibility(View.GONE);
                editTextProfile.setVisibility(View.GONE);
                btnEditProfile.setVisibility(View.GONE);
                //国籍　言語　プロフィール　登録ボタン
                //ガイドでなければ日付　エリア
                entSelectedJob.setVisibility(View.VISIBLE);
                entSelectLang.setVisibility(View.VISIBLE);
                entTextProfile.setVisibility(View.VISIBLE);
                btnEntryProfile.setVisibility(View.VISIBLE);
                if (gudieON == 1) {
                    entDayTable.setVisibility(View.VISIBLE);
                    entSelectArea.setVisibility(View.VISIBLE);
                }
            }

            //登録モード　登録ボタンクリック時
            if (v == btnEntryProfile) {
                //編集ボタン　国籍選択　言語選択　プロフィール選択
                // ガイドであれば　　エリア選択　日付選択
                editSelectedJob.setVisibility(View.VISIBLE);
                editSelectedLang.setVisibility(View.VISIBLE);
                editTextProfile.setVisibility(View.VISIBLE);
                btnEditProfile.setVisibility(View.VISIBLE);
                if (gudieON == 1) {
                    editDayTable.setVisibility(View.VISIBLE);
                    editSelectedArea.setVisibility(View.VISIBLE);

                }
                //国籍　言語　プロフィール　登録ボタン
                //ガイドでなければ日付　エリア
                entSelectedJob.setVisibility(View.GONE);
                entSelectLang.setVisibility(View.GONE);
                entTextProfile.setVisibility(View.GONE);
                btnEntryProfile.setVisibility(View.GONE);
                if (gudieON == 1) {
                    entDayTable.setVisibility(View.GONE);
                    entSelectArea.setVisibility(View.GONE);
                }
            }
        }
    }
}
