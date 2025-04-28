package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseResp;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgAudio;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgCall;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgCard;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgFaceEmotion;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgFile;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgGroupApply;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgImage;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgLocation;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgRed;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgType;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgVideo;
import com.watayouxiang.imclient.model.body.wx.msg.WxMessageUtils;
import com.watayouxiang.imclient.packet.TioCommand;

/**
 * 群聊通知
 * {@link TioCommand#WX_GROUP_CHAT_NTF}
 */
public class WxGroupChatNtf extends BaseResp {
    /**
     * groupid
     */
    public Long g = null;
    /**
     * 在群中的角色
     */
    public int grouprole;
    /**
     * 群人数
     */
    public int joinnum;
    /**
     * 主聊人员的设备类型（DeviceType），1：WS，2：安卓，3：IOS
     */
    public Byte d = 1;
    /**
     * fromUid的简写，聊天发起人信息
     */
    public int f;
    /**
     * 被艾特的用户信息
     */
    public String at = null;
    /**
     * 聊天消息时间
     */
    public Long t = null;
    /**
     * msg id 消息id，全局唯一
     */
    public Long mid = null;
    /**
     * 是否是系统发出的消息，参见：Const.Sendbysys.YES，
     * 1：是，2或null：不是
     */
    public byte sendbysys;
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
     * 群聊激活时传入的群头像
     */
    public String avatar;
    /**
     * 发送者的昵称。
     * <p>
     * 如果设置了 "群昵称"，那么为 "群昵称"。
     */
    public String nick;
    /**
     * 会话id
     */
    public String chatlinkid;
    /**
     * 被操作者 昵称
     */
    public String tonicks;
    /**
     * 操作者 昵称
     */
    public String opernick;

    public String operbizdata;

    /**
     * 系统消息key
     * <p>
     * ex: join
     */
    public String sysmsgkey;

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
    public InnerMsgGroupApply apply;
    public InnerMsgLocation location;
    public InnerMsgFaceEmotion faceEmotion;

    public String getShowContent() {
        return WxMessageUtils.getShowContent(ct, c);
    }
}
