package com.watayouxiang.imclient.model.body.wx.msg;

/**
 * author : TaoWang
 * date : 2020/4/2
 * desc :
 */
public class InnerMsgCard {
    /**
     * 名片
     * bizavatar : /user/base/avatar/20200115/7/1433081217333877887082496.png
     * bizid : 22626
     * bizname : 王涛2
     * cardtype : 1
     * shareFromUid : 23436
     * shareToBizid : 240
     */
    public String bizavatar;
    /**
     * 群id / 用户id
     */
    public String bizid;
    public String bizname;
    /**
     * 1 个人名片，2 群名片
     */
    public int cardtype;
    /**
     * 分享该名片的人的 uid
     */
    public int shareFromUid;
    public String shareToBizid;
}
