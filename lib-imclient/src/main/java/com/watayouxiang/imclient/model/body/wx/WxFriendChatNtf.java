package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseResp;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgAudio;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgCall;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgCard;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgFaceEmotion;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgFile;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgHistory;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgImage;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgLocation;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgRed;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgTransAmount;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgType;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgVideo;
import com.watayouxiang.imclient.model.body.wx.msg.WxMessageUtils;
import com.watayouxiang.imclient.packet.TioCommand;

/**
 * 朋友间的聊天通知-- Server-->Client
 * <p>
 * {@link TioCommand#WX_FRIEND_CHAT_NTF}
 */
public class WxFriendChatNtf extends BaseResp {
    /**
     * actflag : 2
     * avatar : /user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg
     * c : 小米10
     * chatlinkid : 8
     * ct : 1
     * mid : 371954
     * msgtype : 1
     * nick : wata
     * readflag : 2
     * t : 1581393996175
     * touid : 23436
     * uid : 23436
     */

    /**
     * 该消息是否是激活聊天消息：1:是；2：否
     */
    public int actflag;
    /**
     * 激活会话时 - 头像
     */
    public String actavatar;
    /**
     * 激活会话时 - 会话名称
     */
    public String actname;
    /**
     * 发送方头像
     */
    public String avatar;
    /**
     * 聊天会话id
     */
    public String chatlinkid;
    /**
     * 消息id，全局唯一，一条消息一个id
     */
    public String mid;
    /**
     * 消息的类型：1：正常消息：2：操作消息
     */
    public int msgtype;
    /**
     * 发送方昵称
     */
    public String nick;
    /**
     * 我发送的消息，对方是否已读
     * <p>
     * 已读标识。1、已读，2、未读
     */
    public int readflag;
    /**
     * 消息阅读时间
     * 2019-12-12 11:12:12
     */
    public String readtime;
    /**
     * 消息发送时间
     * 45454545434
     */
    public Long t;
    /**
     * 接收方的userid
     */
    public int touid;
    /**
     * 发送方的userid，此字段和curruid对比
     */
    public int uid;
    /**
     * 是否是系统发出的消息，参见：Const.Sendbysys.YES，1：是，2或null：不是
     */
    public byte sendbysys;
    public String sysmsgkey;
    public String tonicks;
    public String opernick;
    /**
     * 红包序列号
     */
    public String serialNumber;
    /**
     * 消息类型
     *
     * @see InnerMsgType
     */
    public int ct;
    /**
     * 消息内容
     */
    public String c;
    public InnerMsgFile fc;
    public InnerMsgVideo vc;
    public InnerMsgAudio ac;
    public InnerMsgImage ic;
    public InnerMsgCard cardc;
    public InnerMsgCall call;
    public InnerMsgRed red;
    public InnerMsgTransAmount transAmountVo;
    public InnerMsgLocation location;
    public InnerMsgFaceEmotion faceEmotion;
    public InnerMsgHistory innerMsgHistory;

    public String getShowContent() {
        return WxMessageUtils.getShowContent(ct, c);
    }

}
