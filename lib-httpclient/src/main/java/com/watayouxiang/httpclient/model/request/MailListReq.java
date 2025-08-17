package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-14
 * desc : 通讯录列表
 *
 * @see MailListResp
 */
public class MailListReq extends BaseReq<MailListResp> {
    /**
     * 通讯录类型：
     * 1：好友
     * 2：群聊
     * null：全部
     */
    private final String mode;
    /**
     * 搜索字段
     */
    private final String searchkey;

    public MailListReq(String mode, String searchkey) {
        this.mode = mode;
        this.searchkey = searchkey;
    }

    @Override
    public String path() {
        return "/mytio/chat/mailList.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("mode", mode)
                .append("searchkey", searchkey)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<MailListResp>>() {
        }.getType();
    }
}
