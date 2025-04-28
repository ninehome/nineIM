package com.tiocloud.chat.feature.session.group.fragment.msg;

import com.tiocloud.chat.feature.session.common.adapter.model.TioMsgType;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.util.StringUtil;
import com.tiocloud.chat.util.TimeUtil;
import com.watayouxiang.androidutils.util.HtmlUtils;
import com.watayouxiang.imclient.model.MsgTemplate;
import com.watayouxiang.imclient.model.body.wx.WxGroupMsgResp;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgType;

/**
 * author : TaoWang
 * date : 2020-02-18
 * desc :
 */
public class HistoryGroupMsg extends TioMsg {

    private final WxGroupMsgResp.DataBean item;
    private final String currUid;
    private final String currNick;
    private final String chatlinkid;

    public HistoryGroupMsg(WxGroupMsgResp.DataBean list, String currUid, String currNick, String chatlinkid) {
        this.item = list;
        this.currUid = currUid;
        this.currNick = currNick;
        this.chatlinkid = chatlinkid;
    }

    public int sysmsg(){
        return this.item.sendbysys;
    }

    public String getSyskey(){
        return item.sysmsgkey;
    }

    public String getOperBizData(){
        return item.operbizdata;
    }

    @Override
    public String getId() {
        return String.valueOf(item.mid);
    }

    @Override
    public TioMsgType getMsgType() {
        if (item.sendbysys == 1) {
            return TioMsgType.tip;
        }
        InnerMsgType contentType = InnerMsgType.valueOf(item.ct);
        if (contentType == null) return TioMsgType.unknown;
        switch (contentType) {
            case VIDEO:
                return TioMsgType.video;
            case IMAGE:
                return TioMsgType.image;
            case AUDIO:
                return TioMsgType.audio;
            case TEXT:
                return TioMsgType.text;
            case FILE:
                return TioMsgType.file;
            case CARD:
                return TioMsgType.card;
            case BLOG:
                return TioMsgType.blog;
            case CALL_AUDIO:
            case CALL_VIDEO:
                return TioMsgType.call;
            case RED_PAPER:
                return TioMsgType.redPaper;
            case GROUP_APPLY:
                return TioMsgType.groupApply;
            case LOCATION:
                return TioMsgType.location;
            case FACE_EMOTION:
                return TioMsgType.faceEmotion;
            case TRANS_Message:
                return TioMsgType.transMessage;
            default:
                return TioMsgType.unknown;
        }
    }

    @Override
    public boolean isSendMsg() {
        return StringUtil.equals(String.valueOf(item.f), currUid);
    }

    @Override
    public String getAvatar() {
        if (getMsgType() == TioMsgType.tip) {
            return null;
        }
        return StringUtil.nonNull(item.avatar);
    }

    @Override
    public String getName() {
        return String.valueOf(item.nick);
    }

    @Override
    public boolean isShowName() {
        if (getMsgType() == TioMsgType.tip) {
            return false;
        } else if (isSendMsg()) {
            return false;
        }
        return true;
    }

    @Override
    public String getAitName() {
        return String.valueOf(item.nick);
    }

    @Override
    public Long getTime() {
        return TimeUtil.dateString2Long(item.t);
    }

    @Override
    public String getContent() {
        if (getMsgType() == TioMsgType.tip) {
            String tipMsg = MsgTemplate.getTipMsg(item.sysmsgkey, item.opernick, item.tonicks, currNick);
            if (tipMsg != null) {
                return tipMsg;
            }
        }
        return HtmlUtils.unescapeHtml(item.c);
    }

    @Override
    public Object getContentObj() {
        InnerMsgType contentType = InnerMsgType.valueOf(item.ct);
        if (contentType == null) return null;
        switch (contentType) {
            case CALL_VIDEO:
            case CALL_AUDIO:
                return item.call;
            case CARD:
                return item.cardc;
            case FILE:
                return item.fc;
            case TEXT:
                return item.c;
            case IMAGE:
                return item.ic;
            case VIDEO:
                return item.vc;
            case BLOG:
                return null;
            case AUDIO:
                return item.ac;
            case RED_PAPER:
                return item.red;
            case GROUP_APPLY:
                return item.apply;
            case LOCATION:
                return item.location;
            case FACE_EMOTION:
                return item.faceEmotion;
            case TRANS_Message:
                return item.c;
        }
        return null;
    }

    @Override
    public String getUid() {
        return String.valueOf(item.f);
    }

    @Override
    public String getChatLinkId() {
        return chatlinkid;
    }
}
