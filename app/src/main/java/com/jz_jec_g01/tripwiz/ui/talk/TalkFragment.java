package com.jz_jec_g01.tripwiz.ui.talk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jz_jec_g01.tripwiz.R;


public class TalkFragment extends Fragment {

    private TalkViewModel talkViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        talkViewModel =
                ViewModelProviders.of(this).get(TalkViewModel.class);
        View root = inflater.inflate(R.layout.fragment_talk, container, false);
        final TextView textView = root.findViewById(R.id.text_talk);
        talkViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
    public class Message {
        public String author;
        public String content;

        public Message(String author, String content) {
            this.author = author;
            this.content = content;
        }
    }
    private DatabaseReference getMessageRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(MESSAGE_STORE); // MESSAGE_STORE = "message"
    }

    private void sendMessage(String content) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        getMessageRef().push().setValue(new Message(user.getUid(), content)).continueWith(new Continuation<Void, Object>() {
            @Override
            public Object then(@NonNull Task<Void> task) throws Exception {
                if (!task.isSuccessful()) {
                    Log.e("FugaFugaWorks","error", task.getException());
                    return null;
                }

                ((TextView) findViewById(R.id.txt_content)).setText("");
                return null;
            }
        });
    }

}