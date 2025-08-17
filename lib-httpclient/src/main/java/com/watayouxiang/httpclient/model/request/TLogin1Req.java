package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.utils.MD5Utils;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/28
 *     desc   :
 *
 *     data 返回 uuid
 * </pre>
 */
public class TLogin1Req extends BaseReq<String> {

    private final String openid;
    private final String unionid;
    private final String type;
    private final String sign;

    /**
     * @param type    QQ 11 , 微信 22, 微博 33
     * @param openid  用户的openid
     * @param unionid unionid
     * @param token   token
     */
    public TLogin1Req(String type, String openid, String unionid, String token) {
        this.openid = openid;
        this.unionid = unionid;
        this.type = type;
        // md5(openid + token + type)
        this.sign = MD5Utils.getMd5(openid + token + type);
    }

    public static TLogin1Req getQQInstance(String openid, String unionid, String token) {
        return new TLogin1Req("11", openid, unionid, token);
    }

    public static TLogin1Req getWXInstance(String openid, String unionid, String token) {
        return new TLogin1Req("22", openid, unionid, token);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("openid", openid)
                .append("unionid", unionid)
                .append("sign", sign)
                ;
    }

    @Override
    public String path() {
        return "/mytio/tlogin/" + type + ".tio_x";
    }
}
