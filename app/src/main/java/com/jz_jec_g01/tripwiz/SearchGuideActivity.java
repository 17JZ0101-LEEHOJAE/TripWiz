package com.jz_jec_g01.tripwiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jz_jec_g01.tripwiz.ui.guide.GuideFragment;

import java.util.ArrayList;

public class SearchGuideActivity extends AppCompatActivity {
    private Button serchBtn;
    private TextView selectedArea;
    private TextView selectDays;
    private TextView selectedLang;
    private TextView selectOld;
    private TextView selectGender;
    private TextView selectCountry;
    //現在地取得後　指定の区をデフォルト選択
    private String[] areas = {"足立区", "荒川区", "板橋区", "江戸川区", "大田区", "葛飾区", "北区", "江東区", "品川区", "渋谷区", "新宿区", "杉並区",
            "墨田区", "世田谷区", "台東区", "中央区", "練馬区", "文京区", "港区", "目黒区"
    };
    private String[] days = {"月曜","火曜","水曜","木曜","金曜","土曜","日曜"};
    private String[] langs = {"日本", "アメリカ", "韓国", "台湾", "スペイン", "ドイツ"};
    private String[] old = {"10代","20代","30代","40代","50代"};
    private String[] gender = {"男性","女性"};
    private String[] country = {"日本", "アメリカ", "韓国", "台湾", "スペイン", "ドイツ"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_guide);
        //ボタン取得
        serchBtn = findViewById(R.id.searchBtn);
        selectedArea = findViewById(R.id.selectAreas);
        selectDays = findViewById(R.id.selectDays);
        selectedLang = findViewById(R.id.selectLangs);
        selectOld = findViewById(R.id.selectOld);
        selectGender = findViewById(R.id.selectGender);
        selectCountry = findViewById(R.id.selectCountry);
        //検索クリックアクション
        serchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //textView値をフラグメントに渡す専用変数
                String areas    = selectedArea.getText().toString();
                String days     = selectDays.getText().toString();
                String langs    = selectedLang.getText().toString();
                String olds     = selectOld.getText().toString();
                String genders  = selectGender.getText().toString();
                String countrys = selectCountry.getText().toString();

                //フラグメントに値を渡す
                Intent returnIntent = new Intent();                  //intent 設定

                returnIntent.putExtra("key.StringAreas",areas);  //returndataに１をセット
                returnIntent.putExtra("key.StringDays",days);
                returnIntent.putExtra("key.StringLangs",langs);
                returnIntent.putExtra("key.StringOlds",olds);
                returnIntent.putExtra("key.StringGenders",genders);
                returnIntent.putExtra("key.StringCountrys",countrys);
                setResult(RESULT_OK, returnIntent);
                finish();
//                Intent intent = new Intent(SearchGuideActivity.this, TamplateActivity.class);
//                intent.putExtra("goto", "GuideFragment");
//                startActivity(intent);
            }
        });
        //条件クリックアクション
        selectedArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAreaOnCheckBox();
            }
        });
        selectDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDaysOnCheckBox();
            }
        });
        selectedLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLangsOnCheckBox();
            }
        });
        selectOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOldOnCheckBox();
            }
        });
        selectGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGenderOnCheckBox();
            }
        });
        selectCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCountryOnCheckBox();
            }
        });
    }
    //条件ダイアログ
    public void addAreaOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(this)
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
                            String Area = String.join(",", areas[i]);
                            AreaBox = AreaBox + " " + Area;
                            selectedArea.setText(AreaBox);

                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    public void addDaysOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(this)
                .setTitle("Area Select")
                .setMultiChoiceItems(days, null, new DialogInterface.OnMultiChoiceClickListener() {
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
                            String Area = String.join(",", days[i]);
                            AreaBox = AreaBox + " " + Area;
                            selectDays.setText(AreaBox);

                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    public void addLangsOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(this)
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
    public void addOldOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(this)
                .setTitle("Area Select")
                .setMultiChoiceItems(old, null, new DialogInterface.OnMultiChoiceClickListener() {
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
                            String Area = String.join(",", old[i]);
                            AreaBox = AreaBox + " " + Area;
                            selectOld.setText(AreaBox);

                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    public void addGenderOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(this)
                .setTitle("Area Select")
                .setMultiChoiceItems(gender, null, new DialogInterface.OnMultiChoiceClickListener() {
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
                            String Area = String.join(",", gender[i]);
                            AreaBox = AreaBox + " " + Area;
                            selectGender.setText(AreaBox);

                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    public void addCountryOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(this)
            .setTitle("Area Select")
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
                    String AreaBox = "";
                    for (Integer i : checkedItems) {
                        String Area = String.join(",", country[i]);
                        AreaBox = AreaBox + " " + Area;
                        selectCountry.setText(AreaBox);

                    }
                }
            })
            .setNegativeButton("Cancel", null)
            .show();
    }
}
