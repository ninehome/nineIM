package com.tiocloud.account.feature.bind_phone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tiocloud.account.R;
import com.tiocloud.account.databinding.AccountBindPhoneActivityBinding;
import com.watayouxiang.androidutils.page.BaseFragment;
import com.watayouxiang.androidutils.page.easy.EasyActivity;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/22
 *     desc   : 绑定手机号
 * </pre>
 */
public class BindPhoneActivity extends EasyActivity<AccountBindPhoneActivityBinding> {

    private BindPhoneFragment phoneFragment;

    public static void start(Context context) {
        Intent starter = new Intent(context, BindPhoneActivity.class);
        if (!(context instanceof Activity)) {
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(starter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.account_bind_phone_activity;
    }

    @Override
    protected Integer getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setData(this);
        resetUI();
    }

    @Override
    public void onBackPressed() {
        boolean backPressed = true;
        if (phoneFragment != null) {
            backPressed = phoneFragment.onBackPressed();
        }
        if (backPressed) {
            super.onBackPressed();
        }
    }

    private void resetUI() {
        phoneFragment = new BindPhoneFragment();
        addFragment(phoneFragment);
        showBindPhoneFragment();
    }

    @Override
    public <T extends BaseFragment> T addFragment(T fragment) {
        fragment.setContainerId(binding.flContainer.getId());
        return super.addFragment(fragment);
    }

    public void showBindPhoneFragment() {
        showFragment(phoneFragment);
    }
}
