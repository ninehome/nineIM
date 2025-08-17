package com.tiocloud.account.feature.retrieve_pwd;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.StringUtils;
import com.tiocloud.account.R;
import com.tiocloud.account.databinding.AccountRetrievePwdResetPwdFragmentBinding;
import com.tiocloud.account.feature.login.LoginActivity;
import com.tiocloud.account.mvp.retrieve_pwd.RetrievePwdContract;
import com.tiocloud.account.mvp.retrieve_pwd.RetrievePwdPresenter;
import com.watayouxiang.androidutils.page.easy.EasyFragment;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.androidutils.widget.TioToast;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/24
 *     desc   : 3、重置密码
 * </pre>
 */
public class ResetPwdFragment extends EasyFragment<AccountRetrievePwdResetPwdFragmentBinding>
        implements RetrievePwdContract.View, RetrievePwdContract.OnResetPwdListener {

    private static final String KEY_SMS_CODE = "KEY_SMS_CODE";
    private static final String KEY_PHONE = "KEY_PHONE";

    public final ObservableField<String> txt_pwd = new ObservableField<>("");
    public final ObservableField<String> txt_pwd2 = new ObservableField<>("");

    private RetrievePwdPresenter presenter;

    private ResetPwdFragment() {
    }

    public static ResetPwdFragment getInstance(@NonNull String smsCode, @NonNull String phone) {
        ResetPwdFragment fragment = new ResetPwdFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_SMS_CODE, smsCode);
        bundle.putString(KEY_PHONE, phone);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String getSmsCode() {
        return getArguments().getString(KEY_SMS_CODE);
    }

    private String getPhone() {
        return getArguments().getString(KEY_PHONE);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.account_retrieve_pwd_reset_pwd_fragment;
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
        if (!StringUtils.equals(txt_pwd.get(), txt_pwd2.get())) {
            TioToast.showShort(getString(R.string.double_input_pwd_unsame));
            return;
        }

        presenter.reqResetPwd(getSmsCode(), getPhone(), txt_pwd.get(), this);
    }

    private RetrievePwdActivity getRetrievePwdActivity() {
        FragmentActivity activity = getActivity();
        if (activity instanceof RetrievePwdActivity) {
            return (RetrievePwdActivity) activity;
        }
        return null;
    }

    @Override
    public void onResetPwdSuccess() {
        TioToast.showShort(getString(R.string.pwd_set_success_login));
        LoginActivity.start(getActivity());
        finish();
    }
}
