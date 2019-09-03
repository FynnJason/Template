package com.fynnjason.template.utils;

import android.util.Log;

import com.fynnjason.template.BuildConfig;

/**
 * 作者：FynnJason
 * 时间：2019-09-03
 * 备注：
 */

public class LogUtils {
    public static void e(String string) {
        if (BuildConfig.DEBUG) {
            Log.e("LogUtils", string);
        }
    }
}
