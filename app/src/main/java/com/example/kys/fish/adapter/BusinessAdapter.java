package com.example.kys.fish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.kys.fish.R;
import com.example.kys.fish.model.BusinessData;

import java.util.List;

/**
 * Created by Lee on 2017/9/27.
 */

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.viewHolder> {
    private Context mContext;
    private List<BusinessData> businessDataList;

    public BusinessAdapter(Context context, List<BusinessData> businessDataList) {
        this.mContext = context;
        this.businessDataList = businessDataList;
    }

    @Override
    public BusinessAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_business, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.logo.setImageResource(R.mipmap.ic_launcher);
        holder.title.setText(businessDataList.get(position).getTitle());
        holder.selled.setText(businessDataList.get(position).getSelled());
        holder.ratingText.setText(businessDataList.get(position).getRating());
        holder.brief.setText(businessDataList.get(position).getBrief());
        holder.ratingbar.setRating(Float.valueOf(businessDataList.get(position).getRating()));
    }


    @Override
    public int getItemCount() {
        return businessDataList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        ImageView logo;
        TextView title, ratingText, selled, brief;
        RatingBar ratingbar;

        public viewHolder(View itemView) {
            super(itemView);
            logo = (ImageView) itemView.findViewById(R.id.business_logo);
            title = (TextView) itemView.findViewById(R.id.business_title);
            ratingbar = (RatingBar) itemView.findViewById(R.id.business_ratingbar);
            ratingText = (TextView) itemView.findViewById(R.id.business_ratingText);
            selled = (TextView) itemView.findViewById(R.id.business_selled);
            brief = (TextView) itemView.findViewById(R.id.business_brief);
        }
    }

}
