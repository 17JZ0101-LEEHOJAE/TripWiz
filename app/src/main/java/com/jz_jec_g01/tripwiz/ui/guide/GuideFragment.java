package com.jz_jec_g01.tripwiz.ui.guide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.jz_jec_g01.tripwiz.R;

public class GuideFragment extends Fragment {
    private TextView text_sample;
    private Button setbutton;


    public GuideFragment() {

    }

    public static GuideFragment newInstance() {
        return new GuideFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 第３引数のbooleanは"container"にreturnするViewを追加するかどうか
        //trueにすると最終的なlayoutに再度、同じView groupが表示されてしまうのでfalseでOKらしい
        View v = inflater.inflate(R.layout.fragment_guide, container, false);
        text_sample = v.findViewById(R.id.text_sample);
        // ボタンを取得して、ClickListenerをセット
        setbutton = (Button) v.findViewById(R.id.buttonSet);
        setbutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dispBody = "こんにちは";
                EditText body = text_sample.findViewById(R.id.text_sample);
                // 1番目:セットする文字列、2番目:TextView.BufferType.NORMAL
                body.setText(dispBody, TextView.BufferType.NORMAL);
            }
        });
        return v;
    }



}