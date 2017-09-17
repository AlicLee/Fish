package com.example.kys.fish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kys.fish.R;
import com.example.kys.fish.model.Comment;

import java.util.List;

import static android.R.attr.name;

/**
 * Created by kys on 2017/9/16.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {
    private int resourceId;
    public CommentAdapter(Context context, int textViewResourceId, List<Comment>objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Comment comment= getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.comment_item, null);

        TextView comment_content=(TextView)view.findViewById(R.id.comment_content);
        TextView comment_item_name = (TextView) view.findViewById(R.id.comment_item_name);
        comment_content.setText(comment.getContent());
       comment_item_name.setText(comment.getContent());
        return view;
    }
    }
