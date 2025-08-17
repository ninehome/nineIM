package com.tiocloud.chat.feature.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.tiocloud.chat.feature.settings.mvp.SettingsContract;
import com.tiocloud.chat.feature.settings.mvp.SettingsPresenter;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.dialog.progress.SingletonProgressDialog;

/**
 * author : TaoWang
 * date : 2020-02-19
 * desc :
 */
public class SettingsActivity extends TioActivity implements SettingsContract.View {

    public static void start(Activity context) {
        Intent starter = new Intent(context, SettingsActivity.class);
        context.startActivityForResult(starter, 1);
    }

    private SettingsPresenter presenter = new SettingsPresenter(this);
    private ViewHolder viewHolder;

    public static class ViewHolder {
        public TextView tv_logoutBtn;
        public TextView tv_version;
        public TextView tv_login_exit;
        public CheckBox switch_verifyAddFriend;
        public CheckBox switch_searchMeAuth;
        public CheckBox switch_msgRemind;
        public CheckBox switch_msgNotice;
        private WtTitleBar titleBar;
        public RelativeLayout rl_account_safe;
        public RelativeLayout rl_privacy;
        public RelativeLayout rl_about;
        public RelativeLayout rl_disturb;
        public RelativeLayout rl_language_switch;
        public RelativeLayout rl_clearHistoryMsg;
        public RelativeLayout rl_version;
        public RelativeLayout rl_notice;
        public RelativeLayout rl_group_send;

        public ViewHolder(View decorView) {
            titleBar = decorView.findViewById(R.id.titleBar);
            tv_logoutBtn = decorView.findViewById(R.id.tv_logoutBtn);
            tv_login_exit = decorView.findViewById(R.id.tv_login_exit);
            tv_version = decorView.findViewById(R.id.tv_version);
            switch_verifyAddFriend = decorView.findViewById(R.id.switch_verifyAddFriend);
            switch_searchMeAuth = decorView.findViewById(R.id.switch_searchMeAuth);
            switch_msgRemind = decorView.findViewById(R.id.switch_msgRemind);
            switch_msgNotice = decorView.findViewById(R.id.switch_msgNotice);
            rl_account_safe = decorView.findViewById(R.id.rl_account_safe);
            rl_privacy = decorView.findViewById(R.id.rl_privacy);
            rl_about = decorView.findViewById(R.id.rl_about);
            rl_disturb = decorView.findViewById(R.id.rl_disturb);
            rl_language_switch = decorView.findViewById(R.id.rl_language_switch);
            rl_clearHistoryMsg = decorView.findViewById(R.id.rl_clearHistoryMsg);
            rl_version = decorView.findViewById(R.id.rl_version);
            rl_notice = decorView.findViewById(R.id.rl_notice);
            rl_group_send = decorView.findViewById(R.id.rl_group_send);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_settings_activity);
        viewHolder = new ViewHolder(getWindow().getDecorView());
        presenter.init();
        viewHolder.titleBar.setTitle(getString(R.string.settings));

        findViewById(R.id.rl_clear_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingletonProgressDialog.show_unCancel(SettingsActivity.this,
                        getString(R.string.clearing));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SingletonProgressDialog.dismiss();
                        ToastUtils.showShort(getString(R.string.clear_success));
                    }
                }, 500);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2){
            setResult(2);
            finish();
        }
    }

    @Override
    public ViewHolder getViewHolder() {
        return viewHolder;
    }
}
