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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.jz_jec_g01.tripwiz.R;

import java.util.ArrayList;


public class MyPageFragment extends Fragment {
    private View v;
    private LinearLayout selectLnagsBox;
    private LinearLayout selectAreaBox;
    private TextView editSelectedLang;
    private TextView editSelectedArea;
    private TextView editSelectedCountry;
    private TextView entSelectedCountry;
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
    private TableRow entDayTable;
    private TableRow editDayTable;
    private TableLayout dayTableLayout;
    private String[] country = {"日本", "アメリカ", "韓国", "台湾", "スペイン", "ドイツ"};
    private String[] langs = {"日本", "アメリカ", "韓国", "台湾", "スペイン", "ドイツ"};
    private String[] areas = {"足立区", "荒川区", "板橋区", "江戸川区", "大田区", "葛飾区", "北区", "江東区", "品川区", "渋谷区", "新宿区", "杉並区",
            "墨田区", "世田谷区", "台東区", "中央区", "練馬区", "文京区", "港区", "目黒区"
    };
    private Switch SwitchUser;
    RatingBar ratingBar;

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
        editSelectedCountry = v.findViewById(R.id.edit_selectCountry);
        editSelectedLang = v.findViewById(R.id.edit_selectLang);
        editSelectedArea = v.findViewById(R.id.edit_selectArea);
        //出力系
        entSelectedCountry =v.findViewById(R.id.entry_selectCountry);
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
        editSelectedCountry.setOnClickListener(new btnselecteds());
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
        setRatingBar();
        return v;
    }
    public void setRatingBar() {
        double stars = 2.2;
        textViewRate.setText(String.valueOf(stars));
        Log.d("" + stars, "setRatingBar: ");
        // 星の数を７に設定
        ratingBar.setNumStars(5);
//        // レートの変更を可能にする
//        ratingBar.setIsIndicator(false);
        // レートが加減される時のステップ幅を0.3に設定
        ratingBar.setStepSize((float) 0.3);
        // レートの初期値を2.0に設定
        ratingBar.setRating((float) stars);
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
            }else if (v.getId() == R.id.edit_selectCountry)
                addCountryOnCheckBox();
            Log.d("国籍ダイアログ表示" , "onClick: ");
        }
    }
    //エリアスピナーダイアログ表示用
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
                            editSelectedLang.setText(AreaBox);

                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
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
                            }
                            editSelectedLang.setText(LangBox);

                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    //国籍スピナーダイアログ表示用
    public void addCountryOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(getActivity())
                .setTitle("Lang Select")
                .setMultiChoiceItems(country, null, new DialogInterface.OnMultiChoiceClickListener() {
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
                        String CountryBox = "";
                        for (Integer i : checkedItems) {
                            int cnt = 0;
                            if(cnt < 4) {
                                cnt++;
                                String Country  = String.join(",", country[i]);

                                CountryBox = CountryBox + " "+ Country
                                ;
                            }
                            editSelectedCountry.setText(CountryBox);
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
                editSelectedCountry.setVisibility(View.GONE);
                editSelectedLang.setVisibility(View.GONE);
                editSelectedArea.setVisibility(View.GONE);
                editDayTable.setVisibility(View.GONE);
                editTextProfile.setVisibility(View.GONE);
                btnEditProfile.setVisibility(View.GONE);
                //国籍　言語　プロフィール　登録ボタン
                //ガイドでなければ日付　エリア
                entSelectedCountry.setVisibility(View.VISIBLE);
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
                editSelectedCountry.setVisibility(View.VISIBLE);
                editSelectedLang.setVisibility(View.VISIBLE);
                editTextProfile.setVisibility(View.VISIBLE);
                btnEditProfile.setVisibility(View.VISIBLE);
                if (gudieON == 1) {
                    editDayTable.setVisibility(View.VISIBLE);
                    editSelectedArea.setVisibility(View.VISIBLE);

                }
                //国籍　言語　プロフィール　登録ボタン
                //ガイドでなければ日付　エリア
                entSelectedCountry.setVisibility(View.GONE);
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
