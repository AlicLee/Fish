package com.example.kys.fish.view.science;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.example.kys.fish.BaseActivity;
import com.example.kys.fish.R;

import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Lee on 2017/10/1.
 */

public class ScienceImgDetailActivity extends BaseActivity {
    @InjectView(R.id.imageView)
    PhotoView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scienceimgdetail);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        String imgName = intent.getStringExtra("imageName");
        try {
            InputStream inputStream = getAssets().open(imgName);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.enable();
        // 获取图片信息
        Info info = imageView.getInfo();
        // 从一张图片信息变化到现在的图片，用于图片点击后放大浏览，具体使用可以参照demo的使用
        imageView.animaFrom(info);
//        // 获取/设置 最大缩放倍数
//        imageView.setMaxScale(4f);
    }
}
