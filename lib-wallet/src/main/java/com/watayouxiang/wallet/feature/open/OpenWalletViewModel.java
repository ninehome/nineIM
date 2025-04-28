package com.watayouxiang.wallet.feature.open;

import androidx.lifecycle.ViewModel;

import com.blankj.utilcode.util.ToastUtils;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.PayOpenReq;
import com.watayouxiang.httpclient.model.response.PayOpenResp;
import com.watayouxiang.wallet.feature.wallet.WalletActivity;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/04
 *     desc   :
 * </pre>
 */
public class OpenWalletViewModel extends ViewModel {

    public void reqOpenWallet(OpenWalletActivity activity) {
        String name = activity.userName.get();
        String cardNo = activity.userId.get();
        String phone = activity.userPhone.get();
        PayOpenReq payOpenReq = new PayOpenReq(name, cardNo, phone);
        payOpenReq.setCancelTag(activity);
        payOpenReq.post(new TioCallback<PayOpenResp>() {
            @Override
            public void onTioSuccess(PayOpenResp payOpenResp) {
                WalletActivity.start(activity);
            }

            @Override
            public void onTioError(String msg) {
                ToastUtils.showShort(msg);
            }
        });
    }
}
