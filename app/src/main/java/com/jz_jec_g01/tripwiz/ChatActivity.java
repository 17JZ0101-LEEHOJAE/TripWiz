package com.jz_jec_g01.tripwiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {
    private ImageButton buttonImage;
    private static final int READ_REQUEST_CODE = 42;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        buttonImage = findViewById(R.id.buttonChatImage);
        buttonImage.setOnClickListener(new View.OnClickListener() {
            //端末標準ギャラリーを起動し、画像のURIを取得
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");

                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });

    }
}

