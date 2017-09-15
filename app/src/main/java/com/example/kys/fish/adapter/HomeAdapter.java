package com.example.kys.fish.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kys.fish.R;
import com.example.kys.fish.model.Home;

import java.util.List;

/**
 * Created by 张静静 on 2017/9/9.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    private Context context;
    private List<Home> mHomeList;
    //定一个了一个内部类ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView homeImage;
        TextView homeContent;
        TextView comment_tv;


        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            homeImage=(ImageView)view.findViewById(R.id.home_img);
            homeContent=(TextView)view.findViewById(R.id.home_content);
            comment_tv=(TextView)view.findViewById(R.id.comment_tv);
          }
          }
        public HomeAdapter(List<Home>homeList){
        mHomeList=homeList;

        }

        /**继承RecyclerView.Adapter，必须重写以下三个方法*/


        /**创建ViewHolder实例，home_item布局传入，再ViewHolder返回*/
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context==null){
            context=parent.getContext();
        }
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_home,parent,false);
        return new ViewHolder(view);
        }

        /**用于对RecycleView子项数据进行赋值，在每个子项被滚到屏幕的时候执行*/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Home home=mHomeList.get(position);
        holder.homeContent.setText(home.getContent());
        holder.comment_tv.setText(home.getComment());
        Glide.with(context).load(home.getImageId()).into(holder.homeImage);//使用glide来加载图片
        }

        /**告诉RecycleView一共有多少项*/
        @Override
    public int getItemCount() {
        return mHomeList.size();
        }
        }
