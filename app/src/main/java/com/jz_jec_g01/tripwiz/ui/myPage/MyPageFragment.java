package com.jz_jec_g01.tripwiz.ui.myPage;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.jz_jec_g01.tripwiz.R;

import java.util.ArrayList;


public class MyPageFragment extends Fragment {
    private View v;
    private TextView selectedLang;
    private TextView selectedArea;
    private TextView textViewRate;
    private Button mon_day_btn;
    private Button tues_day_btn;
    private Button wed_day_btn;
    private Button thurs_day_btn;
    private Button fri_day_btn;
    private Button saturs_day_btn;
    private Button sun_day_btn;
    private Button btnEditProfile;
    private String[] langs = {"日本", "アメリカ", "韓国", "台湾", "スペイン", "ドイツ"};
    private String[] areas = {"足立区", "荒川区", "板橋区", "江戸川区", "大田区", "葛飾区", "北区", "江東区", "品川区", "渋谷区", "新宿区", "杉並区",
            "墨田区", "世田谷区", "台東区", "中央区", "練馬区", "文京区", "港区", "目黒区"
    };
    RatingBar ratingBar;

    private Activity mActivity = null;
    int dayBtn = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_my_page, container, false);
        //評価レート
        textViewRate = v.findViewById(R.id.textViewRate);
        ratingBar = v.findViewById(R.id.ratingBar);
        //言語とエリア取得
        selectedLang = v.findViewById(R.id.selectLang);
        selectedArea = v.findViewById(R.id.selectArea);
        //曜日ボタン取得
        mon_day_btn = v.findViewById(R.id.mon_day);
        tues_day_btn = v.findViewById(R.id.tues_day);
        wed_day_btn = v.findViewById(R.id.wed_day);
        thurs_day_btn = v.findViewById(R.id.thurs_day);
        fri_day_btn = v.findViewById(R.id.fri_day);
        saturs_day_btn = v.findViewById(R.id.saturs_day);
        sun_day_btn = v.findViewById(R.id.sun_day);
        //プロフィールボタン取得
        btnEditProfile = v.findViewById(R.id.buttonEditProfile);

        //プロフィール編集登録
        btnEditProfile.setOnClickListener(new btnMyPegeClickListener());
        setRatingBar();
        return v;
    }
    public void setRatingBar() {
        double stars = 2.2;
        textViewRate.setText(String.valueOf(stars));
        Log.d("" + stars, "setRatingBar: ");
        // 星の数を７に設定
        ratingBar.setNumStars(5);
        // レートの変更を可能にする
        ratingBar.setIsIndicator(false);
        // レートが加減される時のステップ幅を0.3に設定
        ratingBar.setStepSize((float) 0.3);
        // レートの初期値を2.0に設定
        ratingBar.setRating((float) stars);
    }
    private class btnselecteds implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.selectLang) {
                addLangsOnCheckBox();
            }else {
                addAreaOnCheckBox();
            }

        }
    }
    public void addLangsOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(getActivity())
                .setTitle("Lang Lelect")
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
                            }
                            selectedLang.setText(LangBox);

                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    public void addAreaOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(getActivity())
                .setTitle("Area Select")
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
                            }
                            selectedLang.setText(AreaBox);

                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    private class btnMyPegeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (btnEditProfile.getText() == "編集") {
                btnEditProfile.setText("登録");
                /**色指定**/
                btnEditProfile.setBackgroundColor(Color.CYAN);
                //言語とエリア選択
                selectedLang.setOnClickListener(new btnselecteds());
                selectedArea.setOnClickListener(new btnselecteds());
                //曜日選択
                mon_day_btn.setOnClickListener(new BtnAddAlermClickListener());
                tues_day_btn.setOnClickListener(new BtnAddAlermClickListener());
                wed_day_btn.setOnClickListener(new BtnAddAlermClickListener());
                thurs_day_btn.setOnClickListener(new BtnAddAlermClickListener());
                fri_day_btn.setOnClickListener(new BtnAddAlermClickListener());
                saturs_day_btn.setOnClickListener(new BtnAddAlermClickListener());
                sun_day_btn.setOnClickListener(new BtnAddAlermClickListener());
            } else {
                btnEditProfile.setText("編集");
                /**色指定**/
                btnEditProfile.setBackgroundColor(Color.WHITE);
                //データベース接続処理
            }

        }
    }

    /**
     * 案内可能曜日○×判定Switch
     */
    private class BtnAddAlermClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            /**
             * color and Text decisions
             */
            switch (v.getId()) {
                case R.id.mon_day:
                    if (dayBtn == 0) {
                        mon_day_btn.setText("×");
                        /**色指定**/
                        mon_day_btn.setTextColor(Color.RED);
                        dayBtn = 1;
                    } else if (dayBtn == 1) {
                        mon_day_btn.setText("◯");
                        /**色指定**/
                        mon_day_btn.setTextColor(Color.GREEN);
                        dayBtn = 0;
                    }
                    break;
                case R.id.tues_day:
                    if (dayBtn == 0) {
                        tues_day_btn.setText("×");
                        /**色指定**/
                        tues_day_btn.setTextColor(Color.RED);
                        dayBtn = 1;
                    } else if (dayBtn == 1) {
                        tues_day_btn.setText("◯");
                        /**色指定**/
                        tues_day_btn.setTextColor(Color.GREEN);
                        dayBtn = 0;
                    }
                    break;
                case R.id.wed_day:
                    if (dayBtn == 0) {
                        wed_day_btn.setText("×");
                        /**色指定**/
                        wed_day_btn.setTextColor(Color.RED);
                        dayBtn = 1;
                    } else if (dayBtn == 1) {
                        wed_day_btn.setText("◯");
                        /**色指定**/
                        wed_day_btn.setTextColor(Color.GREEN);
                        dayBtn = 0;
                    }
                    break;
                case R.id.thurs_day:
                    if (dayBtn == 0) {
                        thurs_day_btn.setText("×");
                        /**色指定**/
                        thurs_day_btn.setTextColor(Color.RED);
                        dayBtn = 1;
                    } else if (dayBtn == 1) {
                        thurs_day_btn.setText("◯");
                        /**色指定**/
                        thurs_day_btn.setTextColor(Color.GREEN);
                        dayBtn = 0;
                    }
                    break;
                case R.id.fri_day:
                    if (dayBtn == 0) {
                        fri_day_btn.setText("×");
                        /**色指定**/
                        fri_day_btn.setTextColor(Color.RED);
                        dayBtn = 1;
                    } else if (dayBtn == 1) {
                        fri_day_btn.setText("◯");
                        /**色指定**/
                        fri_day_btn.setTextColor(Color.GREEN);
                        dayBtn = 0;
                    }
                    break;
                case R.id.saturs_day:
                    if (dayBtn == 0) {
                        saturs_day_btn.setText("×");
                        /**色指定**/
                        saturs_day_btn.setTextColor(Color.RED);
                        dayBtn = 1;
                    } else if (dayBtn == 1) {
                        saturs_day_btn.setText("◯");
                        /**色指定**/
                        saturs_day_btn.setTextColor(Color.GREEN);
                        dayBtn = 0;
                    }
                    break;
                case R.id.sun_day:
                    if (dayBtn == 0) {
                        sun_day_btn.setText("×");
                        /**色指定**/
                        sun_day_btn.setTextColor(Color.RED);
                        dayBtn = 1;
                    } else if (dayBtn == 1) {
                        sun_day_btn.setText("◯");
                        /**色指定**/
                        sun_day_btn.setTextColor(Color.GREEN);
                        dayBtn = 0;
                    }
                    break;
            }
        }
    }
}
