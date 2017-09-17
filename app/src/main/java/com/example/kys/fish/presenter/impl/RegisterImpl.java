package com.example.kys.fish.presenter.impl;

import com.example.kys.fish.presenter.BasePresenter;
import com.example.kys.fish.view.BaseView;

import static android.R.attr.phoneNumber;

/**
 * Created by kys on 2017/9/16.
 */

public interface RegisterImpl {

        interface View extends BaseView {
            void setLoadingIndicator(boolean active);

            void showRegisterSuccess();

            void showRegisterError();
        }

        interface Presenter extends BasePresenter {
            void Register(String nickName,String name,String passWord);
        }

}
