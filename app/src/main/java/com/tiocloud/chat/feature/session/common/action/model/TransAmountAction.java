package com.tiocloud.chat.feature.session.common.action.model;

import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.model.Response;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseRedPaperAction;
import com.tiocloud.chat.feature.session.p2p.P2PSessionActivity;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.UserInfoReq;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.wallet.feature.trans_amount.TransAmountActivity;

/**
 * author : TaoWang
 * date : 2020/3/5
 * desc :
 */
public class TransAmountAction extends BaseRedPaperAction {
    public TransAmountAction() {
        super(R.drawable.icon_im_transfer, R.string.trans_amount);
    }

    @Override
    public void onClick() {
        if (!(activity instanceof P2PSessionActivity)){
            ToastUtils.showShort("不支持的转账功能");
            return;
        }
        String uid = ((P2PSessionActivity) activity).getUid();
        UserInfoReq req = new UserInfoReq(uid);
        TioHttpClient.get(this, req, new TaoCallback<BaseResp<UserInfoResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<UserInfoResp>> response) {
                UserInfoResp data = response.body().getData();
                if (data == null){
                    return;
                }
                TransAmountActivity.start(activity, data);
            }
        });

    }
}
