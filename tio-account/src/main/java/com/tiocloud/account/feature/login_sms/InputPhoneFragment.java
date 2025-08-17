package com.tiocloud.account.feature.login_sms;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.blankj.utilcode.util.FragmentUtils;
import com.tiocloud.account.R;
import com.tiocloud.account.TioAccount;
import com.tiocloud.account.databinding.AccountSmsLoginInputPhoneFragmentBinding;
import com.tiocloud.account.feature.login.LoginActivity;
import com.tiocloud.account.feature.register.RegisterActivity;
import com.tiocloud.account.mvp.login.LoginContract;
import com.tiocloud.account.mvp.login.LoginPresenter;
import com.watayouxiang.androidutils.listener.OnTioClickListener;
import com.watayouxiang.androidutils.page.easy.EasyFragment;
import com.watayouxiang.androidutils.util.ClickUtils;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/30
 *     desc   : 1、输入手机
 * </pre>
 */
public class InputPhoneFragment extends EasyFragment<AccountSmsLoginInputPhoneFragmentBinding>
        implements LoginContract.View {

    public static InputPhoneFragment getInstance() {
        return new InputPhoneFragment();
    }

    private InputPhoneFragment() {
    }

    public final ObservableField<String> txt_phone = new ObservableField<>("");
    private LoginPresenter presenter;

    @Override
    protected int getContentViewId() {
        return R.layout.account_sms_login_input_phone_fragment;
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
        presenter = new LoginPresenter(this);
        resetUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    private void resetUI() {
//        binding.titleBar.getTvRight().setOnClickListener(new OnTioClickListener() {
//            @Override
//            public void onSingleClick(View view) {
//                LoginActivity.start(getTioActivity());
//                finish();
//            }
//        });
        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        if (TioAccount.inviteEnable){
            binding.tvT2.setVisibility(View.INVISIBLE);
        }
    }

    // 确定按钮
    public void onClick_ok(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        String phone = txt_phone.get();
        if (phone == null) return;
        // 发送短信验证码
        presenter.sendSms(getTioActivity(), phone, (isRegiester) -> FragmentUtils.replace(this, InputCodeFragment.getInstance(phone, isRegiester)));
    }

    // 注册
    public void onClick_register(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        LoginActivity.start(getActivity());
        finish();
    }
}
