package com.example.kys.fish.view.science;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kys.fish.BaseActivity;
import com.example.kys.fish.R;
import com.example.kys.fish.customwidget.ExpandableTextView;
import com.example.kys.fish.model.ScienceData;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Lee on 2017/9/21.
 */

public class ScienceDetailActivity extends BaseActivity {

    ScienceData mScienceData;
    @InjectView(R.id.fishImg)
    ImageView fishImg;
    @InjectView(R.id.name_title)
    TextView nameTitle;
    @InjectView(R.id.name_content)
    TextView nameContent;
    @InjectView(R.id.type_title)
    TextView typeTitle;
    @InjectView(R.id.type_content)
    TextView typeContent;
    @InjectView(R.id.brief_title)
    TextView briefTitle;
    @InjectView(R.id.brief_content)
    ExpandableTextView briefContent;
    @InjectView(R.id.breadingPoint_title)
    TextView breadingPointTitle;
    @InjectView(R.id.breadingPoint_content)
    ExpandableTextView breadingPointContent;
    @InjectView(R.id.dieaseControl_title)
    TextView dieaseControlTitle;
    @InjectView(R.id.dieaseControl_content)
    ExpandableTextView dieaseControlContent;
    @InjectView(R.id.sciencedetail_leftback)
    ImageView sciencedetailLeftback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sciencedetail);
        ButterKnife.inject(this);
        mScienceData = (ScienceData) getIntent().getSerializableExtra("data");
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        nameContent.setText(mScienceData.getName());
        typeContent.setText(mScienceData.getType());
        dieaseControlContent.setText("\u3000\u3000\u3000" + mScienceData.getDieaseControl());
        briefContent.setText("\u3000\u3000\u3000" + mScienceData.getBrief());
        breadingPointContent.setText("\u3000\u3000\u3000" + mScienceData.getBreadingPoint());
        Glide.with(this).load(Uri.parse("file:///android_asset/") + mScienceData.getName().trim() + ".jpg").into(fishImg);
        fishImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScienceDetailActivity.this, ScienceImgDetailActivity.class);
                intent.putExtra("imageName",mScienceData.getName().trim() + ".jpg");
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.sciencedetail_leftback)
    public void onViewClicked() {
        this.finish();
    }
}
