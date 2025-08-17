package com.watayouxiang.httpclient.model.request;

import androidx.annotation.StringDef;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.PayGetClientTokenResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/18
 *     desc   :
 * </pre>
 */
public class PayGetClientTokenReq extends BaseReq<PayGetClientTokenResp> {

    public static final String ACCESS_SAFETY = "ACCESS_SAFETY";
    public static final String ACCESS_CARDlIST = "ACCESS_CARDlIST";

    /**
     * 更多见：{@link com.ehking.sdk.wepay.net.bean.AuthType}
     */
    @StringDef({ACCESS_SAFETY, ACCESS_CARDlIST})
    public @interface BizType {
    }

    /**
     * 唤起安全设置页面：ACCESS_SAFETY; 唤起卡列表页面：ACCESS_CARDlIST
     */
    private final String bizType;

    public PayGetClientTokenReq(@BizType String bizType) {
        this.bizType = bizType;
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("bizType", bizType)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<PayGetClientTokenResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/pay/getClientToken.tio_x";
    }
}
