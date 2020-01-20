package com.jz_jec_g01.tripwiz.ui.guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.jz_jec_g01.tripwiz.R;
import com.jz_jec_g01.tripwiz.SearchGuideActivity;
import com.jz_jec_g01.tripwiz.chats.ChatActivity;

import java.util.ArrayList;
import java.util.List;

//AndroidX
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.Arrays;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;


public class GuideFragment extends Fragment {
    // 表示する画像の名前（拡張子無し）
    private static final String[] names = {
            "katakuriko", "mai",
            "miki", "nagi", "saya",
            "toko"
    };
    private View v;

    private static final int requestCode  = 123;
    // RecyclerViewとAdapter
    private RecyclerView recyclerView = null;
    private GuideAdapter rAdapter = null;
    private Activity mActivity = null;
    private Button searchBtn;


    public interface RecyclerFragmentListener {
        void onRecyclerEvent();
    }
    // それぞれの画像ファイルをdarawableに入れます
    // ArrayListにコピーするためintからInteger型にしました
    private static final Integer[] photos = {
            R.drawable.katakuriko, R.drawable.mai, R.drawable.miki,
            R.drawable.nagi, R.drawable.saya, R.drawable.toko,
    };
    // Resource IDを格納するarray
    private List<Integer> imgList = new ArrayList<>();

    public GuideFragment() {

    }

    public static GuideFragment newInstance() {
        return new GuideFragment();
    }

    public void imagesList (){
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(rLayoutManager);


        // 配列をArrayListにコピー
        List<String> itemNames = new ArrayList<String>(Arrays.asList(names));
        List<Integer> itemImages = new ArrayList<Integer>(Arrays.asList(photos));

        // specify an adapter (see also next example)
        RecyclerView.Adapter rAdapter = new GuideAdapter(itemImages, itemNames);
        recyclerView.setAdapter(rAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL); // ここで横方向に設定
        recyclerView.setLayoutManager(manager);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_guide, container, false);

        // RecyclerViewの参照を取得
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        // レイアウトマネージャを設定(ここで縦方向の標準リストであることを指定)
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        searchBtn = v.findViewById(R.id.searchGuideBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchGuideActivity.class);
                getActivity().startActivityForResult(intent, 123 );
            }
        });
        imagesList();
        return v;
    }
}