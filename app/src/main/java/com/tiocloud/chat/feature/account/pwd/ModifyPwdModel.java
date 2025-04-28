package com.tiocloud.chat.feature.account.pwd;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.UpdatePwdReq;
import com.watayouxiang.httpclient.model.request.UserCurrReq;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public class ModifyPwdModel extends BaseModel {

    public void updatePwd(String oldPwd, String newPwd, final DataProxy<Void> proxy) {
        new UserCurrReq().setCancelTag(this).get(new TioCallback<UserCurrResp>() {
            @Override
            public void onTioSuccess(UserCurrResp resp) {
                String phone = resp.phone;
                String email = resp.email;
                updatePwdInternal(phone, email, oldPwd, newPwd, proxy);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }
        });
    }

    private void updatePwdInternal(String phone, String email, String oldPwd, String newPwd, DataProxy<Void> proxy) {
        new UpdatePwdReq(oldPwd, newPwd, phone, email).setCancelTag(this).post(new TioCallback<Void>() {
            @Override
            public void onTioSuccess(Void aVoid) {
                proxy.onSuccess(null);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }
        });
    }

}
