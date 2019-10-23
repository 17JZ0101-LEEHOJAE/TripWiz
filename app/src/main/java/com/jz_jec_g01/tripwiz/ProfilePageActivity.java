package com.jz_jec_g01.tripwiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jz_jec_g01.tripwiz.model.User;

public class ProfilePageActivity extends AppCompatActivity {
    private User user;
    private TextView textViewLanguage;
    private TextView textViewArea;
    private TextView textViewUserName;
    private ImageView imageViewUser;
    private EditText editTextPr;
    private  RatingBar rb;
    private TextView tvRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        textViewLanguage = findViewById(R.id.textViewLanguage);
        textViewArea = findViewById(R.id.textViewArea);
        textViewUserName = findViewById(R.id.textViewUserName);
        imageViewUser = findViewById(R.id.imageViewUser);
        editTextPr = findViewById(R.id.editTextPr);
        rb = findViewById(R.id.ratingBar);

        //rb.setRating((float) 2.0);

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                // レートが変更された際の処理
                tvRating.setText(String.valueOf(ratingBar.getRating()));
                //レートの量によってテキストのサイズを変える
                tvRating.setTextSize(ratingBar.getRating() * 5);
            }
        });
   }

    /**
     * ユーザー情報を取得するメソッド
     */
    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");//データを取得する
        //デバッグ用データ
        user = new User();
        user.setLanguage("japanese");
        user.setArea("shinjuku");
        user.setName("NIHON DENSHI");
        user.setPr("hello");

        textViewLanguage.setText(user.getLanguage());
        textViewArea.setText(user.getArea());
        textViewUserName.setText(user.getName());
       // imageViewUser.setImageBitmap(user.getUserImage());
        editTextPr.setText(user.getPr());
        for (int i = 0; i <= 6; i++) {
            String textViewId = "textViewWeek" + i;
            int resID = getResources().getIdentifier(textViewId,"id",this.getPackageName());
            TextView textView = findViewById(resID);
            //textView.setText(user.getGuideTime(i));
        }
    }
}
