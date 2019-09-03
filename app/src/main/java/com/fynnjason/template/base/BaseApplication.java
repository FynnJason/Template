package com.fynnjason.template.base;

import android.app.Application;

import com.fynnjason.template.db.DBUtils;
import com.fynnjason.template.network.OkGoUtils;

import me.jessyan.autosize.AutoSizeConfig;

/**
 * 作者：FynnJason
 * 时间：2019-09-02
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkGoUtils.getInstance().init(this);
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(true)
                .setSupportSP(true);
        DBUtils.getInstance().init(this);
    }
}
