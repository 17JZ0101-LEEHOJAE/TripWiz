package com.jz_jec_g01.tripwiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jz_jec_g01.tripwiz.model.User;

public class ProfilePageActivity extends AppCompatActivity {
    private User user;
    private TextView textViewLanguage;
    private TextView textViewArea;
    private TextView textViewUserName;
    private ImageView imageViewUser;
    private EditText editTextPr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        textViewLanguage = findViewById(R.id.textViewLanguage);
        textViewArea = findViewById(R.id.textArea);
        textViewUserName = findViewById(R.id.textViewUserName);
        imageViewUser = findViewById(R.id.imageViewUser);
        editTextPr = findViewById(R.id.editTextPr);
    }

    /**
     * ユーザー情報を取得するメソッド
     */
    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");//データを取得する
        textViewLanguage.setText(user.getLanguage());
        textViewArea.setText(user.getArea());
        textViewUserName.setText(user.getName());
        imageViewUser.setImageBitmap(user.getUserImage());
        editTextPr.setText(user.getPr());
        for (int i = 0; i <= 6; i++) {
            String textViewId = "textViewWeek" + i;
            int resID = getResources().getIdentifier(textViewId,"id",this.getPackageName());
            TextView textView = findViewById(resID);
            textView.setText(user.getGuideTime(i));
        }
    }
}
