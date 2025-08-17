package com.tiocloud.chat.feature.session.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.BarUtils;
import com.jaeger.library.StatusBarUtil;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseAction;
import com.tiocloud.chat.feature.session.common.action.util.ActionUtil;
import com.tiocloud.chat.feature.session.common.model.SessionType;
import com.tiocloud.chat.widget.titlebar.SessionTitleBar;
import com.watayouxiang.androidutils.page.TioActivity;

import java.util.ArrayList;

/**
 * author : TaoWang
 * date : 2019-12-27
 * desc : 会话页
 * 1、定制化配置
 * 2、关闭后回退到哪个页面
 */
public abstract class SessionActivity extends TioActivity {

    @Nullable
    private SessionFragment fragment;
    private SessionTitleBar titleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_message_activity);
        titleBar = findViewById(R.id.titleBar);
//        BarUtils.setStatusBarCustom(findViewById(R.id.h_view));
//        StatusBarUtil.setTranslucent(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActionUtil.onActivityResult(requestCode, resultCode, data, getActions());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ActionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults, getActions());
    }

    @Override
    public void onBackPressed() {
        // 优先处理messageFragment返回事件
        if (fragment != null && fragment.onBackPressed()) {
            return;
        }

        super.onBackPressed();

        // 返回到哪个Activity
        Class<? extends Activity> backToClass = getBackToClass();
        if (backToClass != null) {
            Intent intent = new Intent();
            intent.setClass(this, backToClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }

    public <T extends SessionFragment> void replaceFragment(T fragment) {
        this.fragment = fragment;
        fragment.setContainerId(R.id.fragment_container);
        super.replaceFragment(fragment);
    }

    // ====================================================================================
    // getter
    // ====================================================================================

    @Nullable
    protected abstract Class<? extends Activity> getBackToClass();

    @Nullable
    public abstract ArrayList<BaseAction> getActions();

    @NonNull
    public abstract SessionType getSessionType();

    public SessionTitleBar getTitleBar() {
        return titleBar;
    }

    public void sendComplete(){

    }
}
