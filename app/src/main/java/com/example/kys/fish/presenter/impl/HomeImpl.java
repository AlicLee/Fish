package com.example.kys.fish.presenter.impl;

import com.example.kys.fish.model.Comment;
import com.example.kys.fish.model.Home;
import com.example.kys.fish.presenter.BasePresenter;
import com.example.kys.fish.view.BaseView;

import java.util.List;

/**
 * Created by Lee on 2017/10/9.
 */

public interface HomeImpl {
    interface View extends BaseView {
        void setLoadingIndicator(boolean active);

        void showGetAllChatSuccess(List<Home> homeList);

        void showGetAllChatError();

        void showAddOneChatSuccess();

        void showAddOneChatError();
    }

    interface Presenter extends BasePresenter {
//        void AddSession(String content, String time, List<ImageItem> imageItems);

        void getAllChat(int index);

        void addOneChat(Comment comment);
    }
}
