package com.example.kys.fish.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kys.fish.R;
import com.example.kys.fish.presenter.RecyclerItemDeleteListener;
import com.example.kys.fish.view.home.AddSessionActivity;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ================================================
 * 作    者：ikkong （ikkong@163.com），修改 jeasonlzy（廖子尧）
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：微信图片选择的Adapter, 感谢 ikkong 的提交
 * ================================================
 */
public class ImagePickerAdapter extends RecyclerView.Adapter<ImagePickerAdapter.SelectedPicViewHolder> implements RecyclerItemDeleteListener.ItemTouchAdapter {
    private int maxImgCount;
    private Context mContext;
    public List<ImageItem> mData;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;
    private boolean isAdded;   //是否额外添加了最后一个图片
    private RecyclerView.LayoutManager manager;

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (isAdded && (fromPosition == mData.size() - 1 || toPosition == mData.size() - 1)) {
            return;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mData, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mData, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public void setImages(List<ImageItem> data) {
        mData = new ArrayList<>(data);
        if (getItemCount() < maxImgCount) {
            mData.add(new ImageItem());
            isAdded = true;
        } else {
            isAdded = false;
        }
        notifyDataSetChanged();
    }

    public List<ImageItem> getImages() {
        //由于图片未选满时，最后一张显示添加图片，因此这个方法返回真正的已选图片
        if (isAdded) return new ArrayList<>(mData.subList(0, mData.size() - 1));
        else return mData;
    }

    public ImagePickerAdapter(Context mContext, List<ImageItem> data, int maxImgCount, GridLayoutManager manager) {
        this.mContext = mContext;
        this.maxImgCount = maxImgCount;
        this.mInflater = LayoutInflater.from(mContext);
        this.manager = manager;
        setImages(data);
    }

    @Override
    public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedPicViewHolder(mInflater.inflate(R.layout.adapter_comment_image, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectedPicViewHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SelectedPicViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_img;
        private int clickPosition;

        public SelectedPicViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        }

        public void bind(SelectedPicViewHolder holder, final int position) {
            //设置条目的点击事件
//            itemView.setOnClickListener(this);
            //根据条目位置设置图片
            ImageItem item = mData.get(position);
            if (isAdded && position == getItemCount() - 1) {
                iv_img.setImageResource(R.drawable.selector_image_add);
                clickPosition = AddSessionActivity.IMAGE_ITEM_ADD;
            } else {
                ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext, item.path, iv_img, 0, 0);
                clickPosition = position;
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(view, clickPosition);
                    }
                }
            });
        }

    }
}