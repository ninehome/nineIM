package com.tiocloud.account.feature.t_bind_phone;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.AppUtils;
import com.tiocloud.account.R;
import com.tiocloud.account.databinding.AccountTBindPhoneFragmentBinding;
import com.tiocloud.account.mvp.t_bind_phone.TBindPhoneContract;
import com.tiocloud.account.mvp.t_bind_phone.TBindPhonePresenter;
import com.watayouxiang.androidutils.page.easy.EasyFragment;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.androidutils.widget.TioToast;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/28
 *     desc   : 三方登录绑定手机号
 * </pre>
 */
public class TBindPhoneFragment extends EasyFragment<AccountTBindPhoneFragmentBinding> implements TBindPhoneContract.View {

    public final ObservableField<String> txt_phone = new ObservableField<>("");
    public final ObservableField<String> txt_code = new ObservableField<>("");
    public final ObservableField<Boolean> isStartTimer = new ObservableField<>(false);

    private TBindPhonePresenter presenter;

    @Override
    protected int getContentViewId() {
        return R.layout.account_t_bind_phone_fragment;
    }

    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

    @Override
    protected Integer getStatusBarColor() {
//        return Color.parseColor("#DBEAFF");
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
        presenter = new TBindPhonePresenter(this);
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
        presenter.bindPhone(getContext(),txt_code.get(), txt_phone.get());
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
        binding.tvReqPhoneCode.setText(String.format(Locale.getDefault(),
                getString(R.string.sended)+"(%ds)", second));
    }

    @Override
    public void onCodeTimerStop() {
        isStartTimer.set(false);
        binding.tvReqPhoneCode.setText(getString(R.string.get_code));
    }

    @Override
    public void onTBindPhoneSuccess() {
        TioToast.showShort(getString(R.string.third_bind_phone_success));
        finish();
        // 重启app，刷新数据
        AppUtils.relaunchApp(true);
    }

    private TBindPhoneActivity getTBindPhoneActivity() {
        FragmentActivity activity = getActivity();
        if (activity instanceof TBindPhoneActivity) {
            return (TBindPhoneActivity) activity;
        }
        return null;
    }

    /**
     * 点击返回回调
     *
     * @return 是否返回上一页
     */
    public boolean onBackPressed() {
        presenter.showBackConfirmDialog(getTioActivity());
        return false;
    }
}
