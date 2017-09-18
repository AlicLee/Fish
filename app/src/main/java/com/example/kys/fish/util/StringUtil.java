package com.example.kys.fish.util;

/**
 * Created by Lee on 2017/9/18.
 */

public class StringUtil {
    /**
     * 判断字符串是否为空
     *
     * @param str 要判断的字符串
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
