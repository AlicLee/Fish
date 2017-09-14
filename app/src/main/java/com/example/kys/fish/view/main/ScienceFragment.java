package com.example.kys.fish.view.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.kys.fish.R;
import com.example.kys.fish.model.ScienceData;
import com.example.kys.fish.presenter.impl.ScienceImpl;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Lee on 2017/9/9.
 */

public class ScienceFragment extends Fragment implements ScienceImpl.View {
    View mView;
    @InjectView(R.id.science_left_radio)
    RadioButton scienceLeftRadio;
    @InjectView(R.id.science_right_radio)
    RadioButton scienceRightRadio;
    @InjectView(R.id.science_list)
    RecyclerView scienceList;
    @InjectView(R.id.science_radiogroup)
    RadioGroup scienceRadiogroup;

    //private ScienceDBHelper scienceDBHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_science, container, false);
        initView(mView);
        ButterKnife.inject(this, mView);
        return mView;
    }

    private void initView(View mView) {
        scienceRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.science_left_radio) {

                } else {

                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void reLoadLeftListView(ArrayList<ScienceData> dataList) {

    }

    @Override
    public void reLoadRightListView(ArrayList<ScienceData> dataList) {

    }
}
