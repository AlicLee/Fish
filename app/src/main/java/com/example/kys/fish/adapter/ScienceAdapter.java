package com.example.kys.fish.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kys.fish.R;
import com.example.kys.fish.model.ScienceData;
import com.example.kys.fish.view.science.ScienceDetailActivity;

import java.util.List;

/**
 * Created by Lee on 2017/9/10.
 */

public class ScienceAdapter extends RecyclerView.Adapter<ScienceAdapter.viewHolder> {
    private List<ScienceData> mScienceData;
    private Context mContext;

    public ScienceAdapter(Context context, List<ScienceData> scienceData) {
        this.mContext = context;
        this.mScienceData = scienceData;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_science, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        holder.briefd_text.setText("\u3000\u3000\u3000" + mScienceData.get(position).getBrief());
        Glide.with(mContext).load(Uri.parse("file:///android_asset/") + mScienceData.get(position).getName().trim() + ".jpg").into(holder.fish_img);
        holder.type_text.setText(mScienceData.get(position).getType());
        holder.name_text.setText(mScienceData.get(position).getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ScienceDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", mScienceData.get(position));
                intent.setClass(mContext, ScienceDetailActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mScienceData.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        ImageView fish_img;
        TextView name_text;
        TextView type_text;
        TextView briefd_text;
        CardView cardView;

        public viewHolder(View itemView) {
            super(itemView);
            fish_img = (ImageView) itemView.findViewById(R.id.adapter_science_img);
            name_text = (TextView) itemView.findViewById(R.id.adapter_science_name);
            type_text = (TextView) itemView.findViewById(R.id.adapter_science_type);
            briefd_text = (TextView) itemView.findViewById(R.id.adapter_science_bried);
            cardView = (CardView) itemView.findViewById(R.id.adapter_science_card);
        }
    }

}
