package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.WxCoinItemListResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/19
 *     desc   :
 * </pre>
 */
public class WxCoinItemListReq extends BaseReq<WxCoinItemListResp> {
    /**
     * 类型：暂时可不传
     */
    private String mode;
    private final String pageNumber;
    private final String pageSize;

    public WxCoinItemListReq(String pageNumber, String pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public WxCoinItemListReq(String mode, String pageNumber, String pageSize) {
        this.mode = mode;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("mode", mode)
                .append("pageNumber", pageNumber)
                .append("pageSize", pageSize)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<WxCoinItemListResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/wxcoin/itemlist.tio_x";
    }
}
