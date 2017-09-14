package com.example.kys.fish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kys.fish.R;
import com.example.kys.fish.model.ScienceData;

import java.util.ArrayList;

/**
 * Created by Lee on 2017/9/10.
 */

public class ScienceAdapter extends RecyclerView.Adapter<ScienceAdapter.viewHolder> {
    private ArrayList<ScienceData> mScienceData;
    private Context mContext;

    public ScienceAdapter(Context context, ArrayList<ScienceData> scienceData) {
        this.mContext = context;
        this.mScienceData = scienceData;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_science, parent);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.briefd_text.setText("\u3000\u3000489418413515416310510351asdas456789");
    }



    @Override
    public int getItemCount() {
        return 5;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        ImageView fish_img;
        TextView name_text;
        TextView type_text;
        TextView briefd_text;

        public viewHolder(View itemView) {
            super(itemView);
            fish_img = (ImageView) itemView.findViewById(R.id.adapter_science_img);
            name_text = (TextView) itemView.findViewById(R.id.adapter_science_name);
            type_text = (TextView) itemView.findViewById(R.id.adapter_science_type);
            briefd_text = (TextView) itemView.findViewById(R.id.adapter_science_bried);
        }
    }
}
