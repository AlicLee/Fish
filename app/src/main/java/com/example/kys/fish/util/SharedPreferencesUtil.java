package com.example.kys.fish.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lee on 2017/9/10.
 */

public class SharedPreferencesUtil {
    private Context mContext;
    SharedPreferences sharedPreferences;

    public SharedPreferencesUtil(Context context) {
        this.mContext = context;
        sharedPreferences = mContext.getSharedPreferences("Fish", Context.MODE_PRIVATE);
    }

    public void saveObject(String saveName, String saveValue) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(saveName, saveValue);
        editor.apply();
    }

    public String readObject(String saveName) {
        return sharedPreferences.getString(saveName, "");
    }
}
