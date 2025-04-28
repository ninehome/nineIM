package com.watayouxiang.httpclient.model.request;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.CommonResp;
import com.watayouxiang.httpclient.model.response.DealApplyResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-24
 * desc : 处理申请
 */
public class SendFaceEmotionReq extends BaseReq<String> {
    private String emotion;
    private Integer type;
    private String chatlinkid;
    private int width;
    private int height;

    public SendFaceEmotionReq(String emotion, Integer type, String chatlinkid) {
        this.emotion = emotion;
        this.type = type;
        this.chatlinkid = chatlinkid;
    }

    public SendFaceEmotionReq setWidthHeight(int w, int h){
        this.width = w;
        this.height = h;
        return this;
    }

    @Override
    public String path() {
        return "/mytio/chat/emotion.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("type", type.toString())
                .append("emotion", emotion)
                .append("chatlinkid", chatlinkid)
                .append("width", width+"")
                .append("height", height+"")
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
