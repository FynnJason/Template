package com.fynnjason.template.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：FynnJason
 * 备注：
 */
public abstract class BaseMvcFragment extends Fragment {

    public View rootView;
    public Unbinder unbinder;
    private boolean repeatView = true;
    public ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //避免视图重复加载
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        unbinder = ButterKnife.bind(getFragment(), rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //禁止重新绘制界面
        if (repeatView) {
            repeatView = false;
            mProgressDialog = new ProgressDialog(getContext());
            initData();
            initView();
            initListener();
            loadData();
        }
    }

    @Override
    public void onDestroy() {
        mProgressDialog.cancel();
        mProgressDialog = null;
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
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

    /**
     * 视图id
     *
     * @return 视图id
     */
    public abstract int getLayoutId();

    /**
     * 当前Fragment实例
     *
     * @return Fragment
     */
    public Fragment getFragment() {
        return this;
    }

    /**
     * 初始化数据，接收传递数据
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
     * 剩余逻辑代码
     */
    public void loadData() {
    }
}
