package com.example.kys.fish.presenter.impl;

import com.example.kys.fish.presenter.BasePresenter;
import com.example.kys.fish.view.BaseView;

/**
 * Created by Lee on 2017/9/6.
 */

public interface LoginImpl {
    interface View extends BaseView {
        void setLoadingIndicator(boolean active);

        void showLoginSuccess();

        void showLoginError();
    }

    interface Presenter extends BasePresenter {
        void login(String nickName, String passWord);
    }
}
