package com.tiocloud.chat.feature.splash;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.account.TioAccount;
import com.tiocloud.account.feature.login.LoginActivity;
import com.tiocloud.chat.GatewayActivity;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.main.MainActivity;
import com.tiocloud.chat.mvp.launcher.LauncherContract;
import com.tiocloud.chat.mvp.launcher.LauncherPresenter;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.dialog.progress.SingletonProgressDialog;
import com.watayouxiang.httpclient.callback.TioCallbackImpl;
import com.watayouxiang.httpclient.model.request.GatewayReq;
import com.watayouxiang.httpclient.model.response.GatewayResp;
import com.watayouxiang.httpclient.prefernces.HttpPreferences;
import com.watayouxiang.httpclient.utils.HttpUrlUtils;

/**
 * author : TaoWang
 * date : 2019-12-31
 * desc : 欢迎页面
 */
public class SplashActivity extends TioActivity implements LauncherContract.View {

    @Nullable
    private LauncherPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) > 0) {
            finish();
            return;
        }
        hideStatusBar();
        final View view = View.inflate(this, R.layout.tio_activity_welcome, null);
        setContentView(view);

        //创建一个通知
        String CHANNEL_ONE_ID = getString(R.string.new_message_inform);
        String CHANNEL_ONE_NAME= getString(R.string.new_message_inform);
        NotificationChannel notificationChannel;
        //进行8.0的判断
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel= new NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }

        presenter = new LauncherPresenter(this);
        gatewayReq(() -> {
            presenter.init();
            TioAccount.isOA = TioConfig.OpenCloseConfig.isOA();
            TioAccount.showRegiest = !TioConfig.OpenCloseConfig.hideRegiest();
            HttpUrlUtils.newPwdRule = TioConfig.OpenCloseConfig.newPwdRule();
        });
    }

    private void gatewayReq(Runnable runnable) {
        String localEnterpriseId = HttpPreferences.getGatewayId();

        SingletonProgressDialog.show_unCancel(this, getString(R.string.loading));
        new GatewayReq(localEnterpriseId).get(new TioCallbackImpl<GatewayResp>(){
            @Override
            public void onTioSuccess(GatewayResp data) {
                HttpPreferences.saveGatewayId(localEnterpriseId);
                if(data.expire == 1) {
                    HttpPreferences.saveBaseUrl(data.host);
                    HttpPreferences.saveResUrl(data.res);
                    runnable.run();
                } else {
                    gatewayRequestError("企业已过期");
                }
            }

            @Override
            public void onTioError(String msg) {
                gatewayRequestError(msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    @Override
    public void openLoginPage() {
        LoginActivity.start(this);
    }

    @Override
    public void openMainPage() {
        MainActivity.start(this);
    }

    private void gatewayRequestError(String msg) {
        SingletonProgressDialog.dismiss();
        ToastUtils.showLong(msg);
        HttpPreferences.saveGatewayId("");

        Intent intent = new Intent(SplashActivity.this, GatewayActivity.class);
        startActivity(intent);
        finish();
    }
}