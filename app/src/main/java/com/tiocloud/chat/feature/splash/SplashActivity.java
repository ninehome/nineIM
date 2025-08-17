package com.tiocloud.chat.feature.splash;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.annotation.Nullable;

import com.tiocloud.account.TioAccount;
import com.tiocloud.account.feature.login.LoginActivity;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.main.MainActivity;
import com.tiocloud.chat.mvp.launcher.LauncherContract;
import com.tiocloud.chat.mvp.launcher.LauncherPresenter;
import com.watayouxiang.androidutils.page.TioActivity;
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
        // 问题：用android的installer安装打开闪屏页，按Home键回到首页，然后点击launcher的图标会再打开一个闪屏页。
        // 原因：installer安装方式打开闪屏页，系统会创建了新的Task中用于存放闪屏页实例，从而导致重复启动闪屏页面。
        // 解决办法：避免从桌面启动程序后，会重新实例化入口类的activity。
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
        NotificationChannel notificationChannel= null;
        //进行8.0的判断
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel= new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }


        //渐变背景图片
        AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
        aa.setDuration(TioConfig.OpenCloseConfig.getSplashTime());//设置动画持续时间
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationEnd(Animation arg0) {
                if (isFinishing()){
                    return;
                }
                presenter.toNext();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationStart(Animation animation) {}

        });
        if (!getString(R.string.my_name).equals("鸽哒")){
            findViewById(R.id.ll_logo).setVisibility(View.GONE);
        }
        presenter = new LauncherPresenter(this);
        presenter.init();
        TioAccount.isOA = TioConfig.OpenCloseConfig.isOA();
        TioAccount.showRegiest = !TioConfig.OpenCloseConfig.hideRegiest();
        HttpUrlUtils.newPwdRule = TioConfig.OpenCloseConfig.newPwdRule();
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
//        OneKeyLoginActivity.start(this);
    }

    @Override
    public void openMainPage() {
        MainActivity.start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2){

        }
    }
}
