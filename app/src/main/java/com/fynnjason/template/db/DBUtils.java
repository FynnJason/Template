package com.fynnjason.template.db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.fynnjason.template.gen.DaoMaster;
import com.fynnjason.template.gen.DaoSession;

/**
 * 作者：FynnJason
 * 时间：2019-09-03
 * 备注：
 */

public class DBUtils {

    private static final String DBName = "template.db";

    private DBUtils() {
    }

    private static class Holder {
        private static final DBUtils instance = new DBUtils();
    }

    public static DBUtils getInstance() {
        return Holder.instance;
    }

    /**
     * 初始化GreenDao
     *
     * @param app Application
     */
    public void init(Application app) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(app, DBName);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    private DaoSession mDaoSession;

    DaoSession getDaoSession() {
        return mDaoSession;
    }
}
