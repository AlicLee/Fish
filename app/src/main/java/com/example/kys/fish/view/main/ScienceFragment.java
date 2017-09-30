package com.example.kys.fish.view.main;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.kys.fish.R;
import com.example.kys.fish.adapter.ScienceAdapter;
import com.example.kys.fish.model.ScienceData;
import com.example.kys.fish.presenter.impl.ScienceImpl;

import java.util.ArrayList;
import java.util.List;

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
    private List<ScienceData> freshFishList = new ArrayList<>();
    private List<ScienceData> seaFishList = new ArrayList<>();
    ScienceAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_science, container, false);
        ButterKnife.inject(this, mView);
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(getActivity().getDatabasePath("science.db"), null);
        if (freshFishList.size() == 0) {
            Cursor cursor = database.rawQuery("select * from science where kind =?", new String[]{"0"});
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                ScienceData scienceData = new ScienceData();
                scienceData.setName(cursor.getString(cursor.getColumnIndex("name")));
                scienceData.setKind(cursor.getString(cursor.getColumnIndex("kind")));
                scienceData.setBrief(cursor.getString(cursor.getColumnIndex("brief")));
                scienceData.setType(cursor.getString(cursor.getColumnIndex("type")));
                scienceData.setDieaseControl(cursor.getString(cursor.getColumnIndex("dieaseControl")));
                scienceData.setBreadingPoint(cursor.getString(cursor.getColumnIndex("breadingPoint")));
                Log.e("fresh", "name" + scienceData.getName() + "type:" + scienceData.getType());
                freshFishList.add(scienceData);
            }
            cursor.close();
        }
        if (seaFishList.size() == 0) {
            Cursor cursor = database.rawQuery("SELECT * FROM science WHERE kind =?", new String[]{"1"});
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                ScienceData scienceData = new ScienceData();
                scienceData.setName(cursor.getString(cursor.getColumnIndex("name")));
                scienceData.setKind(cursor.getString(cursor.getColumnIndex("kind")));
                scienceData.setBrief(cursor.getString(cursor.getColumnIndex("brief")));
                scienceData.setType(cursor.getString(cursor.getColumnIndex("type")));
                scienceData.setDieaseControl(cursor.getString(cursor.getColumnIndex("dieaseControl")));
                scienceData.setBreadingPoint(cursor.getString(cursor.getColumnIndex("breadingPoint")));
                seaFishList.add(scienceData);
                Log.e("sea", "name" + scienceData.getName() + "type:" + scienceData.getType());
            }
            cursor.close();
        }
        initView(mView);
        return mView;
    }

    private void initView(View mView) {
        scienceLeftRadio.setChecked(true);
        //设置recyclerview进场动画
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);
//        animation.setDelay(1.0f);
        scienceList.setLayoutAnimation(animation);

        adapter = new ScienceAdapter(getActivity(), freshFishList);
        scienceList.setLayoutManager(new LinearLayoutManager(getActivity()));
        scienceList.setAdapter(adapter);
        scienceRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.science_left_radio) {
                    adapter = new ScienceAdapter(getActivity(), freshFishList);
                    scienceList.setAdapter(adapter);
                    scienceList.scheduleLayoutAnimation();
                    Log.e("ScienceFragment", "leftRadioClick");
                } else {
                    adapter = new ScienceAdapter(getActivity(), seaFishList);
                    scienceList.setAdapter(adapter);
                    scienceList.scheduleLayoutAnimation();
                    Log.e("ScienceFragment", "rightRadioClick");
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
