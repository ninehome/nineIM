package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.TLogin2Resp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/28
 *     desc   :
 * </pre>
 */
public class TLogin2Req extends BaseReq<TLogin2Resp> {

    private final String type;
    private final String unionid;
    private final String uuid;
    private final String openid;
    private final String nick;
    private final String avatar;
    private final String sex;
    private final String qq_is_yellow_vip;
    private final String qq_yellow_vip_level;
    private final String wx_country;
    private final String wx_province;
    private final String wx_city;

    /**
     * 三方登录第二步
     *
     * @param type                QQ登录: 11, 微信登录: 22, 微博登录: 33
     * @param unionid
     * @param uuid
     * @param openid
     * @param nick
     * @param avatar
     * @param sex                 整数。1: 男， 2: 女
     * @param qq_is_yellow_vip    标识用户是否为黄钻用户（0：不是；1：是）。
     * @param qq_yellow_vip_level 黄钻等级
     * @param wx_country          国家
     * @param wx_province         省
     * @param wx_city             市
     */
    public TLogin2Req(String type, String unionid, String uuid, String openid, String nick, String avatar, String sex,
                      String qq_is_yellow_vip, String qq_yellow_vip_level,
                      String wx_country, String wx_province, String wx_city) {
        this.type = type;
        this.unionid = unionid;
        this.uuid = uuid;
        this.openid = openid;
        this.nick = nick;
        this.avatar = avatar;
        this.sex = sex;
        this.qq_is_yellow_vip = qq_is_yellow_vip;
        this.qq_yellow_vip_level = qq_yellow_vip_level;
        this.wx_country = wx_country;
        this.wx_province = wx_province;
        this.wx_city = wx_city;
    }

    public static TLogin2Req getQQInstance(String unionid, String uuid, String openid, String nick, String avatar, String sex,
                                           String qq_is_yellow_vip, String qq_yellow_vip_level) {
        return new TLogin2Req("11", unionid, uuid, openid, nick, avatar, sex, qq_is_yellow_vip, qq_yellow_vip_level,
                null, null, null);
    }

    public static TLogin2Req getWXInstance(String unionid, String uuid, String openid, String nick, String avatar, String sex,
                                           String wx_country, String wx_province, String wx_city) {
        return new TLogin2Req("22", unionid, uuid, openid, nick, avatar, sex, null, null,
                wx_country, wx_province, wx_city);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<TLogin2Resp>>() {
        }.getType();
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("unionid", unionid)
                .append("uuid", uuid)
                .append("openid", openid)
                .append("nick", nick)
                .append("avatar", avatar)
                .append("sex", sex)
                // qq独有
                .append("is_yellow_vip", qq_is_yellow_vip)
                .append("yellow_vip_level", qq_yellow_vip_level)
                // wx独有
                .append("country", wx_country)
                .append("province", wx_province)
                .append("city", wx_city)
                ;
    }

    @Override
    public String path() {
        return "/mytio/tlogin/cb/p/" + type + ".tio_x";
    }
}
