package com.watayouxiang.db.temp;

import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgCard;

import org.greenrobot.greendao.annotation.Id;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/16
 *     desc   :
 * </pre>
 * {@link InnerMsgCard}
 */
public class InMsgCardTable {
    @Id
    private long id;
    private String bizavatar;
    /**
     * 群id / 用户id
     */
    private String bizid;
    private String bizname;
    /**
     * 1 个人名片，2 群名片
     */
    private int cardtype;
    /**
     * 分享该名片的人的 uid
     */
    private int shareFromUid;
    private String shareToBizid;
}
