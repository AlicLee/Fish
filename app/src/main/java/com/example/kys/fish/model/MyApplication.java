package com.example.kys.fish.model;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kys on 2017/10/13.
 */

public class MyApplication extends Application {

    //使用list来保存每一个activity
    private List<Activity> mList = new LinkedList<Activity>();

    //为了实现每次使用该类时不创建新的对象而创建的静态对象
    private static MyApplication instance;

    //构造方法
    private MyApplication() {

    }

    //实例化一次
    public synchronized static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    //关闭每一个list里的Activity
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    //杀进程
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

}