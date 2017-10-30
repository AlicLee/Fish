package com.example.kys.fish.view.business;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kys.fish.R;
import com.example.kys.fish.customwidget.ExpandableTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class BusinessDetailActivity extends AppCompatActivity {

    @InjectView(R.id.businessdetail_leftback)
    ImageView businessdetailLeftback;
    @InjectView(R.id.business_title_tv)
    TextView businessTitleTv;
    @InjectView(R.id.name_content)
    TextView nameContent;
    @InjectView(R.id.breedingCompany_introduce)
    TextView breedingCompanyIntroduce;
    @InjectView(R.id.type_content)
    TextView typeContent;
    @InjectView(R.id.fishImg)
    ImageView fishImg;
    @InjectView(R.id.breedVarieties_introduce)
    TextView breedVarietiesIntroduce;
    @InjectView(R.id.brief_content)
    ExpandableTextView briefContent;
    @InjectView(R.id.delivery_title)
    TextView deliveryTitle;
    @InjectView(R.id.breadingPoint_content)
    ExpandableTextView breadingPointContent;
    @InjectView(R.id.contact_title)
    TextView contactTitle;
    @InjectView(R.id.control_content)
    ExpandableTextView controlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_detail);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.businessdetail_leftback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.businessdetail_leftback:
                this.finish();
                break;

        }
    }
}
