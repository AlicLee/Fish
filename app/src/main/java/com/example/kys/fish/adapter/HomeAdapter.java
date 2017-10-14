package com.example.kys.fish.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kys.fish.R;
import com.example.kys.fish.customwidget.CommentListTextView;
import com.example.kys.fish.model.Comment;
import com.example.kys.fish.model.Home;

import java.util.List;

import static com.example.kys.fish.config.AppConfig.login;

/**
 * Created by 张静静 on 2017/9/9.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context context;
    private List<Home> mSessionList;

    //定一个了一个内部类ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        RecyclerView imgRecyclerView;
        //        ImageView homeImage;
        TextView homeContent;
        CommentListTextView comment_List;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            imgRecyclerView = (RecyclerView) view.findViewById(R.id.imgRecyclerView);
//            homeImage = (ImageView) view.findViewById(R.id.home_adapter_img);
            homeContent = (TextView) view.findViewById(R.id.home_content);
            comment_List = (CommentListTextView) view.findViewById(R.id.comment_list);
        }
    }

    public HomeAdapter(List<Home> sessionList) {
        mSessionList = sessionList;

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
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_home, parent, false);
        return new ViewHolder(view);
    }

    /**
     * 用于对RecycleView子项数据进行赋值，在每个子项被滚到屏幕的时候执行
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Home home = mSessionList.get(position);
        holder.homeContent.setText(home.getSessionContent());
        String filesPath = home.getSessionFiles();
        String[] filePathList = filesPath.split(",");
        SessionImgAdapter adapter = new SessionImgAdapter(context, filePathList);
        holder.imgRecyclerView.setAdapter(adapter);
        List<Comment> commentList = home.getCommentList();
        for (int i = 0; i < commentList.size(); i++) {
            Comment comment = commentList.get(i);
            if (comment.getReceiversId() == login.getUserId()) {
                commentList.add(new Comment().setId(comment.getId()).setCommentContent(comment.getCommentContent()).setSendName(comment.getSendName()));
            } else {
                commentList.add(new Comment().setId(comment.getId()).setCommentContent(comment.getCommentContent()).setSendName(comment.getSendName()).setReceiversName(comment.getReceiversName()));
            }
        }
        holder.comment_List.setData(commentList);
//        Glide.with(context).load(login.getAvatarPath()).into(holder.homeImage);
//        holder.homeContent.setText(home.getContent());
        //使用glide来加载图片
//        Glide.with(context).load(home.getImageId()).into(holder.homeImage);
    }

    /**
     * 告诉RecycleView一共有多少项
     */
    @Override
    public int getItemCount() {
        return mSessionList.size();
    }
}

