package com.example.kys.fish.presenter.impl;

import com.example.kys.fish.model.ScienceData;
import com.example.kys.fish.presenter.BasePresenter;
import com.example.kys.fish.view.BaseView;

import java.util.ArrayList;

/**
 * Created by Lee on 2017/9/12.
 */

public interface ScienceImpl {
    interface View extends BaseView {
        void reLoadLeftListView(ArrayList<ScienceData> dataList);

        void reLoadRightListView(ArrayList<ScienceData> dataList);
    }

    interface Presenter extends BasePresenter {
        void setLeftListData();

        void setRightListData();
    }
}
