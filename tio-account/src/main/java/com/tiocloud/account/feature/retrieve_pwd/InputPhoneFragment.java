package com.tiocloud.account.feature.retrieve_pwd;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.account.R;
import com.tiocloud.account.databinding.AccountRetrievePwdInputPhoneFragmentBinding;
import com.tiocloud.account.feature.login.LoginActivity;
import com.tiocloud.account.mvp.retrieve_pwd.RetrievePwdContract;
import com.tiocloud.account.mvp.retrieve_pwd.RetrievePwdPresenter;
import com.watayouxiang.androidutils.page.easy.EasyFragment;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.yanxun.ConstantUtils;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/24
 *     desc   : 1、输入手机
 * </pre>
 */
public class InputPhoneFragment extends EasyFragment<AccountRetrievePwdInputPhoneFragmentBinding>
        implements RetrievePwdContract.View, RetrievePwdContract.OnSmsBeforeCheckListener, RetrievePwdContract.OnSendSmsListener, RetrievePwdContract.OnCodeTimerListener, RetrievePwdContract.OnResetPwdListener {

    public final ObservableField<String> txt_phone = new ObservableField<>("");

    private RetrievePwdPresenter presenter;

    @Override
    protected int getContentViewId() {
        return R.layout.account_retrieve_pwd_input_phone_fragment;
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
        binding.setData(this);
        presenter = new RetrievePwdPresenter(this);
        resetUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    private void resetUI() {
        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        binding.tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.reqSmsBeforeCheck(getActivity(), txt_phone.get(), InputPhoneFragment.this);
            }
        });

        if (ConstantUtils.checkCode){
            binding.etSms.setHint(getString(R.string.hint_input_code));
            binding.etPhone.setHint(getString(R.string.hint_input_phone));
            binding.etPhone.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_mobilephone), null, null, null);
        }else if (ConstantUtils.checkEmail){
            binding.etSms.setHint(getString(R.string.mail_code));
            binding.etPhone.setHint(getString(R.string.hint_input_mail));
            binding.etPhone.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_email), null, null, null);
        }
    }

    // 确定按钮
    public void onClick_ok(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
//        presenter.reqSmsBeforeCheck(txt_phone.get(), this);
        String password = binding.etPwd1.getSubmitText();
        if (password == null){
            ToastUtils.showShort(getString(R.string.hint_input_new_pwd));
            return;
        }
        if (password.length() < 6){
            ToastUtils.showShort(getString(R.string.hint_nput_six_new_pwd));
            return;
        }
        if (!password.equals(binding.etPwd2.getSubmitText())){
            ToastUtils.showShort(getString(R.string.double_input_pwd_unsame));
            return;
        }
        presenter.reqResetPwd(binding.etSms.getSubmitText(), txt_phone.get(), password, this);
    }

    private RetrievePwdActivity getRetrievePwdActivity() {
        FragmentActivity activity = getActivity();
        if (activity instanceof RetrievePwdActivity) {
            return (RetrievePwdActivity) activity;
        }
        return null;
    }

    @Override
    public void onSmsBeforeCheckSuccess() {
//        getRetrievePwdActivity().showSmsCodeFragment(txt_phone.get());
        presenter.reqSendSms(getActivity(), txt_phone.get(), this);
    }

    @Override
    public void onSendSmsSuccess() {
        presenter.startCodeTimer(60, this);
    }

    @Override
    public void onCodeTimerRunning(int second) {
        binding.tvGetCode.setVisibility(View.GONE);
        binding.tvSeconds.setVisibility(View.VISIBLE);
        binding.tvSeconds.setText(getString(R.string.sended)+"("+second+")");
    }

    @Override
    public void onCodeTimerStop() {
        binding.tvSeconds.setVisibility(View.GONE);
        binding.tvGetCode.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResetPwdSuccess() {
        TioToast.showShort(getString(R.string.pwd_set_success_login));
        LoginActivity.start(getActivity());
        finish();
    }
}
