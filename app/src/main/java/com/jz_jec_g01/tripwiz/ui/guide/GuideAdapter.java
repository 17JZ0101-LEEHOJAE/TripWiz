package com.jz_jec_g01.tripwiz.ui.guide;

//AndroidX
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.v7.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.jz_jec_g01.tripwiz.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.ViewHolder> {

    private List<String> iImages;
    private List<String> iNames;
    final String url = "http://10.210.20.161";
//    final String url = "http://www.jz.jec.ac.jp/17jzg01";
    final Request request = new Request.Builder().url(url).build();
    final OkHttpClient client = new OkHttpClient.Builder().build();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        private ImageView imageView;
        private TextView textView;

        ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.user_image_view);
            textView = v.findViewById(R.id.user_name_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    GuideAdapter(List<String> itemImages, List<String> itemNames) {
        this.iImages = itemImages;
        this.iNames = itemNames;
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_guide, parent, false);

        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        client.newCall(request).enqueue(new Callback() {
            final Handler mHandler = new Handler(Looper.getMainLooper());

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("接続","失敗");
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String imgUrl = url + "/image/" + iImages.get(position);
                        Log.d("URL", imgUrl);

                        Request request = new Request.Builder()
                                .url(imgUrl)
                                .get()
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                                        holder.imageView.setImageBitmap(bitmap);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        holder.textView.setText(iNames.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return iNames.size();
    }
}
