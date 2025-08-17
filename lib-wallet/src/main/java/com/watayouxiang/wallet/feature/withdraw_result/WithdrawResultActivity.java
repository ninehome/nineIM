package com.watayouxiang.wallet.feature.withdraw_result;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.androidutils.page.BaseFragment;
import com.watayouxiang.androidutils.page.easy.EasyLightActivity;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.databinding.WalletWithdrawResultActivityBinding;
import com.watayouxiang.wallet.feature.withdraw_record.WithdrawRecordActivity;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/20
 *     desc   : 提现结果页
 * </pre>
 */
public class WithdrawResultActivity extends EasyLightActivity<WalletWithdrawResultActivityBinding> {
    private static final String KEY_SERIAL_NUMBER = "KEY_SERIAL_NUMBER";

    private WithdrawResultViewModel viewModel;

    public static void start(Context context, String serialnumber) {
        Intent starter = new Intent(context, WithdrawResultActivity.class);
        starter.putExtra(KEY_SERIAL_NUMBER, serialnumber);
        context.startActivity(starter);
    }

    public WithdrawResult getWithdrawResult() {
        return viewModel.getWithdrawResult();
    }

    public String getSerialNumber() {
        return getIntent().getStringExtra(KEY_SERIAL_NUMBER);
    }

    @Override
    protected Integer getBackgroundColor() {
        return Color.parseColor("#F8F8F8");
    }

    @NonNull
    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.wallet_withdraw_result_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String serialNumber = getSerialNumber();
        if (serialNumber == null) return;

        // titleBar - rightBtn
        binding.titleBar.getTvRight().setOnClickListener(this::clickWithdrawRecord);

        viewModel = newViewModel(WithdrawResultViewModel.class);
        viewModel.getWithholdQuery(serialNumber, this);
    }

    // 提现记录
    private void clickWithdrawRecord(View view) {
        if (ClickUtils.isViewSingleClick(view))
            WithdrawRecordActivity.start(this);
    }

    @Override
    public <T extends BaseFragment> void replaceFragment(T fragment) {
        fragment.setContainerId(binding.flContainer.getId());
        super.replaceFragment(fragment);
    }
}
