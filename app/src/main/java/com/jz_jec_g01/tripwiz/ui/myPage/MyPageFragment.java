package com.jz_jec_g01.tripwiz.ui.myPage;

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

public class MyPageFragment extends Fragment {

    private MyPageViewModel myPageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myPageViewModel =
                ViewModelProviders.of(this).get(MyPageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_page, container, false);
        final TextView textView = root.findViewById(R.id.text_myPage);
        myPageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}