package com.watayouxiang.wallet.feature.withdraw_result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.watayouxiang.androidutils.page.TioFragment;
import com.watayouxiang.wallet.databinding.WalletWithdrawResultFragmentBinding;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/20
 *     desc   :
 * </pre>
 */
public class WithdrawFragment extends TioFragment {

    public final ObservableField<String> amountInfo = new ObservableField<>("20.00元");
    public final ObservableField<String> bankInfo = new ObservableField<>("民生银行(0238)");
    public final ObservableField<String> feeInfo = new ObservableField<>("0.02元");
    public final ObservableField<String> withdrawStatus = new ObservableField<>("提现申请成功，等待银行处理");
    public final ObservableField<String> withdrawTip = new ObservableField<>("预计2小时内到账");

    private WalletWithdrawResultFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = WalletWithdrawResultFragmentBinding.inflate(inflater, container, false);
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
        WithdrawResult result = getWithdrawResultActivity().getWithdrawResult();
        if (result == null) return;

        String _withdrawAmount = result.getWithdrawAmount();
        String _withdrawFee = result.getWithdrawFee();
        String _bankInfo = result.getBankInfo();
        String _bankIcon = result.getBankIcon();

        if (_withdrawAmount != null) {
            amountInfo.set(_withdrawAmount + "元");
        }
        if (_withdrawFee != null) {
            feeInfo.set(_withdrawFee + "元");
        }
        if (_bankInfo != null) {
            bankInfo.set(_bankInfo);
        }
        if (_bankIcon != null) {
            binding.ivBankIcon.loadStatic(_bankIcon);
        }
    }

    public void clickOk(View v) {
        finish();
    }

    private void resetUI() {
        amountInfo.set("");
        bankInfo.set("");
        feeInfo.set("");
        withdrawStatus.set("提现申请成功，等待银行处理");
        withdrawTip.set("预计2小时内到账");
    }

    public WithdrawResultActivity getWithdrawResultActivity() {
        return (WithdrawResultActivity) getActivity();
    }
}
