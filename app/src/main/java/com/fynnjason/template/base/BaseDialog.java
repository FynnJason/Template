package com.fynnjason.template.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;

import com.fynnjason.template.R;


/**
 * 作者：FynnJason
 * 修改时间：2019/8/6
 */

public abstract class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.Theme_AppCompat_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        // 设置底部显示并宽度全屏
        Window window = this.getWindow();
        if (window != null) {
            if (gravity() != 0)
                window.setGravity(gravity());
            // 把 DecorView 的默认 padding 取消，同时 DecorView 的默认大小也会取消
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            // 设置宽度
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
            // 给 DecorView 设置背景颜色，很重要，不然导致 Dialog 内容显示不全，有一部分内容会充当 padding，上面例子有举出
            window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
        }

        initView();
        remain();
    }

    public abstract int getLayoutId(); // 布局id

    public abstract void initView(); // 初始化布局

    public abstract void remain(); // 剩余逻辑

    public abstract int gravity(); // 设置dialog弹出位置

}
