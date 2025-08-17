package com.watayouxiang.wallet.feature.recharge.mvp;

import com.blankj.utilcode.util.ToastUtils;
import com.ehking.sdk.wepay.interfaces.WalletPay;
import com.ehking.sdk.wepay.net.bean.AuthType;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.model.response.PayRechargeResp;
import com.watayouxiang.wallet.feature.recharge_result.RechargeResultActivity;
import com.watayouxiang.wallet.tools.MoneyUtils;

public class Presenter extends Contract.Presenter {
    public Presenter(Contract.View view) {
        super(new Model(), view, false);
    }

    @Override
    public void init() {
        getView().resetUI();
    }

    @Override
    public void walletRecharge(String amount) {
        String format = MoneyUtils.yuan2fen(amount);
        if (format == null) {
            TioToast.showShort("金额格式化错误");
            return;
        }

        getModel().postRecharge(format, new BaseModel.DataProxy<PayRechargeResp>() {
            @Override
            public void onSuccess(PayRechargeResp payRechargeResp) {
                super.onSuccess(payRechargeResp);

                evokeRecharge$SDK(payRechargeResp);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                ToastUtils.showShort(msg);
            }
        });
    }

    private void evokeRecharge$SDK(PayRechargeResp resp) {
        String merchantId = resp.getMerchantId();
        String walletId = resp.getWalletId();
        String token = resp.getToken();

        WalletPay walletPay = WalletPay.Companion.getInstance();
        walletPay.init(getView().getActivity());
        walletPay.setWalletPayCallback((source, status, errorMessage) -> {

            if ("SUCCESS".equals(status) || "PROCESS".equals(status)) {
                getView().finish();
                RechargeResultActivity.start(getView().getActivity(), resp.getSerialnumber());
            } else {
                TioToast.showShort(errorMessage);
            }

        });
        walletPay.evoke(merchantId, walletId, token, AuthType.RECHARGE.name());
    }
}
