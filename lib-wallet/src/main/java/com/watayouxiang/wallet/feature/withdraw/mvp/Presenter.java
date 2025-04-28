package com.watayouxiang.wallet.feature.withdraw.mvp;

import com.blankj.utilcode.util.KeyboardUtils;
import com.ehking.sdk.wepay.interfaces.WalletPay;
import com.ehking.sdk.wepay.net.bean.AuthType;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.model.response.PayGetWalletInfoResp;
import com.watayouxiang.httpclient.model.response.PayWithholdResp;
import com.watayouxiang.wallet.feature.withdraw_result.WithdrawResultActivity;
import com.watayouxiang.wallet.tools.MoneyUtils;

public class Presenter extends Contract.Presenter {
    public Presenter(Contract.View view) {
        super(new Model(), view, false);
    }

    @Override
    public void init() {
        getView().resetUI();
        getWalletInfo();
    }

    @Override
    public void getWalletInfo(boolean withdrawAll) {
        getModel().getWalletInfo(new BaseModel.DataProxy<PayGetWalletInfoResp>() {
            @Override
            public void onSuccess(PayGetWalletInfoResp resp) {
                super.onSuccess(resp);
                String balance = String.valueOf(resp.getAmount());
                TioDBPreferences.saveCurrMoney(balance);
                String format = MoneyUtils.fen2yuan(balance);
                if (format == null) return;

                getView().onMoneyResp(format);
                if (withdrawAll) {
                    getView().onWithdrawAllResp(format);
                }
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
            }
        });
    }

    @Override
    public void getWalletInfo() {
        getWalletInfo(false);
    }

    @Override
    public void postWithdraw(String amount) {
        String points = MoneyUtils.yuan2fen(amount);
        if (points == null) {
            TioToast.showShort("金额转换错误");
            return;
        }

        getModel().postWithhold(new BaseModel.DataProxy<PayWithholdResp>() {
            @Override
            public void onSuccess(PayWithholdResp payWithholdResp) {
                super.onSuccess(payWithholdResp);

                postWithhold$SDK(payWithholdResp, points);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                TioToast.showShort(msg);
            }
        }, points);
    }

    private void postWithhold$SDK(PayWithholdResp resp, String points) {
        String merchantId = resp.getMerchantId();
        String walletId = resp.getWalletId();
        String token = resp.getToken();
        String serialNumber = resp.getSerialnumber();

        WalletPay walletPay = WalletPay.Companion.getInstance();
        walletPay.init(getView().getActivity());
        walletPay.setWalletPayCallback((source, status, errorMessage) -> {

            if ("SUCCESS".equals(status) || "PROCESS".equals(status)) {
                getView().finish();
                WithdrawResultActivity.start(getView().getActivity(), serialNumber);
                KeyboardUtils.hideSoftInput(getView().getActivity());
            } else {
                TioToast.showShort(errorMessage);
            }

        });
        walletPay.evoke(merchantId, walletId, token, AuthType.WITHHOLDING.name());
    }
}
