package com.tiocloud.social;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.watayouxiang.social.callback.SocialLoginCallback;
import com.watayouxiang.social.callback.SocialShareCallback;
import com.watayouxiang.social.entities.QQShareEntity;
import com.watayouxiang.social.entities.ShareEntity;
import com.watayouxiang.social.entities.ThirdInfoEntity;
import com.watayouxiang.social.entities.WXShareEntity;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/15
 *     desc   :
 * </pre>
 */
public class TioSocialDemoActivity extends AppCompatActivity implements SocialLoginCallback, SocialShareCallback {

    private TextView tv_log;

    private void log(String log) {
        tv_log.append(String.valueOf(log));
        tv_log.append("\n");
        tv_log.append("\n");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_demo_activity);
        tv_log = findViewById(R.id.tv_log);
        tv_log.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 用处：qq登录和分享回调，以及微博登录回调
        // qq分享如果选择留在qq，通过home键退出，再进入app则不会有回调
        TioSocial.INSTANCE.socialHelper.onActivityResult(requestCode, resultCode, data);
    }

    public void onClick_QQLogin(View view) {
        TioSocial.INSTANCE.socialHelper.loginQQ(this, this);
    }

    public void onClick_WXLogin(View view) {
        TioSocial.INSTANCE.socialHelper.loginWX(this, this);
    }

    public void onClick_WBLogin(View view) {

    }

    public void onClick_QQShare(View view) {
        ShareEntity shareEntity = QQShareEntity.createImageTextInfo(
                "标题",
                "https://www.tiocloud.com/2/index.html",
                "https://www.tiocloud.com/2/imgs/header/logo.png",
                "总结",
                getString(R.string.app_name)
        );
        TioSocial.INSTANCE.socialHelper.shareQQ(this, shareEntity, this);
    }

    public void onClick_WXShare(View view) {
        ShareEntity shareEntity = WXShareEntity.createWebPageInfo(
                false,
                "https://www.tiocloud.com/2/index.html",
                R.drawable.weibosdk_empty_failed,
                "标题",
                "总结"
        );
        TioSocial.INSTANCE.socialHelper.shareWX(this, shareEntity, this);
    }

    public void onClick_WBShare(View view) {

    }

    // ====================================================================================
    // Social Callback
    // ====================================================================================

    @Override
    public void loginSuccess(ThirdInfoEntity info) {
        log("loginSuccess: " + info);
    }

    @Override
    public void socialError(String msg) {
        log("socialError: " + msg);
    }

    @Override
    public void shareSuccess(int type) {
        log("shareSuccess: " + type);
    }
}
