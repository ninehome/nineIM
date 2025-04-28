package com.watayouxiang.db.temp;

import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgType;

import org.greenrobot.greendao.annotation.Id;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/16
 *     desc   :
 *     {@link com.watayouxiang.imclient.model.body.wx.WxGroupMsgResp.DataBean}
 * </pre>
 */
public class GroupMsgTable {
    @Id
    private long mid;
    private String avatar;
    private String bc;
    private int d;
    private int f;
    private String g;
    private String nick;
    private int sendbysys;
    /**
     * 1 独有的消息，2 双方的消息
     */
    private int sigleflag;
    /**
     * 独有消息归属人
     */
    private int sigleuid;
    private String sysmsgkey;
    private String t;
    /**
     * 被操作者nick
     */
    private String tonicks;
    /**
     * 操作者nick
     */
    private String opernick;
    /**
     * 消息过滤标志：1 过滤，2 正常
     */
    private int whereflag;
    /**
     * 需要过滤的用户uid列表：,12312,45353,54535,
     */
    private String whereuid;
    /**
     * 消息类型
     *
     * @see InnerMsgType
     */
    private int ct;
    /**
     * 消息内容
     */
    private String c;
    private InMsgFileTable fc;
    private InMsgVideoTable vc;
    private InMsgAudioTable ac;
    private InMsgImageTable ic;
    private InMsgCardTable cardc;
    private InMsgCallTable call;
}
