package com.tiocloud.account.feature.modify_pwd;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.request.base.Request;
import com.tiocloud.account.R;
import com.tiocloud.account.databinding.AccountModifyPwdInputPhoneFragmentBinding;
import com.tiocloud.account.feature.login.LoginActivity;
import com.tiocloud.account.mvp.logout.LogoutContract;
import com.tiocloud.account.mvp.logout.LogoutPresenter;
import com.tiocloud.account.mvp.retrieve_pwd.RetrievePwdContract;
import com.tiocloud.account.mvp.retrieve_pwd.RetrievePwdPresenter;
import com.watayouxiang.androidutils.page.easy.EasyFragment;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.progress.SingletonProgressDialog;
import com.watayouxiang.androidutils.yanxun.ConstantUtils;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.BaseResp;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/24
 *     desc   : 1、输入手机
 * </pre>
 */
public class InputPhoneFragment extends EasyFragment<AccountModifyPwdInputPhoneFragmentBinding>
        implements LogoutContract.View, RetrievePwdContract.View, RetrievePwdContract.OnSmsBeforeCheckListener, RetrievePwdContract.OnSendSmsListener, RetrievePwdContract.OnCodeTimerListener, RetrievePwdContract.OnResetPwdListener {

    public final ObservableField<String> txt_phone = new ObservableField<>("");

//    public final ObservableField<Integer> txt_yanz = new ObservableField<>(ConstantUtils.checkCode?R.drawable.icon_vertification:R.drawable.icon_email);

    private RetrievePwdPresenter presenter;

    private final LogoutPresenter logoutPresenter = new LogoutPresenter(this);

    @Override
    protected int getContentViewId() {
        return R.layout.account_modify_pwd_input_phone_fragment;
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
        presenter = new RetrievePwdPresenter(this);
        resetUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    private void resetUI() {

        binding.tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.reqSmsBeforeCheck(getContext(),txt_phone.get(), InputPhoneFragment.this);
            }
        });
        if (ConstantUtils.checkCode){
            binding.etSms.setHint(getString(R.string.short_code));
            binding.etPhone.setHint(getString(R.string.hint_input_phone));
        }else if (ConstantUtils.checkEmail){
            binding.etSms.setHint(getString(R.string.mail_code));
            binding.etPhone.setHint(getString(R.string.hint_input_mail));
//            binding.etSms.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_email), null, null, null);
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

//        onResetPwdSuccess();
    }

//    private RetrievePwdActivity getRetrievePwdActivity() {
//        FragmentActivity activity = getActivity();
//        if (activity instanceof RetrievePwdActivity) {
//            return (RetrievePwdActivity) activity;
//        }
//        return null;
//    }

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
//        TioToast.showShort("密码设置成功，请登录");
////        LoginActivity.start(getActivity());
//        finish();

//        LoginActivity.start(getActivity());
//        finish();
        logoutPresenter.logout(getActivity());
    }
}
