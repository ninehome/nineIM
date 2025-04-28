package com.tiocloud.account.feature.bind_phone;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;

import com.tiocloud.account.R;
import com.tiocloud.account.databinding.AccountBindPhoneFragmentBinding;
import com.tiocloud.account.mvp.bind_phone.BindPhoneContract;
import com.tiocloud.account.mvp.bind_phone.BindPhonePresenter;
import com.tiocloud.account.mvp.logout.LogoutPresenter;
import com.watayouxiang.androidutils.page.easy.EasyFragment;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.oper.EasyOperDialog;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/24
 *     desc   : 绑定手机号
 * </pre>
 */
public class BindPhoneFragment extends EasyFragment<AccountBindPhoneFragmentBinding> implements BindPhoneContract.View {

    public final ObservableField<String> txt_phone = new ObservableField<>("");
    public final ObservableField<String> txt_code = new ObservableField<>("");
    public final ObservableField<String> txt_pwd = new ObservableField<>("");
    public final ObservableField<Boolean> isStartTimer = new ObservableField<>(false);

    private BindPhonePresenter presenter;

    @Override
    protected int getContentViewId() {
        return R.layout.account_bind_phone_fragment;
    }

    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

    @Override
    protected Integer getStatusBarColor() {
        return Color.WHITE;
    }

    @Override
    protected Boolean getStatusBarLightMode() {
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setData(this);
        presenter = new BindPhonePresenter(this);
        resetUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    private void resetUI() {
        // 返回按钮
        binding.titleBar.getIvBack().setOnClickListener(view -> onBackPressed());
    }

    // 绑定手机号
    public void onClick_ok(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        presenter.bindPhone(getContext(),txt_code.get(), txt_phone.get(), txt_pwd.get());
    }

    // 获取验证码
    public void onClick_reqPhoneCode(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        presenter.reqSendSms(getActivity(), txt_phone.get());
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
        binding.tvReqPhoneCode.setText("获取验证码");
    }

    @Override
    public void onBindPhoneSuccess() {
        TioToast.showShort("手机绑定成功");
        finish();
    }

    private BindPhoneActivity getBindPhoneActivity() {
        FragmentActivity activity = getActivity();
        if (activity instanceof BindPhoneActivity) {
            return (BindPhoneActivity) activity;
        }
        return null;
    }

    /**
     * 点击返回回调
     *
     * @return 是否返回上一页
     */
    public boolean onBackPressed() {
        new EasyOperDialog.Builder("确定要退出吗？")
                .setPositiveBtnTxt("退出")
                .setNegativeBtnTxt("取消")
                .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                    @Override
                    public void onClickPositive(View view, EasyOperDialog dialog) {
                        BindPhoneFragment.this.finish();
                        LogoutPresenter.kickOut();
                    }

                    @Override
                    public void onClickNegative(View view, EasyOperDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show_unCancel(getTioActivity());
        return false;
    }
}
