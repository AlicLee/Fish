package com.example.kys.fish.view.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class HomeFragment extends Fragment {
    View mView;
    @InjectView(R.id.home_list_view)
    RecyclerView homeListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        initView(mView);
        ButterKnife.inject(this, mView);
        return mView;
    }

    /**
     *
     */
    private void initView(View view) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
