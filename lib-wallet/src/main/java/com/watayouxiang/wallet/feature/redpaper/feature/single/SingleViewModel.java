package com.watayouxiang.wallet.feature.redpaper.feature.single;

import androidx.lifecycle.ViewModel;

import com.ehking.sdk.wepay.interfaces.WalletPay;
import com.ehking.sdk.wepay.net.bean.AuthType;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.PaySendRedPacketReq;
import com.watayouxiang.httpclient.model.response.PaySendRedPacketResp;
import com.watayouxiang.wallet.feature.redpaper.RedPaperActivity;
import com.watayouxiang.wallet.tools.MoneyUtils;
import com.watayouxiang.wallet.widget.keyboard.InputPwdUtils;
import com.watayouxiang.wallet.widget.keyboard.OnEncryPasswordInputFinish;
import com.watayouxiang.wallet.widget.keyboard.OnPasswordInputFinish;

import java.math.BigDecimal;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/09
 *     desc   :
 * </pre>
 */
public class SingleViewModel extends ViewModel {

    public void postSendRedPacket(SingleFragment fragment) {
        RedPaperActivity activity = fragment.getRedPaperActivity();
        String packetType = "1";
        String chatlinkid = activity.getRedPaperVo().chatlinkid;
        String singleAmount = MoneyUtils.yuan2fen(fragment.money.get());
        String packetCount = fragment.amount.get();
        String amount = getAmount(singleAmount, packetCount);
        String remark = fragment.getGiftTxt();

        InputPwdUtils.input(fragment.getActivity(),Integer.parseInt(amount), new OnEncryPasswordInputFinish() {
            @Override
            public void pwd(String password, long t) {
                PaySendRedPacketReq paySendRedPacketReq = new PaySendRedPacketReq(packetType, amount, chatlinkid, singleAmount, packetCount, remark);
                paySendRedPacketReq.setPasswd(password, t);
                paySendRedPacketReq.setCancelTag(activity);
                paySendRedPacketReq.post(new TioCallback<PaySendRedPacketResp>() {
                    @Override
                    public void onTioSuccess(PaySendRedPacketResp paySendRedPacketResp) {
                        fragment.getActivity().finish();
                        //发送红包成功
//                evoke$SDK(paySendRedPacketResp, fragment);
                    }

                    @Override
                    public void onTioError(String msg) {
                        TioToast.showShort(msg);
                    }
                });
            }
        });


    }

    private void evoke$SDK(PaySendRedPacketResp resp, SingleFragment fragment) {
        String merchantId = resp.getMerchantId();
        String walletId = resp.getWalletId();
        String token = resp.getToken();

        WalletPay walletPay = WalletPay.Companion.getInstance();
        walletPay.init(fragment.getRedPaperActivity());
        walletPay.setWalletPayCallback((source, status, errorMessage) -> {

            if ("SUCCESS".equals(status) || "PROCESS".equals(status)) {
                fragment.finish();
            } else {
                TioToast.showShort(errorMessage);
            }
//            TioToast.showLong(String.format(Locale.getDefault(),
//                    "source = %s, status = %s, errorMessage = %s",
//                    source, status, errorMessage));

        });
        walletPay.evoke(merchantId, walletId, token, AuthType.REDPACKET.name());
    }

    private String getAmount(String singleAmount, String packetCount) {
        BigDecimal _singleAmount = new BigDecimal(singleAmount);
        BigDecimal _packetCount = new BigDecimal(packetCount);
        BigDecimal temp = _singleAmount.multiply(_packetCount);
        return temp.toString();
    }
}
