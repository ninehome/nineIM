package com.tiocloud.chat.feature.session.group.fragment.mvp;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.session.common.adapter.model.TioMsgType;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.group.GroupSessionActivity;
import com.tiocloud.chat.feature.session.group.fragment.GroupSessionFragment;
import com.tiocloud.chat.feature.session.group.fragment.msg.GroupMsg;
import com.tiocloud.chat.feature.session.group.fragment.msg.HistoryGroupMsg;
import com.tiocloud.chat.preferences.TioCache;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.db.dao.CacheTableCrud;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.db.dao.GroupMsgTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.db.table.GroupMsgTable;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.model.MsgTemplate;
import com.watayouxiang.imclient.model.body.wx.WxFriendErrorNtf;
import com.watayouxiang.imclient.model.body.wx.WxGroupChatNtf;
import com.watayouxiang.imclient.model.body.wx.WxGroupMsgResp;
import com.watayouxiang.imclient.model.body.wx.WxGroupOperNtf;
import com.watayouxiang.imclient.model.body.wx.WxHandshakeResp;
import com.watayouxiang.imclient.model.body.wx.WxSessionOperReq;
import com.watayouxiang.imclient.model.body.wx.WxUserOperNtf;
import com.watayouxiang.imclient.model.body.wx.internal.ChatItems;
import com.watayouxiang.imclient.packet.TioPacketBuilder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author : TaoWang
 * date : 2020-02-09
 * desc :
 */
public class GroupFragmentPresenter extends GroupFragmentContract.Presenter {

    private final String currUid = String.valueOf(TioDBPreferences.getCurrUid());
    private final String currNick = CurrUserTableCrud.curr_getNick();
    private String mNextStartMid;

    GroupSessionFragment groupSessionFragment;

    public GroupFragmentPresenter(GroupFragmentContract.View view) {
        super(new GroupFragmentModel(), view, true);
        groupSessionFragment = (GroupSessionFragment) view;
        // 进入房间
        TioIMClient.getInstance().sendPacket(TioPacketBuilder.getWxSessionOperReq(WxSessionOperReq.in(getView().getChatLinkIds().get(0))));
        TioCache.setInChatLinkId(getView().getChatLinkIds().get(0));
    }

    @Override
    public void detachView() {
        super.detachView();
        // 离开房间
        TioIMClient.getInstance().sendPacket(TioPacketBuilder.getWxSessionOperReq(WxSessionOperReq.out()));
        TioCache.setInChatLinkId(null);
    }

    // ====================================================================================
    // event
    // ====================================================================================

    // 用户操作通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxUserOperNtf(WxUserOperNtf ntf) {
        if (!getView().getChatLinkIds().get(0).equals(ntf.chatlinkid)) return;
        if (ntf.oper == 8) {// 清空聊天消息通知
            GroupMsgTableCrud.clear(getView().getChatLinkIds().get(0));
            refresh();
        }
    }

    // 群操作通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxGroupOperNtf(WxGroupOperNtf ntf) {
        WxGroupOperNtf.Oper oper = WxGroupOperNtf.Oper.valueOf(ntf.oper);
        if (oper == null) return;
        switch (oper) {
            case BACK_MSG:
            case DEL_MSG:
                getView().getMsgListProxy().deleteMsg(Long.parseLong(ntf.bizdata));//msgId
                break;
            case GROUP_REMOVED:
                GroupMsgTableCrud.deleteUserMsg(ntf.chatlinkid, ntf.bizdata);//uid
                getView().getMsgListProxy().deleteUserMsg(Integer.parseInt(ntf.bizdata));//uid
                break;
            case GROUP_MANAGE_UPDATE:
                if (ntf.uid == TioDBPreferences.getCurrUid()){
                    ChatItems chatItems = ntf.chatItems;
                    groupSessionFragment.myrole = chatItems.getBizrole();
                }
                groupSessionFragment.getGroupInfo();
                break;
        }
    }

    // 群聊消息通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxGroupChatNtf(WxGroupChatNtf wxGroupChatNtf) {
        String myChatlinkId = getView().getChatLinkIds().get(0);
        if (!myChatlinkId.equals(wxGroupChatNtf.chatlinkid)){
            return;
        }

        if (wxGroupChatNtf.sendbysys == 1){
            String syskey = wxGroupChatNtf.sysmsgkey;
            if (syskey == null){
                syskey = "";
            }
            String operbizdata = wxGroupChatNtf.operbizdata;
            if (syskey.equals("setManager")){
                if (!TextUtils.isEmpty(operbizdata)){
                    CacheTableCrud.setGroupRole(String.valueOf(wxGroupChatNtf.g), operbizdata, 3);
                }
                getView().getMsgListProxy().readAllMsg();
            }else if (syskey.equals("cancleManager")){
                if (!TextUtils.isEmpty(operbizdata)){
                    CacheTableCrud.setGroupRole(String.valueOf(wxGroupChatNtf.g), operbizdata, 2);
                }
                getView().getMsgListProxy().readAllMsg();
            }else if (syskey.equals("friendflag")){
                groupSessionFragment.canFriend = "1".equals(operbizdata);
            }else if (syskey.equals("exitFlag")){

            }



            if (!TioConfig.OpenCloseConfig.showInviteKickTips()){
                int groupRoleMy = CacheTableCrud.getGroupRoleMy(String.valueOf(-Long.parseLong(wxGroupChatNtf.chatlinkid)));
                if (groupRoleMy == 2){
                    return;
                }
            }
        }


//        if (groupSessionFragment.getMyRole() == 2 && !TioConfig.OpenCloseConfig.isShowGroupTipMsg()){
//            if (1 == wxGroupChatNtf.sendbysys && !MsgTemplate.updatenotice.getKey().equals(wxGroupChatNtf.sysmsgkey) && !"showTime".equals(wxGroupChatNtf.sysmsgkey)){
//                return;
//            }
//        }




//        if (!TioConfig.OpenCloseConfig.showInviteKickTips()){
//            if (1 == wxGroupChatNtf.sendbysys && (groupSessionFragment.getMyRole() != 1 && groupSessionFragment.getMyRole() != 3) ){
//                if ("join".equals(wxGroupChatNtf.sysmsgkey)){
//                    String operBizData = wxGroupChatNtf.operbizdata;
//                    if (operBizData == null){
//                        return;
//                    }
//                    String[] splits = operBizData.split(",");
//                    boolean isHasMy = false;
//                    for (String split : splits){
//                        if (split.equals(TioDBPreferences.getCurrUid())){
//                            isHasMy = true;
//                            break;
//                        }
//                    }
//                    if (!isHasMy){
//                        return;
//                    }
//                }
//                if ("operkick".equals(wxGroupChatNtf.sysmsgkey)){
//                    return;
//                }
//                if (wxGroupChatNtf.sysmsgkey.contains("forbidden")){
//                    return;
//                }
//                if (wxGroupChatNtf.sysmsgkey.contains("leave")){
//                    return;
//                }
//            }
//        }
        if (!TioConfig.OpenCloseConfig.showBackMsg()){
            if (1 == wxGroupChatNtf.sendbysys){
                if (wxGroupChatNtf.sysmsgkey != null && wxGroupChatNtf.sysmsgkey.contains("managermsgback")){
                    if (groupSessionFragment.myrole == 2){
                        return;
                    }
                }
            }
        }
//        ToastUtils.showShort("收到新消息");
        GroupMsg groupMsg = new GroupMsg(wxGroupChatNtf, currUid, currNick);
        GroupMsgTableCrud.insertOrUpdate(2,groupMsg.getUid(), getView().getChatLinkIds().get(0), groupSessionFragment.groupName, groupMsg.getAvatar(), wxGroupChatNtf.sendbysys, wxGroupChatNtf.sysmsgkey, wxGroupChatNtf.mid.intValue(), 0, wxGroupChatNtf.ct, wxGroupChatNtf.c, groupMsg);
        getView().getMsgListProxy().sendMsg(groupMsg);
        if (MsgTemplate.updatenotice.getKey().equals(wxGroupChatNtf.sysmsgkey)){
            getView().toRefreshNotice(true, wxGroupChatNtf.tonicks);
        }
    }

    // 异常通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxFriendErrorNtf(WxFriendErrorNtf ntf) {
        if (!StringUtils.equals(ntf.chatlinkid, getView().getChatLinkIds().get(0))) return;
        TioToast.showShort(ntf.msg);
    }

    // 握手响应
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxHandshakeResp(WxHandshakeResp wxHandshakeResp) {
        refresh();
    }

    // ====================================================================================
    // ui
    // ====================================================================================

    @Override
    public void init() {
        // 初始化ui
        getView().resetUI();
        // 加载数据
        refresh();
    }

    @Override
    public void refresh() {
        loadFromCache();
        getMsgList(null);
    }

    @Override
    public void loadMore() {
        if (mNextStartMid != null) {
            getMsgList(mNextStartMid);
        }
    }


    private void getMsgList(String startMid) {
        getModel().getTioGroupMsgList(getView().getChatLinkIds().get(0), startMid, new BaseModel.DataProxy<TioGroupMsgList>() {
            @Override
            public void onSuccess(TioGroupMsgList msgList) {
                WxGroupMsgResp msgResp = msgList.msgResp;
                List<TioMsg> tioMsgListTmp = msgList.tioMsgList;
                List<TioMsg> tioMsgList = new ArrayList<>();
                for (TioMsg tioMsg : tioMsgListTmp){
                    HistoryGroupMsg historyGroupMsg = (HistoryGroupMsg) tioMsg;
//                    if (groupRoleMy == 2 && !TioConfig.OpenCloseConfig.isShowGroupTipMsg()){
//                        if (tioMsg.getMsgType() == TioMsgType.tip){
//                            String sysKey1 = "showTime";
//                            String sysKey2 = historyGroupMsg.getSyskey();
//                            if (!sysKey1.equals(sysKey2)){
//                                continue;
//                            }
//                        }
//                    }
                    if (historyGroupMsg.sysmsg() == 1 && !TioConfig.OpenCloseConfig.showInviteKickTips()){
                        int groupRoleMy = CacheTableCrud.getGroupRoleMy(String.valueOf(-Long.parseLong(historyGroupMsg.getChatLinkId())));
                        if (groupRoleMy == 2){
                            continue;
                        }

//                        if ((groupSessionFragment.getMyRole() != 1 && groupSessionFragment.getMyRole() != 3) && historyGroupMsg.getSyskey() != null){
//                            if (historyGroupMsg.getSyskey().contains("join")){
//                                String operBizData = historyGroupMsg.getOperBizData();
//                                if (operBizData == null){
//                                    continue;
//                                }
//                                String[] splits = operBizData.split(",");
//                                boolean isHasMy = false;
//                                for (String split : splits){
//                                    if (split.equals(TioDBPreferences.getCurrUid())){
//                                        isHasMy = true;
//                                        break;
//                                    }
//                                }
//                                if (!isHasMy){
//                                    continue;
//                                }
//                            }
//                            if (historyGroupMsg.getSyskey().contains("operkick")){
//                                continue;
//                            }
//                            if (historyGroupMsg.getSyskey().contains("forbidden")){
//                                continue;
//                            }
//                            if (historyGroupMsg.getSyskey().contains("leave")){
//                                continue;
//                            }
//                        }
                    }
                    if (!TioConfig.OpenCloseConfig.showBackMsg()){
                        if (tioMsg.getMsgType() == TioMsgType.tip){
                            if (historyGroupMsg.getSyskey() != null && historyGroupMsg.getSyskey().contains("managermsgback")){
                                if (groupSessionFragment.myrole == 2){
                                    continue;
                                }
                            }
                        }
                    }
                    GroupMsgTableCrud.insertOrUpdate(1,historyGroupMsg.getUid(), getView().getChatLinkIds().get(0),
                            groupSessionFragment.groupName, historyGroupMsg.getAvatar(),
                            historyGroupMsg.sysmsg(), historyGroupMsg.getSyskey(),
                            Integer.parseInt(tioMsg.getId()), 0,
                            historyGroupMsg.getMsgType().getValue(), historyGroupMsg.getContent(), historyGroupMsg);
                    tioMsgList.add(tioMsg);
                }
                // 获取下一页 mid
                List<WxGroupMsgResp.DataBean> data = msgResp.data;
                if (data.size() > 0) {
                    if (mNextStartMid == null){
                        mNextStartMid = data.get(data.size() - 1).mid;
                    }else {
                        mNextStartMid = String.valueOf(Math.min(Integer.parseInt(mNextStartMid), Integer.parseInt(data.get(data.size() - 1).mid)));
                    }
                }
                // 如果是刷新，则先清除旧数据
                 if (startMid == null) {
                    getView().getMsgListProxy().setNewData(tioMsgList);
                }else {
                    // 设置新数据
                    if (msgResp.lastPage || tioMsgList.size() == 0) {
                        getView().getMsgListProxy().fetchMoreEnd(tioMsgList);
                    } else {
                        getView().getMsgListProxy().fetchMoreComplete(tioMsgList);
                    }
                }
                // 如果是第一页，则需要定位到底部
                if (startMid == null) {
//                    getView().getMsgListProxy().scrollToBottom();
                    if (tioMsgList.size() == 0){
                        GroupMsgTableCrud.clear(getView().getChatLinkIds().get(0));
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);

            }
        });
    }




    public int getMsgId(){
        if (groupSessionFragment.getSessionActivity() == null){
            return -1;
        }
        return ((GroupSessionActivity) groupSessionFragment.getSessionActivity()).getMsgId();
    }

    boolean loadFromCache = false;

    private void loadFromCache(){
        if (TioConfig.OpenCloseConfig.enterGroupChatCheck()){
            //如果需要群验证的，则不使用消息缓存
            Log.e("zlb", "不使用缓存");
            return;
        }
        Integer msgId = getMsgId();
        if (msgId < 0){
            return;
        }

        loadFromCache = true;
        List<GroupMsgTable> groupMsgTableList = GroupMsgTableCrud.queryList(0, getView().getChatLinkIds().get(0), msgId < 1 ? null : msgId);
        List<TioMsg> tioMsgList = new ArrayList<>();
        if (groupMsgTableList == null || groupMsgTableList.size() <= 0){
            return;
        }
        int msgIdPosition = 0;
        int index = 0;
        for (GroupMsgTable groupMsgTable : groupMsgTableList){
            TioMsg tioMsg = null;
            if (groupMsgTable.getSourceType() == 1){
                tioMsg = new Gson().fromJson(groupMsgTable.getMsgEntity(), HistoryGroupMsg.class);
            }else if (groupMsgTable.getSourceType() == 2){
                tioMsg = new Gson().fromJson(groupMsgTable.getMsgEntity(), GroupMsg.class);
            }

//            if (groupSessionFragment.getMyRole() == 2 && !TioConfig.OpenCloseConfig.isShowGroupTipMsg()){
//                if (tioMsg.getMsgType() == TioMsgType.tip){
//                    String sysKey1 = "showTime";
//                    String sysKey2 = groupMsgTable.getSyskey();
//                    if (!sysKey1.equals(sysKey2)){
//                        continue;
//                    }
//                }
//            }
            if (groupMsgTable.getSysMsg() == 1 && !TioConfig.OpenCloseConfig.showInviteKickTips()){
                int groupRoleMy = CacheTableCrud.getGroupRoleMy(String.valueOf(-Long.parseLong(tioMsg.getChatLinkId())));
                if (groupRoleMy == 2){
                    continue;
                }
//                if ((groupSessionFragment.getMyRole() != 1 && groupSessionFragment.getMyRole() != 3) && groupMsgTable.getSyskey() != null){
//                    if (groupMsgTable.getSyskey().contains("join")){
//                        String operBizData = null;
//                        if (groupMsgTable.getSourceType() == 1){
//                            operBizData = ((HistoryGroupMsg)tioMsg).getOperBizData();
//                        }else {
//                            operBizData = ((GroupMsg)tioMsg).getOperBizData();
//                        }
//                        if (operBizData == null){
//                            continue;
//                        }
//                        String[] splits = operBizData.split(",");
//                        boolean isHasMy = false;
//                        for (String split : splits){
//                            if (split.equals(TioDBPreferences.getCurrUid())){
//                                isHasMy = true;
//                                break;
//                            }
//                        }
//                        if (!isHasMy){
//                            continue;
//                        }
//                    }
//                    if (groupMsgTable.getSyskey().contains("operkick")){
//                        continue;
//                    }
//                    if (groupMsgTable.getSyskey().contains("forbidden")){
//                        continue;
//                    }
//                    if (groupMsgTable.getSyskey().contains("leave")){
//                        continue;
//                    }
//                }
            }
            if (!TioConfig.OpenCloseConfig.showBackMsg()){
                if (tioMsg.getMsgType() == TioMsgType.tip){
                    if (groupMsgTable.getSyskey() != null && groupMsgTable.getSyskey().contains("managermsgback")){
                        if (groupSessionFragment.myrole == 2){
                            continue;
                        }
                    }
                }
            }
            if (msgId > 0){
                if (tioMsg.getId().equals(msgId.toString())){
                    msgIdPosition = index;
                }
            }
            index++;
            tioMsgList.add(tioMsg);
        }
        // 如果是刷新，则先清除旧数据
        getView().getMsgListProxy().clearList();
        getView().getMsgListProxy().fetchMoreComplete(tioMsgList);
        if (tioMsgList.size() > 0) {
            mNextStartMid = tioMsgList.get(0).getId();
        }
        // 如果是第一页，则需要定位到底部
        if (msgId > 0){
            getView().getMsgListProxy().scrollToPosition(msgIdPosition);
        }else {
            getView().getMsgListProxy().scrollToBottom();
        }

    }
}
