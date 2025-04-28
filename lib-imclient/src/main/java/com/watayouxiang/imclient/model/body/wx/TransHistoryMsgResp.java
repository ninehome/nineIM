package com.watayouxiang.imclient.model.body.wx;

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

import java.util.List;

/**
 * author : TaoWang
 * date : 2020/3/12
 * desc :
 */
public class TransHistoryMsgResp {

    /**
     * chatlinkid : 1258
     * data : [{"ac":"","avatar":"/user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg","bc":"","c":"[{\"comefrom\":5,\"coverheight\":400,\"coversize\":18336,\"coverurl\":\"/wx/upload/img/26/9014/1119567/88097620/74541310988/15/222529/1237746472418680832_sm.jpg\",\"coverwidth\":400,\"filename\":\"20200311222528.jpg\",\"height\":3024,\"id\":3622,\"session\":\"12137467724452865136510745600\",\"size\":\"583624\",\"status\":1,\"title\":\"20200311222528.jpg\",\"uid\":23440,\"url\":\"/wx/upload/img/26/9014/1119567/88097620/74541310988/15/222529/1237746472418680832.jpg\",\"width\":3024}]","cardc":"","ct":6,"fc":"","ic":"[{\"comefrom\":5,\"coverheight\":400,\"coversize\":18336,\"coverurl\":\"/wx/upload/img/26/9014/1119567/88097620/74541310988/15/222529/1237746472418680832_sm.jpg\",\"coverwidth\":400,\"filename\":\"20200311222528.jpg\",\"height\":3024,\"id\":3622,\"session\":\"12137467724452865136510745600\",\"size\":\"583624\",\"status\":1,\"title\":\"20200311222528.jpg\",\"uid\":23440,\"url\":\"/wx/upload/img/26/9014/1119567/88097620/74541310988/15/222529/1237746472418680832.jpg\",\"width\":3024}]","mid":"380662","msgtype":1,"nick":"叶孤城","readflag":1,"readtime":"2020-03-12 11:09:29","sendbysys":2,"sigleflag":2,"sigleuid":-1,"t":"2020-03-11 22:25:30","touid":23436,"uid":23440,"vc":""}]
     */

    public String msgIds;
    public String fromBizId;
    public String fromChatMode;
    public String title;
    public List<InnerMsgHistory> content;

    public String getMsgIds() {
        return msgIds;
    }

    public void setMsgIds(String msgIds) {
        this.msgIds = msgIds;
    }

    public String getFromBizId() {
        return fromBizId;
    }

    public void setFromBizId(String fromBizId) {
        this.fromBizId = fromBizId;
    }

    public String getFromChatMode() {
        return fromChatMode;
    }

    public void setFromChatMode(String fromChatMode) {
        this.fromChatMode = fromChatMode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<InnerMsgHistory> getContent() {
        return content;
    }

    public void setContent(List<InnerMsgHistory> content) {
        this.content = content;
    }
}
