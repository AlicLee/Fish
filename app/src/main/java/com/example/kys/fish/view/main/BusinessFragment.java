package com.example.kys.fish.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kys.fish.R;

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
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
