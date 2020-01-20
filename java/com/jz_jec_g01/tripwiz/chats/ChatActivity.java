package com.jz_jec_g01.tripwiz.chats;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.google.firebase.database.ValueEventListener;
import com.jz_jec_g01.tripwiz.R;

import java.util.Random;

public class ChatActivity extends ListActivity {
    private ImageButton buttonImage;
    private static final int READ_REQUEST_CODE = 42;

    // TODO: change this to your own Firebase URL
    private static final String FIREBASE_URL = "https://android-chat.firebaseio-demo.com";
    private String mUsername;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private ChatListAdapter mChatListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
//        // Make sure we have a mUsername
//        setupUsername();
//        setTitle("Chatting as " + mUsername);
//
//        // Setup our Firebase mFirebaseRef
//        mFirebaseRef = new Firebase(FIREBASE_URL).child("chat");
//
//        // Setup our input methods. Enter key on the keyboard or pushing the send button
//        EditText inputText = (EditText) findViewById(R.id.messageInput);
//        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//                    sendMessage();
//                }
//                return true;
//            }
//        });
//
//        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendMessage();
//            }
//        });
//
//        buttonImage = findViewById(R.id.buttonChatImage);
//        buttonImage.setOnClickListener(new View.OnClickListener() {
//            //端末標準ギャラリーを起動し、画像のURIを取得
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("image/*");
//
//                startActivityForResult(intent, READ_REQUEST_CODE);
//            }
//        });

    }
    @Override
    public void onStop() {
        super.onStop();
        mFirebaseRef.getRoot().child(".info/connected").removeEventListener((ChildEventListener) mConnectedListener);
        mChatListAdapter.cleanup();
    }

    private void setupUsername() {
        SharedPreferences prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
        mUsername = prefs.getString("username", null);
        if (mUsername == null) {
            Random r = new Random();
            // Assign a random user name if we don't have one saved.
            mUsername = "JavaUser" + r.nextInt(100000);
            prefs.edit().putString("username", mUsername).commit();
        }
    }

    private void sendMessage() {
        EditText inputText = (EditText) findViewById(R.id.messageInput);
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Chat chat = new Chat(input, mUsername);
            // Create a new, auto-generated child of that chat location, and save our chat data there
            mFirebaseRef.push().setValue(chat);
            inputText.setText("");
        }
    }
}

