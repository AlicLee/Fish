package com.example.kys.fish.util;

import java.text.SimpleDateFormat;

/**
 * Created by Lee on 2017/10/8.
 */

public class DateUtil {
    public static String getCurrentTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sDateFormat.format(new java.util.Date());
    }
}
