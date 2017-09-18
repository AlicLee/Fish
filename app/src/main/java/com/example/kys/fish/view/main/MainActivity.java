package com.example.kys.fish.view.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kys.fish.BaseActivity;
import com.example.kys.fish.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Lee on 2017/9/9.
 */

public class MainActivity extends BaseActivity {
    @InjectView(R.id.fragment_content)
    FrameLayout fragmentContent;
    Fragment mFragment;
    HomeFragment mHomeFragment;
    ScienceFragment mScienceFragment;
    NewsFragment mNewsFragment;
    BusinessFragment mBusinessFragment;
    PersonFragment mPersonFragment;
    @InjectView(R.id.home_img)
    ImageView homeImg;
    @InjectView(R.id.home_txt)
    TextView homeTxt;
    @InjectView(R.id.home_layout)
    RelativeLayout homeLayout;
    @InjectView(R.id.science_img)
    ImageView scienceImg;
    @InjectView(R.id.science_txt)
    TextView scienceTxt;
    @InjectView(R.id.science_layout)
    RelativeLayout scienceLayout;
    @InjectView(R.id.business_img)
    ImageView businessImg;
    @InjectView(R.id.business_txt)
    TextView businessTxt;
    @InjectView(R.id.business_layout)
    FrameLayout businessLayout;
    @InjectView(R.id.news_img)
    ImageView newsImg;
    @InjectView(R.id.news_txt)
    TextView newsTxt;
    @InjectView(R.id.news_layout)
    RelativeLayout newsLayout;
    @InjectView(R.id.personal_img)
    ImageView personalImg;
    @InjectView(R.id.personal_txt)
    TextView personalTxt;
    @InjectView(R.id.personal_layout)
    RelativeLayout personalLayout;
    FragmentManager mFragmentManager;
    Fragment[] mFragments;
    TextView[] mTextViews;
    ImageView[] mImageViews;
    int[] mNormalDrawables = new int[]{R.mipmap.home_gray, R.mipmap.science_gray, R.mipmap.business_gray, R.mipmap.news_gray, R.mipmap.personal_gray};
    int[] mClickedDrawables = new int[]{R.mipmap.home_blue, R.mipmap.science_blue, R.mipmap.business_blue, R.mipmap.news_blue, R.mipmap.personal_blue};
    int SelectTabIndex = 0;//选中那个fragment
    //退出时的时间
    private long mExitTime;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mFragmentManager = getFragmentManager();
        mHomeFragment = new HomeFragment();
        mScienceFragment = new ScienceFragment();
        mBusinessFragment = new BusinessFragment();
        mNewsFragment = new NewsFragment();
        mPersonFragment = new PersonFragment();
        mFragments = new Fragment[]{mHomeFragment, mScienceFragment, mBusinessFragment, mNewsFragment, mPersonFragment};
        mTextViews = new TextView[]{homeTxt, scienceTxt, businessTxt, newsTxt, personalTxt};
        mImageViews = new ImageView[]{homeImg, scienceImg, businessImg, newsImg, personalImg};
        setSelectTab(0);
    }

    /**
     * 切换fragment
     *
     * @param i 第几个fragment
     */
    private void setSelectTab(int i) {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        if (mFragments[i].isAdded()) {
            mFragmentTransaction.show(mFragments[i]);
            if (SelectTabIndex != i)
                mFragmentTransaction.hide(mFragments[SelectTabIndex]);
        } else {
            mFragmentTransaction.add(R.id.fragment_content, mFragments[i]);
            if (SelectTabIndex != i)
                mFragmentTransaction.hide(mFragments[SelectTabIndex]);
            mFragment = mFragments[i];
        }
        setTabView(i);
        mFragmentTransaction.commit();
    }

    /**
     * 切换fragment视图
     *
     * @param i 第几个fragment
     */
    private void setTabView(int i) {
        //设置选中
        mImageViews[i].setImageResource(mClickedDrawables[i]);
        mTextViews[i].setTextColor(ContextCompat.getColor(this, R.color.blue));
        //清除选中
        if (SelectTabIndex != i) {
            mImageViews[SelectTabIndex].setImageResource(mNormalDrawables[SelectTabIndex]);
            mTextViews[SelectTabIndex].setTextColor(ContextCompat.getColor(this, R.color.gray_light));
            //更换selectTabIndex
            SelectTabIndex = i;
        }
    }

    @OnClick({R.id.home_layout, R.id.science_layout, R.id.business_layout, R.id.news_layout, R.id.personal_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_layout:
                setSelectTab(0);
                break;
            case R.id.science_layout:
                setSelectTab(1);
                break;
            case R.id.business_layout:
                setSelectTab(2);
                break;
            case R.id.news_layout:
                setSelectTab(3);
                break;
            case R.id.personal_layout:
                setSelectTab(4);
                break;
        }
    }

    /**
     * 返回键监听
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 连按两次退出程序
     */
    private void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }
}
