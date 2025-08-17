package com.watayouxiang.wallet.feature.wallet.mvp;

import android.app.Activity;
import android.content.Intent;

import com.blankj.utilcode.util.ToastUtils;
import com.ehking.sdk.wepay.interfaces.WalletPay;
import com.ehking.sdk.wepay.net.bean.AuthType;
import com.tiocloud.payease.PayEase;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.PayGetClientTokenReq;
import com.watayouxiang.httpclient.model.request.PayOpenFlagReq;
import com.watayouxiang.httpclient.model.response.PayGetClientTokenResp;
import com.watayouxiang.httpclient.model.response.PayGetWalletInfoResp;
import com.watayouxiang.httpclient.model.response.PayOpenFlagResp;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.TioWallet;
import com.watayouxiang.wallet.feature.open.OpenWalletActivity;
import com.watayouxiang.wallet.feature.wallet.WalletActivity;
import com.watayouxiang.wallet.tools.MoneyUtils;
import com.watayouxiang.wallet.widget.keyboard.InputPwdUtils;
import com.watayouxiang.wallet.widget.keyboard.OnPasswordInputFinish;
import com.watayouxiang.wallet.widget.keyboard.OpenCallback;

import java.util.ArrayList;
import java.util.Arrays;

public class Presenter extends Contract.Presenter {

    /**
     * 仅支持余额支付
     */
    private static final boolean isOnlySupportBalance = false;

    private WalletPay walletPay;

    public Presenter(Contract.View view) {
        super(new Model(), view, false);
    }

    public static void openWalletActivity(Activity activity) {
        Intent starter = new Intent(activity, WalletActivity.class);
        activity.startActivity(starter);

//        PayOpenFlagReq openFlagReq = new PayOpenFlagReq();
//        openFlagReq.setCancelTag(activity);
//        openFlagReq.get(new TioCallback<PayOpenFlagResp>() {
//            @Override
//            public void onTioSuccess(PayOpenFlagResp payOpenFlagResp) {
//                int openflag = payOpenFlagResp.getOpenflag();
//                if (true || openflag == 1) {
//                    // 已经开户
//                    Intent starter = new Intent(activity, WalletActivity.class);
//                    activity.startActivity(starter);
//                } else {
//                    // 未开户
//                    OpenWalletActivity.start(activity);
//                }
//            }
//
//            @Override
//            public void onTioError(String msg) {
//                ToastUtils.showShort(msg);
//            }
//        });
    }

    @Override
    public void init() {
        getView().resetUI();
//        initWalletPay();
        getWalletInfo();
    }

    private void initWalletPay() {
        walletPay = WalletPay.Companion.getInstance();
        // 调试开关（TAG：WalletPay | CustomGsonResponseBodyConverter）
        walletPay.setDebug(TioWallet.IS_DEBUG);
        // 单例销毁 (退出登录时需要调该方法)
        walletPay.destroy();
        // 初始化
        walletPay.init(getView().getActivity());
        // 使用随机键盘
        walletPay.setIsRandomKeyboard(true);
        // 设置页面颜色
        walletPay.setColor("#4C94FF");
        // 仅支持余额支付
        if (isOnlySupportBalance) {
            ArrayList<String> arrayList = (ArrayList<String>) Arrays.asList(
                    AuthType.TRANSFER.name(),
                    AuthType.REDPACKET.name(),
                    AuthType.ONLINEPAY.name()
            );
            walletPay.setOnlySupportBalance(true, arrayList);
        } else {
            walletPay.setOnlySupportBalance(false, new ArrayList<>());
        }
        // 活体配置(0静态活体，1动态活体)，默认动态活体。已废弃
        // walletPay.setBiopsy(0);
        // 回调
        walletPay.setWalletPayCallback((source, status, errorMessage) -> {
            if ("FAILURE".equals(status)) {
                if (errorMessage != null) {
                    TioToast.showShort(errorMessage);
                }
            } else if ("PROCESS".equals(status)) {

            } else if ("SUCCESS".equals(status)) {

            } else if ("CANCEL".equals(status)) {

            }
        });
    }

    @Override
    public void query$ShowMoney() {
        getWalletInfo(true);
    }

    @Override
    public void getWalletInfo() {
        getWalletInfo(false);
    }

    private void getWalletInfo(boolean mustShowMoney) {
        getModel().getWalletInfo(new BaseModel.DataProxy<PayGetWalletInfoResp>() {
            @Override
            public void onSuccess(PayGetWalletInfoResp resp) {
                super.onSuccess(resp);
                if (mustShowMoney || getView().isMoneyVisibility()) {
                    String balance = String.valueOf(resp.getAmount());
                    TioDBPreferences.saveCurrMoney(balance);
                    TioDBPreferences.setOpenWalletFlag(true);
                    String format = MoneyUtils.fen2yuan(balance);
                    if (format != null) {
                        getView().showMoney(format);
                    }
                }
            }
            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
//                ToastUtils.showShort(msg);
                if (msg != null && (msg.contains("未开户") || msg.contains("Chưa có tài khoản"))){
                    ToastUtils.showShort(getView().getActivity().getString(R.string.qingshezhizhifumima));
                    TioDBPreferences.setOpenWalletFlag(false);
                    InputPwdUtils.setPwdP(getView().getActivity(), true, true, new OpenCallback() {
                        @Override
                        public void result(boolean result, String msg) {
                            ToastUtils.showShort(msg);
                        }
                    });
                }

            }
        });
    }

    @Override
    public void getClientToken(@PayGetClientTokenReq.BizType String bizType) {
        getModel().getGetClientToken(bizType, new BaseModel.DataProxy<PayGetClientTokenResp>() {
            @Override
            public void onSuccess(PayGetClientTokenResp payGetClientTokenResp) {
                super.onSuccess(payGetClientTokenResp);

                evoke$SDK(bizType, payGetClientTokenResp);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                TioToast.showShort(msg);
            }
        });
    }

    private void evoke$SDK(String bizType, PayGetClientTokenResp resp) {
        walletPay.init(getView().getActivity());
        String merchantId = PayEase.MERCHANT_ID;
        String walletId = resp.getWalletId();
        String token = resp.getToken();
        walletPay.evoke(merchantId, walletId, token, bizType);
    }
}
