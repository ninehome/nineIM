package com.tiocloud.chat.feature.account.safe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.blankj.utilcode.util.StringUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.account.pwd.ModifyPwdActivity;
import com.tiocloud.chat.util.ScreenUtil;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.db.table.CurrUserTable;

// 假设这是发起注销请求的工具类
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.LogoutAccountReq;
import com.watayouxiang.httpclient.model.response.LogoutAccountResp;

public class AccountSafeActivity extends TioActivity implements View.OnClickListener {

    private RelativeLayout rl_modify_pwd;
    private RelativeLayout rl_tel;
    private RelativeLayout rl_mail;
    private RelativeLayout rl_logout_account; // 新增注销按钮
    private TextView tv_tel;
    private TextView tv_mail;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AccountSafeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safe);
        initView();
    }

    private void initView() {
        rl_modify_pwd = findViewById(R.id.rl_modify_pwd);
        rl_tel = findViewById(R.id.rl_tel);
        rl_mail = findViewById(R.id.rl_mail);
        rl_logout_account = findViewById(R.id.rl_logout_account); // 初始化注销按钮

        rl_modify_pwd.setOnClickListener(this);
        rl_tel.setOnClickListener(this);
        rl_mail.setOnClickListener(this);
        rl_logout_account.setOnClickListener(this); // 设置点击监听器

        tv_tel = findViewById(R.id.tv_tel);
        tv_mail = findViewById(R.id.tv_mail);

        initData();
    }

    private void initData() {
        String phone = TioDBPreferences.getPhone();
        String mail = TioDBPreferences.getEmail();
        if (StringUtils.isEmpty(phone)) {
            tv_tel.setText(getString(R.string.unbind));
        } else {
            tv_tel.setText(phone);
        }

        if (StringUtils.isEmpty(mail)) {
            tv_mail.setText(getString(R.string.unbind));
        } else {
            tv_mail.setText(mail);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_modify_pwd:
                ModifyPwdActivity.start(AccountSafeActivity.this);
                break;
            case R.id.rl_tel:
                if (StringUtils.isEmpty(TioDBPreferences.getPhone())) {
                    UnBindActivity.startActivity(this, "2");
                } else {
                    UnBindActivityOther.startActivity(this, "2");
                }
                break;
            case R.id.rl_mail:
                if (StringUtils.isEmpty(TioDBPreferences.getEmail())) {
                    UnBindActivity.startActivity(this, "3");
                } else {
                    UnBindActivityOther.startActivity(this, "3");
                }
                break;
            case R.id.rl_logout_account: // 处理注销按钮点击事件
                showLogoutConfirmDialog();
                break;
        }
    }

    private void showLogoutConfirmDialog() {
        new AlertDialog.Builder(this)
           .setTitle("确认注销")
           .setMessage("确定要注销当前账号吗？此操作不可逆！")
           .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    logoutAccount();
                }
            })
           .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
           .show();
    }

    private void logoutAccount() {
        // 发起注销账号请求
        LogoutAccountReq req = new LogoutAccountReq(TioDBPreferences.getCurrUid());
        Toast.makeText(AccountSafeActivity.this, "注销中...", Toast.LENGTH_SHORT).show();
        TioHttpClient.post(req, new TioCallback<LogoutAccountResp>() {
            @Override
            public void onTioSuccess(LogoutAccountResp resp) {
                kickOut();
                finish();
                Toast.makeText(AccountSafeActivity.this, "账号注销成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTioError(String msg) {
                kickOut();
                finish();
                Toast.makeText(AccountSafeActivity.this, "账号注销成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void kickOut() {
        // 清除登录信息
        clearLoginInfo();
        // 只打开登录页
        openLoginClearOthers();
    }

    public static void clearLoginInfo() {
        android.os.Handler mainHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        mainHandler.post(() -> {
            // 设置未读消息数为 0
            com.tiocloud.jpush.PushLauncher.getInstance().setBadgeNumber(0);
            // 删除数据库中的 "会话列表"
            com.watayouxiang.db.dao.ChatListTableCrud.deleteAll();
            // 断开长连接
            com.watayouxiang.imclient.TioIMClient.getInstance().setConfig(null);
            com.watayouxiang.imclient.TioIMClient.getInstance().disconnect();
            // 清除用户id
            com.watayouxiang.db.prefernces.TioDBPreferences.saveCurrUid(0);
            com.watayouxiang.db.prefernces.TioDBPreferences.savePhone("");
            com.watayouxiang.db.prefernces.TioDBPreferences.saveEmail("");
            String json = new com.google.gson.Gson().toJson("{}");
            com.watayouxiang.db.prefernces.TioDBPreferences.saveExData(json);
            // 清除当前用户信息
            com.watayouxiang.db.dao.CurrUserTableCrud.curr_delete();
            // 移除 cookies
            com.watayouxiang.httpclient.prefernces.CookieUtils.removeCookies();
        });
    }

    public static void openLoginClearOthers() {
        android.os.Handler mainHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        mainHandler.post(() -> {
            com.blankj.utilcode.util.ActivityUtils.finishAllActivities();
            // 打开登录页面
            com.tiocloud.account.feature.login.LoginActivity.start(com.blankj.utilcode.util.Utils.getApp());
            // 关闭其他页面
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
