package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.PayGetWalletInfoResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/17
 *     desc   :
 * </pre>
 */
public class PayOpenWalletReq extends BaseReq<PayGetWalletInfoResp> {

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("passwd", passwd)
                ;
    }

    private String passwd;

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<PayGetWalletInfoResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/wallet/open.tio_x";
    }
}
