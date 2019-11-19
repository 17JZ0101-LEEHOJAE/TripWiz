package com.jz_jec_g01.tripwiz.ui.guide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.jz_jec_g01.tripwiz.R;



public class GuideFragment extends Fragment {

    private EditText editText;
    private Button btnSearch;
    private GuideViewModel guideViewModel;

//    public static GuideFragment newInstace(int resourceId) {
//        GuideFragment fragment = new GuideFragment();
//        searchWord args = new searchWord();
//    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide,container,false);

    }

}