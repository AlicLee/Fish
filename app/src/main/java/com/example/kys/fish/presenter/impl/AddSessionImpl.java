package com.example.kys.fish.presenter.impl;

import com.example.kys.fish.presenter.BasePresenter;
import com.example.kys.fish.view.BaseView;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.List;

/**
 * Created by Lee on 2017/10/7.
 */

public class AddSessionImpl {
    public interface View extends BaseView {
        void setLoadingIndicator(boolean active);

        void showAddSessionSuccess();

        void showAddSessionError();
    }

    public interface Presenter extends BasePresenter {
        void AddSession(String content, String time, List<ImageItem> imageItems);
    }
}
