package com.watayouxiang.wallet.feature.recharge_result.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.watayouxiang.androidutils.page.TioFragment;
import com.watayouxiang.wallet.databinding.WalletRechargeResultFragmentFailedBinding;
import com.watayouxiang.wallet.feature.recharge_result.RechargeResult;
import com.watayouxiang.wallet.feature.recharge_result.RechargeResultActivity;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/20
 *     desc   :
 * </pre>
 */
public class FailedFragment extends TioFragment {

    public final ObservableField<Boolean> showMoney = new ObservableField<>(true);
    public final ObservableField<String> money = new ObservableField<>("200.00");
    public final ObservableField<String> failedReason = new ObservableField<>("账户余额不足");

    private WalletRechargeResultFragmentFailedBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = WalletRechargeResultFragmentFailedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setData(this);
        resetUI();
        initUI();
    }

    private void initUI() {
        RechargeResult result = getRechargeResultActivity().getRechargeResult();

        String money = result.getMoney();
        this.money.set(money);

        String failedReason = result.getFailedReason();
        this.failedReason.set(failedReason);
    }

    private void resetUI() {
        money.set("0.00");
        failedReason.set("");
    }

    public RechargeResultActivity getRechargeResultActivity() {
        return (RechargeResultActivity) getActivity();
    }

    public void clickOk(View v) {
        finish();
    }
}
