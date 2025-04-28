package com.tiocloud.account.feature.register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;

import com.jaeger.library.StatusBarUtil;
import com.tiocloud.account.R;
import com.tiocloud.account.TioAccount;
import com.tiocloud.account.databinding.AccountPhoneRegisterFragmentBinding;
import com.tiocloud.account.feature.login.LoginActivity;
import com.tiocloud.account.feature.login_sms.SmsLoginActivity;
import com.tiocloud.account.mvp.register.RegisterContract;
import com.tiocloud.account.mvp.register.RegisterPresenter;
import com.tiocloud.account.widget.ThirdPartyLoginView;
import com.watayouxiang.androidutils.page.easy.EasyFragment;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.yanxun.ConstantUtils;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/24
 *     desc   :
 * </pre>
 */
public class PhoneRegisterFragment extends EasyFragment<AccountPhoneRegisterFragmentBinding> implements RegisterContract.View {

    public final ObservableField<String> txt_phone = new ObservableField<>("");
    public final ObservableField<String> txt_code = new ObservableField<>("");
    public final ObservableField<String> txt_nick = new ObservableField<>("");
    public final ObservableField<String> txt_pwd = new ObservableField<>("");
    public final ObservableField<Boolean> isStartTimer = new ObservableField<>(false);

    private RegisterPresenter presenter;

    @Override
    protected int getContentViewId() {
        return R.layout.account_phone_register_fragment;
    }

//    @Override
//    protected View getStatusBarHolder() {
//        return binding.statusBar;
//    }

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
//        StatusBarUtil.setTransparent(getActivity());
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
        // 默认不绑定邮箱
        binding.tvBindEmail.setSelected(false);
        // 验证码登录
        if (ConstantUtils.checkCode){
            binding.llCode.setVisibility(View.VISIBLE);
//            binding.tvCodeLogin.setVisibility(View.VISIBLE);
            binding.etPhone.setInputType(InputType.TYPE_CLASS_NUMBER);
            /*binding.llEmail.setVisibility(View.GONE);*/
            binding.etCode.setHint(getString(R.string.hint_input_code));
//            binding.llSuperpwd.setVisibility(View.GONE);
        }else if (ConstantUtils.checkEmail){
            binding.llCode.setVisibility(View.VISIBLE);
//            binding.tvCodeLogin.setVisibility(View.GONE);
            binding.etPhone.setInputType(InputType.TYPE_CLASS_TEXT);
            /*binding.llEmail.setVisibility(View.VISIBLE);*/
            binding.etCode.setHint(getString(R.string.hint_input_mail_code));
//            binding.llSuperpwd.setVisibility(View.GONE);
            if (ConstantUtils.checkEmail){
                binding.etPhone.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_email), null, null, null);
            }
        }else {
            binding.llCode.setVisibility(View.GONE);
//            binding.llSuperpwd.setVisibility(View.VISIBLE);
//            binding.tvCodeLogin.setVisibility(View.GONE);
            binding.etPhone.setInputType(InputType.TYPE_CLASS_TEXT );
            /*binding.llEmail.setVisibility(View.GONE);*/
        }

        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        if (TioAccount.inviteEnable){
            binding.llInvite.setVisibility(View.VISIBLE);
        }else {
            binding.llInvite.setVisibility(View.GONE);
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
        presenter.phoneRegister(getContext(),txt_phone.get(), txt_code.get(), txt_nick.get(), txt_pwd.get(), binding.etInvite.getText().toString(), txt_phone.get(), binding.protocolView.isCheckbox.get());
    }

    // 绑定邮箱
    public void onClick_bindEmail(View view) {
        getRegisterActivity().showPhoneRegisterBindEmailFragment();
    }

    // 获取验证码
    public void onClick_reqPhoneCode(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        presenter.reqSendSms(getActivity(), ConstantUtils.checkCode?txt_phone.get():txt_phone.get());
    }

    @Override
    public void onPhoneRegisterSuccess(String uid, String pwd) {
//        TioToast.showShort("注册成功，请登录");
        finish();
//        LoginActivity.start(getActivity());
        RegisterSuccessActivity.start(getActivity(), uid, pwd);
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

    }

    private RegisterActivity getRegisterActivity() {
        FragmentActivity activity = getActivity();
        if (activity instanceof RegisterActivity) {
            return (RegisterActivity) activity;
        }
        return null;
    }
}
