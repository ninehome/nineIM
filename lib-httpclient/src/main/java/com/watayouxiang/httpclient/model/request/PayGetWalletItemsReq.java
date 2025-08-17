package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.PayGetWalletItemsResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/26
 *     desc   :
 * </pre>
 */
public class PayGetWalletItemsReq extends BaseReq<PayGetWalletItemsResp> {

    /**
     * 类型：1 充值；2 提现；3 红包; null 全部
     */
    private final String mode;
    private final String pageNumber;

    public PayGetWalletItemsReq(String mode, String pageNumber) {
        this.mode = mode;
        this.pageNumber = pageNumber;
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("mode", mode)
                .append("pageNum", pageNumber)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<PayGetWalletItemsResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/walletLog/walletDetail.tio_x";
    }
}
