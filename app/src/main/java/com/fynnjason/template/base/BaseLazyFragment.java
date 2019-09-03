package com.wy.kuaikantoutiao.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 懒加载
 * 有两种情况：
 * 1.当前fragment就是要显示的fragment，因为setUserVisibleHint生命周期先于onCreateView，
 * 所以load方法不在setUserVisibleHint中执行，此时需要在onViewCreated中执行load方法
 * <p>
 * 2.fragment是左右两边的，此时左右两边fragment的setUserVisibleHint不会执行load方法，
 * onViewCreated因为视图不可见原因也不会执行load方法，所以只有切换到左右两边fragment时，
 * 该fragment为可见状态，才会重新进入setUserVisibleHint去执行一次load
 *
 * Created by FynnJason on 2018/04/02
 */


public abstract class BaseLazyFragment extends Fragment {

    public View rootView;
    public Unbinder unbinder;


    private boolean mIsLoad; // 是否已经加载过数据
    private boolean mIsViewVisible; // 视图是否可见状态
    public Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //避免视图重复创建
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        // butterknife出现的问题，在viewPager中快速左右滑动fragment，可能会造成unbinder为null
        if (unbinder == null) {
            unbinder = ButterKnife.bind(getFragment(), rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mIsViewVisible) {
            load();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    // 这里的方法只会当视图第一次可见时加载一次
    private void load() {
        // 有两种办法解决getActivity为null所带来的bug，一种是在onAttach中绑定一个activity引用，在onDetach释放，这样activity就一直有了
        // 另外一种是根据getActivity是否为null来加载数据，因为用户快速滑动时，其实去加载数据并显示在视图上是没有必要的，我是这么觉得的，第二种配合setUserVisibleHint生命周期来处理
        if (getActivity() == null) {
            return;
        }
        if (mIsLoad) {
            return;
        }
        mIsLoad = true;
        initData();
        initView();
        initListener();
        loadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsViewVisible = isVisibleToUser;
        // 只有当视图可见时，才去执行方法
        if (mIsViewVisible && rootView != null) {
            load();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && mActivity == null) {
            mActivity = getActivity();
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

    public void showToast(String msg) {
        if (null != getContext())
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
