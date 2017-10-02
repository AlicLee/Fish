package com.example.kys.fish;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;

/**
 * Created by Lee on 2017/9/11.
 */

public class MyApplication extends Application {
    private final String TAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
//            Log.e(TAG, "e:" + e);
        }
        return 1;
    }
}
