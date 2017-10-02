package com.example.kys.fish.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kys.fish.R;
import com.example.kys.fish.adapter.BusinessAdapter;
import com.example.kys.fish.model.BusinessData;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Lee on 2017/9/9.
 */

public class BusinessFragment extends Fragment {
    View mView;
    @InjectView(R.id.business_view)
    RecyclerView businessView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_business, container, false);
        ButterKnife.inject(this, mView);
        initView(mView);
        return mView;
    }

    /**
     * 初始化view
     *
     * @param mView
     */
    private void initView(View mView) {
        businessView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<BusinessData> businessData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            businessData.add(new BusinessData().setBrief("这是简介").setRating("4.5").setSelled("月售249份").setTitle("咸鱼店"));
        }
        BusinessAdapter adapter = new BusinessAdapter(getActivity(), businessData);
        businessView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
