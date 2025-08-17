package com.tiocloud.account.feature.register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;

import com.tiocloud.account.R;
import com.tiocloud.account.TioAccount;
import com.tiocloud.account.databinding.AccountPhoneRegisterBindEmailFragmentBinding;
import com.tiocloud.account.feature.login.LoginActivity;
import com.tiocloud.account.feature.login_sms.SmsLoginActivity;
import com.tiocloud.account.mvp.register.RegisterContract;
import com.tiocloud.account.mvp.register.RegisterPresenter;
import com.tiocloud.account.widget.ThirdPartyLoginView;
import com.watayouxiang.androidutils.page.easy.EasyFragment;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.androidutils.widget.TioToast;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/24
 *     desc   :
 * </pre>
 */
public class PhoneRegisterBindEmailFragment extends EasyFragment<AccountPhoneRegisterBindEmailFragmentBinding> implements RegisterContract.View {

    public final ObservableField<String> txt_phone = new ObservableField<>("");
    public final ObservableField<String> txt_code = new ObservableField<>("");
    public final ObservableField<String> txt_email = new ObservableField<>("");
    public final ObservableField<String> txt_pwd = new ObservableField<>("");
    public final ObservableField<Boolean> isStartTimer = new ObservableField<>(false);

    private RegisterPresenter presenter;

    @Override
    protected int getContentViewId() {
        return R.layout.account_phone_register_bind_email_fragment;
    }

    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

//    @Override
//    protected Integer getStatusBarColor() {
////        return Color.parseColor("#DBEAFF");
//        return Color.WHITE;
//    }

    @Override
    protected Boolean getStatusBarLightMode() {
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setData(this);
        presenter = new RegisterPresenter(this);
        resetUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ThirdPartyLoginView.onActivityResult(requestCode, resultCode, data);
    }

    private void resetUI() {
        // 默认绑定邮箱
        binding.tvBindEmail.setSelected(true);
        // 验证码登录
        if (!TioAccount.OPEN_SMS_LOGIN) {
            binding.tvCodeLogin.setVisibility(View.INVISIBLE);
        }
    }

    // 密码登录
    public void onClick_topRight(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        LoginActivity.start(getActivity());
        finish();
    }

    // 验证码登录
    public void onClick_codeLogin(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        SmsLoginActivity.start(getActivity());
        finish();
    }

    // 注册
    public void onClick_ok(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        presenter.phoneRegisterBindEmail(getContext(),txt_code.get(), txt_phone.get(), txt_email.get(), txt_pwd.get(), binding.protocolView.isCheckbox.get());
    }

    // 绑定邮箱
    public void onClick_bindEmail(View view) {
        getRegisterActivity().showPhoneRegisterFragment();
    }

    // 获取验证码
    public void onClick_reqPhoneCode(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        presenter.reqSendSms(getActivity(), txt_phone.get());
    }

    @Override
    public void onPhoneRegisterSuccess(String uid, String pwd) {

    }

    @Override
    public void onSendSmsSuccess() {
        presenter.startCodeTimer(60);
    }

    @Override
    public void onCodeTimerRunning(int second) {
        isStartTimer.set(true);
        binding.tvReqPhoneCode.setText(String.format(Locale.getDefault(), "已发送(%ds)", second));
    }

    @Override
    public void onCodeTimerStop() {
        isStartTimer.set(false);
        binding.tvReqPhoneCode.setText(getString(R.string.get_code));
    }

    @Override
    public void onRegisterBindEmailSuccess() {
        TioToast.showShort(getString(R.string.bind_mail_success));
        LoginActivity.start(getActivity());
        finish();
    }

    private RegisterActivity getRegisterActivity() {
        FragmentActivity activity = getActivity();
        if (activity instanceof RegisterActivity) {
            return (RegisterActivity) activity;
        }
        return null;
    }
}
