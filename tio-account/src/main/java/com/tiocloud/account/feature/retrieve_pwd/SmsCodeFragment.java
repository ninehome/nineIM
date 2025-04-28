package com.tiocloud.account.feature.retrieve_pwd;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;

import com.tiocloud.account.R;
import com.tiocloud.account.databinding.AccountRetrievePwdSmsCodeFragmentBinding;
import com.tiocloud.account.mvp.retrieve_pwd.RetrievePwdContract;
import com.tiocloud.account.mvp.retrieve_pwd.RetrievePwdPresenter;
import com.watayouxiang.androidutils.page.easy.EasyFragment;
import com.watayouxiang.androidutils.util.ClickUtils;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/24
 *     desc   : 2、获取验证码
 * </pre>
 */
public class SmsCodeFragment extends EasyFragment<AccountRetrievePwdSmsCodeFragmentBinding> implements RetrievePwdContract.View,
        RetrievePwdContract.OnSendSmsListener, RetrievePwdContract.OnCodeTimerListener, RetrievePwdContract.OnSmsCheckListener {

    private static final String KEY_PHONE = "KEY_PHONE";

    public final ObservableField<String> txt_code = new ObservableField<>("");
    public final ObservableField<Boolean> isStartTimer = new ObservableField<>(false);

    private RetrievePwdPresenter presenter;

    private SmsCodeFragment() {
    }

    public static SmsCodeFragment getInstance(@NonNull String phone) {
        SmsCodeFragment fragment = new SmsCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PHONE, phone);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    private String getPhone() {
        return getArguments().getString(KEY_PHONE);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.account_retrieve_pwd_sms_code_fragment;
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

    }

    // 确定按钮
    public void onClick_ok(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        presenter.reqSmsCheck(txt_code.get(), getPhone(), this);
    }

    // 获取验证码
    public void onClick_reqPhoneCode(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        presenter.reqSendSms(getActivity(), getPhone(), this);

    }

    @Override
    public void onSendSmsSuccess() {
        presenter.startCodeTimer(60, this);
    }

    @Override
    public void onCodeTimerRunning(int second) {
        isStartTimer.set(true);
        binding.tvReqPhoneCode.setText(String.format(Locale.getDefault(), getString(R.string.sended)+"(%ds)", second));
    }

    @Override
    public void onCodeTimerStop() {
        isStartTimer.set(false);
        binding.tvReqPhoneCode.setText(getString(R.string.get_code));
    }

    private RetrievePwdActivity getRetrievePwdActivity() {
        FragmentActivity activity = getActivity();
        if (activity instanceof RetrievePwdActivity) {
            return (RetrievePwdActivity) activity;
        }
        return null;
    }

    @Override
    public void onSmsCheckSuccess() {
        getRetrievePwdActivity().showResetPwdFragment(txt_code.get(), getPhone());
    }
}
