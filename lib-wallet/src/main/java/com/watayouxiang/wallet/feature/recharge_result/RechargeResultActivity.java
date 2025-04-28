package com.watayouxiang.wallet.feature.recharge_result;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.androidutils.page.BaseFragment;
import com.watayouxiang.androidutils.page.easy.EasyLightActivity;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.databinding.WalletRechargeResultActivityBinding;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/20
 *     desc   : 充值结果页
 * </pre>
 */
public class RechargeResultActivity extends EasyLightActivity<WalletRechargeResultActivityBinding> {
    private static final String RECHARGE_SERIAL_NUMBER = "RECHARGE_SERIAL_NUMBER";

    private RechargeResultViewModel viewModel;

    public static void start(Context context, String serialnumber) {
        Intent starter = new Intent(context, RechargeResultActivity.class);
        starter.putExtra(RECHARGE_SERIAL_NUMBER, serialnumber);
        context.startActivity(starter);
    }

    public String getSerialNumber() {
        return getIntent().getStringExtra(RECHARGE_SERIAL_NUMBER);
    }

    @NonNull
    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.wallet_recharge_result_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = newViewModel(RechargeResultViewModel.class);
        String serialNumber = getSerialNumber();
        if (serialNumber == null) return;

        viewModel.getRechargeQuery(serialNumber, this);
    }

    @Override
    public <T extends BaseFragment> void replaceFragment(T fragment) {
        fragment.setContainerId(binding.flContainer.getId());
        super.replaceFragment(fragment);
    }

    public RechargeResult getRechargeResult() {
        return viewModel.getRechargeResult();
    }
}
