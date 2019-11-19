package com.jz_jec_g01.tripwiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.jz_jec_g01.tripwiz.model.JsonRequestManager;
import com.jz_jec_g01.tripwiz.model.User;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfilePageActivity extends AppCompatActivity {
    private User user;
    private TextView textViewLanguage;
    private TextView textViewArea;
    private TextView textViewUserName;
    private ImageView imageViewUser;
    private EditText editTextPr;
    private  RatingBar rb;
    private TextView tvRating;
    private Button buttonEditProfile;
    JsonRequestManager manager;
    //final String url = "http://10.210.20.161"; //学校のパソコンのIPアドレス
    final String url = "http://www.jz.jec.ac.jp/17jzg01";
    final Request request = new Request.Builder().url(url).build();
    final OkHttpClient client = new OkHttpClient.Builder().build();

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
        buttonEditProfile = findViewById(R.id.buttonEditProfile);
        manager = JsonRequestManager.getInstance();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile);

        /**
         * データベースにアクセスし、データを取得する
         */
        manager.asyncGetMyPage(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Debug", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String content = response.body().string();
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() { //マイページ情報表示

                        imageViewUser.setImageBitmap(bitmap);
                        textViewUserName.setText(content);
                        textViewLanguage.setText(content);
                        textViewArea.setText(content);
                    }
                });
            }
        });



        /**
         * profile update event
         */
//        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                        .setDisplayName("NEWNAMEAFTERUPDATE. User")
//                        .setPhotoUri(Uri.parse("https://www.google.com/search?q=profile+picture"))
//                        .build();
//                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            // Log.d(TAG, "User profile updated.");
//                            Toast.makeText(getApplicationContext(), "User profile updated.", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//            }
//        });




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
//        user = new User();
//        user.setLanguage("japanese");
//        user.setArea("shinjuku");
//        user.setName("NIHON DENSHI");
//        user.setPr("hello");
//
//        textViewLanguage.setText(user.getLanguage());
//        textViewArea.setText(user.getArea());
//        textViewUserName.setText(user.getName());
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
