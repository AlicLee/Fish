package com.example.kys.fish.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.kys.fish.R;
import com.example.kys.fish.adapter.HomeAdapter;
import com.example.kys.fish.model.Comment;
import com.example.kys.fish.model.Home;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Lee on 2017/9/9.
 */

public class HomeFragment extends Fragment {
    View mView;//当前视图
    @InjectView(R.id.home_list_view)
    RecyclerView homeListView;
    @InjectView(R.id.search_img)
    ImageView searchImg;
    @InjectView(R.id.search_edit)
    EditText searchEdit;
    @InjectView(R.id.delete_img)
    ImageView deleteImageView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private Home[] homes = {new Home("所谓成长，就是逼着你一个人，踉踉跄跄的受伤，跌跌撞撞的坚强。", R.mipmap.home_one),
            new Home("很多人一生只做了“等待”与“后悔”两件事，合起来叫“来不及”。", R.mipmap.home_two),
            new Home("生活不可能像你想象得那么好，但也不会像你想象得那么糟,我觉得人的脆弱和坚强都超乎自己的想象.", R.mipmap.home_three)};
    private List<Home> homeList = new ArrayList<>();
    private HomeAdapter adapter;
    private List<Comment> commentList = new ArrayList<Comment>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, mView);
        initHomes();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());//getActivity().getApplicationContext()解决了兼容性问题，进行强制转换
        homeListView.setLayoutManager(layoutManager);
        adapter = new HomeAdapter(homeList);
        homeListView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }
        });
        return mView;
    }


    /**
     * 从刚才定义的数组中随机挑选一个放到homeList中，为了时数据多，这里设为一个循环
     */
    private void initHomes() {
        homeList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(homes.length);
            homeList.add(homes[index]);
        }
    }


    /**
     * 清空搜索框内容
     */
    @OnClick({R.id.search_img, R.id.delete_img, R.id.search_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delete_img:
                searchEdit.setText("");
                break;
        }
    }

//    private void initComment() {
//        Comment apple = new Comment("Apple", "123");
//        commentList.add(apple);
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
