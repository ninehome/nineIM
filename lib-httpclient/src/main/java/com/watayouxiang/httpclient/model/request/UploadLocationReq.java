package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;

import java.io.File;
import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/3/9
 * desc :
 *
 * @see String
 */
public class UploadLocationReq extends BaseReq<String> {
    /**
     * 会话id
     */
    private final String chatlinkid;
    /**
     * 单文件上传
     */
    private final String imgPath;

    private final String lat;

    private final String lng;

    private final String addr;

    public UploadLocationReq(String chatlinkid, String imgPath, String lat, String lng, String addr) {
        this.chatlinkid = chatlinkid;
        this.imgPath = imgPath;
        this.lat = lat;
        this.lng = lng;
        this.addr = addr;
    }

    @Override
    public String path() {
        return "/mytio/chat/location.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("chatlinkid", chatlinkid)
                .append("lat", lat)
                .append("lng", lng)
                .append("address", addr)
                ;
    }

    @Override
    public TioMap<String, File> files() {
        return super.files()
                .append("uploadFile", new File(imgPath))
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
