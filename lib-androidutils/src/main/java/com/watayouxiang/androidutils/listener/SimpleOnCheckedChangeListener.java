package com.watayouxiang.androidutils.listener;

import android.widget.CompoundButton;

/**
 * author : TaoWang
 * date : 2020/4/1
 * desc : Switch 状态改变监听
 */
public abstract class SimpleOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (compoundButton.isPressed()) {
            onUserCheckedChanged(compoundButton, isChecked);
        } else {
            onCodeCheckedChanged(compoundButton, isChecked);
        }
    }

    /**
     * 代码调用
     */
    public void onCodeCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

    }

    /**
     * 用户调用
     */
    public void onUserCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

    }
}
