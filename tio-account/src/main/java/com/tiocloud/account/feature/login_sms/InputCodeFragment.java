package com.tiocloud.account.feature.login_sms;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.tiocloud.account.R;
import com.tiocloud.account.databinding.AccountSmsLoginInputCodeFragmentBinding;
import com.tiocloud.account.feature.login.InputNickActivity;
import com.tiocloud.account.feature.login.LoginActivity;
import com.tiocloud.account.mvp.login.LoginContract;
import com.tiocloud.account.mvp.login.LoginPresenter;
import com.tiocloud.account.widget.PhoneCodeView;
import com.watayouxiang.androidutils.page.easy.EasyFragment;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.httpclient.model.request.CodeRegisterReq;
import com.watayouxiang.httpclient.model.request.PhoneRegisterReq;

import java.util.Locale;
import java.util.UUID;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/30
 *     desc   : 2、输入验证码
 * </pre>
 */
public class InputCodeFragment extends EasyFragment<AccountSmsLoginInputCodeFragmentBinding>
        implements LoginContract.View, FragmentUtils.OnBackClickListener, LoginContract.OnTimerListener, LoginContract.OnSendSmsListener {

    private static final String KEY_PHONE = "KEY_PHONE";

    public final ObservableField<String> txt_code = new ObservableField<>("");
    public final ObservableField<Boolean> isStartTimer = new ObservableField<>(false);

    private LoginPresenter presenter;


    private InputCodeFragment() {
    }

    public static InputCodeFragment getInstance(@NonNull String phone, boolean isRegiester) {
        InputCodeFragment fragment = new InputCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PHONE, phone);
        bundle.putBoolean("isRegiester", isRegiester);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    private String getPhone() {
        return getArguments().getString(KEY_PHONE);
    }

    private boolean isRegiester(){
        return getArguments().getBoolean("isRegiester", false);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.account_sms_login_input_code_fragment;
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
        presenter = new LoginPresenter(this);
        resetUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public boolean onBackClick() {
        FragmentUtils.replace(this, InputPhoneFragment.getInstance());
        return true;
    }

    private void resetUI() {
        onSendSmsSuccess(false);
        binding.phone.setText(getPhone());
        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().finish();
                onBackClick();
            }
        });
        binding.phoneCode.setOnInputListener(new PhoneCodeView.OnInputListener() {
            @Override
            public void onSucess(String code) {
                if (isRegiester()){
                    toRegiester(getPhone(), code);
                }else {
                    presenter.codeLogin(getPhone(), code, getTioActivity());
                }
            }

            @Override
            public void onInput() {

            }
        });
        binding.phoneCode.showSoftInput();
    }

    private void toRegiester(String phone, String code) {
        Intent intent = new Intent(getActivity(), InputNickActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("phone", phone);
        startActivity(intent);
        ActivityUtils.finishAllActivities();
    }

    // 确定按钮
    public void onClick_ok(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        presenter.codeLogin(getPhone(), txt_code.get(), getTioActivity());
    }

    // 获取验证码
    public void onClick_reqPhoneCode(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        presenter.sendSms(getTioActivity(), getPhone(), this);
    }

    @Override
    public void OnTimerRunning(int second) {
        isStartTimer.set(true);
//        binding.tvReqPhoneCode.setText(String.format(Locale.getDefault(), "已发送(%ds)", second));
    }

    @Override
    public void OnTimerStop() {
        isStartTimer.set(false);
//        binding.tvReqPhoneCode.setText("获取验证码");
    }

    @Override
    public void onSendSmsSuccess(boolean isRegiester) {
//        presenter.startCodeTimer(this);
    }
}
