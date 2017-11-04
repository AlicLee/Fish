package com.example.kys.fish.presenter.impl;

import com.example.kys.fish.presenter.BasePresenter;
import com.example.kys.fish.view.BaseView;

/**
 * Created by kys on 2017/10/31.
 */

public interface PersonImpl {
    interface View extends BaseView {
        void setLoadingIndicator(boolean active);

        void showPersonSuccess();

        void showPersonError();
    }

    interface Presenter extends BasePresenter {
        void person(String personalHead);
    }

}
