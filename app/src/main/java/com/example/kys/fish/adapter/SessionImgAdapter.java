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
        //动态设置ImageView的宽高，根据自己每行item数量计算
        //dm.widthPixels-dip2px(20)即屏幕宽度-左右10dp+10dp=2换为px的宽度，最后/3得到每个item的宽高
//        if (filePathList.length > 0) {
//            int screenWidth = DensityUtil.initScreenWidth((Activity) context);
//            int viewWidth;
//            if (filePathList.length <= 3) {
//                viewWidth = (screenWidth - DensityUtil.dip2px(context, 20)) / filePathList.length;
//            } else {
//                viewWidth = (screenWidth - DensityUtil.dip2px(context, 20)) / 3;
//            }
//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(viewWidth, viewWidth);
//            view.setLayoutParams(lp);
//        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String path = ApiService.BASE_URL + filePathList[position];
        Glide.with(context).load(path).into(holder.img);
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
