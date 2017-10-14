package com.example.kys.fish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.kys.fish.R;
import com.example.kys.fish.httpnetworks.ApiService;

/**
 * Created by Lee on 2017/10/13.
 */

public class SessionImgAdapter extends RecyclerView.Adapter<SessionImgAdapter.ViewHolder> {
    private String[] filePathList;
    private Context context;

    public SessionImgAdapter(Context context, String[] filePathList) {
        this.filePathList = filePathList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_sessionimg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // if()
        Glide.with(context).load(ApiService.BASE_URL+filePathList[position]).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return filePathList.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.adapter_session_img);
        }
    }
}
