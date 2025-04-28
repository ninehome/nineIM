package com.tiocloud.account.feature.login_sms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.FragmentUtils;
import com.jaeger.library.StatusBarUtil;
import com.tiocloud.account.R;
import com.tiocloud.account.databinding.AccountSmsLoginActivityBinding;
import com.tiocloud.account.widget.ThirdPartyLoginView;
import com.watayouxiang.androidutils.page.easy.EasyActivity;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/30
 *     desc   : 短信登录
 * </pre>
 */
public class SmsLoginActivity extends EasyActivity<AccountSmsLoginActivityBinding> {

    public static void start(Context context) {
        Intent starter = new Intent(context, SmsLoginActivity.class);
        if (!(context instanceof Activity)) {
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(starter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.account_sms_login_activity;
    }

//    @Override
//    protected Integer getBackgroundColor() {
//        return Color.WHITE;
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        binding.setData(this);
        resetUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ThirdPartyLoginView.onActivityResult(requestCode, resultCode, data);
    }

    private void resetUI() {
        FragmentUtils.add(getSupportFragmentManager(), InputPhoneFragment.getInstance(), binding.flContainer.getId());
    }

    @Override
    public void onBackPressed() {
        if (!FragmentUtils.dispatchBackPress(getSupportFragmentManager())) {
            super.onBackPressed();
        }
    }
}
