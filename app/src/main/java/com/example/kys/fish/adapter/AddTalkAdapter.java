package com.example.kys.fish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kys.fish.R;
import com.example.kys.fish.model.Add;

import java.util.List;

/**
 * Created by kys on 2017/9/19.
 */
public class AddTalkAdapter extends RecyclerView.Adapter<AddTalkAdapter.ViewHolder> {
    private Context context;
    private List<Add> mAddList;
    int mMaxNum;

    //定一个了一个内部类ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView adapter_img;
        TextView adapter_tv;


        public ViewHolder(View view) {
            super(view);
            adapter_img = (ImageView) view.findViewById(R.id.adapter_img);
            adapter_tv = (TextView) view.findViewById(R.id.adapter_tv);

        }
    }

    public AddTalkAdapter(List<Add> addList) {
        mAddList = addList;
    }

    /**继承RecyclerView.Adapter，必须重写以下三个方法*/


    /**
     * 创建ViewHolder实例，home_item布局传入，再ViewHolder返回
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_add, parent, false);
        return new ViewHolder(view);
    }

    /**
     * 用于对RecycleView子项数据进行赋值，在每个子项被滚到屏幕的时候执行
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//           Add add = mAddList.get(position);
//            holder.adapter_tv.setText(add.getContentText());
//            //使用glide来加载图片
//            Glide.with(context).load(add.getImageId()).into(holder.adapter_img);
        if (mAddList.size() < mMaxNum) {
            if (mAddList.size() == 0) {
                holder.adapter_tv.setVisibility(View.VISIBLE);
                holder.adapter_img.setVisibility(View.GONE);
            } else {
                return;
            }
        }
    }

    /**
     * 告诉RecycleView一共有多少项
     */
    @Override
    public int getItemCount() {
//            return mAddList.size();
//        }
        if (mAddList == null || mAddList.size() == 0) {
            return 1;
        } else if (mAddList.size() < mMaxNum) {

            return mAddList.size() + 1;
        } else {
            return mAddList.size();
        }
    }
}
