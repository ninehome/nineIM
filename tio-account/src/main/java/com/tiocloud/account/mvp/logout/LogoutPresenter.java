package com.tiocloud.account.mvp.logout;

import android.app.Activity;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.lzy.okgo.request.base.Request;
import com.tiocloud.account.R;
import com.tiocloud.account.feature.login.LoginActivity;
import com.tiocloud.account.widget.LogoutDialog;
import com.tiocloud.jpush.PushLauncher;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.progress.SingletonProgressDialog;
import com.watayouxiang.db.dao.ChatListTableCrud;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.prefernces.CookieUtils;
import com.watayouxiang.imclient.TioIMClient;

/**
 * author : TaoWang
 * date : 2020-02-12
 * desc :
 */
public class LogoutPresenter extends LogoutContract.Presenter {

    public LogoutPresenter(LogoutContract.View view) {
        super(view);
    }

    @Override
    public void logout(Activity activity) {
        getModel().requestLogout(new TioCallback<Void>() {
            @Override
            public void onStart(Request<BaseResp<Void>, ? extends Request> request) {
                super.onStart(request);
                // 显示强加载对话框
                SingletonProgressDialog.show_unCancel(activity, activity.getString(R.string.logout));
            }

            @Override
            public void onTioSuccess(Void aVoid) {
                // 踢出登录
                LogoutPresenter.kickOut();
            }

            @Override
            public void onTioError(String msg) {
                // 提示
                TioToast.showShort(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                // 隐藏强对话框
                SingletonProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void showLogoutDialog(Activity activity) {
        new LogoutDialog(new LogoutDialog.OnBtnListener() {
            @Override
            public void onClickPositive(View view, LogoutDialog dialog) {
                dialog.dismiss();
                logout(activity);
            }

            @Override
            public void onClickNegative(View view, LogoutDialog dialog) {
                dialog.dismiss();
            }
        }).show_unCancel(activity);
    }

    // ====================================================================================
    // 踢出登录
    // ====================================================================================

    public static void kickOut() {
        // 清除登录信息
        clearLoginInfo();
        // 只打开登录页
        openLoginClearOthers();
    }

    public static void clearLoginInfo() {
        ThreadUtils.runOnUiThread(() -> {
            // 设置未读消息数为 0
            PushLauncher.getInstance().setBadgeNumber(0);
            // 删除数据库中的 "会话列表"
            ChatListTableCrud.deleteAll();
            // 断开长连接
            TioIMClient.getInstance().setConfig(null);
            TioIMClient.getInstance().disconnect();
            // 清除用户id
            TioDBPreferences.saveCurrUid(0);
            TioDBPreferences.savePhone("");
            TioDBPreferences.saveEmail("");
            String json = new Gson().toJson("{}");
            TioDBPreferences.saveExData(json);
            // 清除当前用户信息
            CurrUserTableCrud.curr_delete();
            // 移除 cookies
            CookieUtils.removeCookies();
        });
    }

    public static void openLoginClearOthers() {
        ThreadUtils.runOnUiThread(() -> {
            ActivityUtils.finishAllActivities();
            // 打开登录页面
            LoginActivity.start(Utils.getApp());
            // 关闭其他页面

        });
    }
}
