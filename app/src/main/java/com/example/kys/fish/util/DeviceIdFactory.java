package com.example.kys.fish.util;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
 * Created by Lee on 2017/9/10.
 */

public class DeviceIdFactory {
    public static String getuniqueId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        String simSerialNumber = tm.getSimSerialNumber();
        String androidId = android.provider.Settings.Secure.getString(
                context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new
                UUID(androidId.hashCode(), ((long) imei.hashCode() << 32) | simSerialNumber.hashCode());
        String uniqueIuniqueId = deviceUuid.toString();
        return uniqueIuniqueId;
    }
}
