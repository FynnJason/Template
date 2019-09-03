package com.fynnjason.template.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.fynnjason.template.R;
import com.fynnjason.template.utils.LogUtils;
import com.gyf.immersionbar.ImmersionBar;


import butterknife.ButterKnife;
/**
 * 作者：FynnJason
 * 备注：
 */
public abstract class BaseMvcActivity extends AppCompatActivity {

    public ImmersionBar mImmersionBar;
    public Activity mActivity;
    public ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //强制竖屏
        setContentView(getLayoutId());

        LogUtils.e(getLocalClassName());

        mActivity = this;
        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        // 沉浸式
        mImmersionBar.statusBarDarkFont(false).statusBarColor(R.color.colorPrimaryDark).fitsSystemWindows(true).init();

        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setCanceledOnTouchOutside(false);

        initData();
        initView();
        initListener();
        loadData();
    }

    @Override
    public void onDestroy() {
        mProgressDialog.cancel();
        mProgressDialog = null;
        super.onDestroy();
    }

    public void showProgress(String content) {
        if (mProgressDialog != null) {
            mProgressDialog.setMessage(content);
            mProgressDialog.show();
        }
    }

    public void showProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.setMessage("加载中");
            mProgressDialog.show();
        }
    }

    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 布局id
     */
    public abstract int getLayoutId();


    /**
     * 初始化数据
     */
    public void initData() {
    }

    /**
     * 初始化视图
     */
    public void initView() {
    }

    /**
     * 初始化监听事件
     */
    public void initListener() {
    }

    /**
     * 剩余的逻辑代码，加载数据都放在这里处理
     */
    public void loadData() {
    }

    /**
     * 防止Fragment崩溃后重置Activity导致的Fragment重叠问题
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
        if (outState != null) {
            //存在Bundle数据,去除fragments的状态保存，解决fragment错乱问题。
            String FRAGMENTS_TAG = "android:support:fragments";
            outState.remove(FRAGMENTS_TAG);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null) {
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}

