package com.tiocloud.account.feature.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jaeger.library.StatusBarUtil;
import com.tiocloud.account.R;
import com.tiocloud.account.databinding.AccountRegisterActivityBinding;
import com.watayouxiang.androidutils.page.BaseFragment;
import com.watayouxiang.androidutils.page.easy.EasyActivity;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/22
 *     desc   : 手机号注册
 * </pre>
 */
public class RegisterActivity extends EasyActivity<AccountRegisterActivityBinding> {

    private PhoneRegisterFragment phoneFragment;
    private PhoneRegisterBindEmailFragment emailFragment;
    private ShowPage showPage;

    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
        if (!(context instanceof Activity)) {
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(starter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.account_register_activity;
    }

    @Override
    protected Integer getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        binding.setData(this);
        resetUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (showPage == null) return;
        switch (showPage) {
            case REGISTER:
                phoneFragment.onActivityResult(requestCode, resultCode, data);
                break;
            case BIND_EMAIL:
                emailFragment.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void resetUI() {
        phoneFragment = new PhoneRegisterFragment();
        emailFragment = new PhoneRegisterBindEmailFragment();

        addFragment(phoneFragment);
        addFragment(emailFragment);

        showPhoneRegisterFragment();
    }

    @Override
    public <T extends BaseFragment> T addFragment(T fragment) {
        fragment.setContainerId(binding.flContainer.getId());
        return super.addFragment(fragment);
    }

    public void showPhoneRegisterBindEmailFragment() {
        hideFragment(phoneFragment);
        showFragment(emailFragment);
        showPage = ShowPage.BIND_EMAIL;
    }

    public void showPhoneRegisterFragment() {
        hideFragment(emailFragment);
        showFragment(phoneFragment);
        showPage = ShowPage.REGISTER;
    }

    private enum ShowPage {
        REGISTER, BIND_EMAIL
    }
}
