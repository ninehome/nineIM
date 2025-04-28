package com.tiocloud.chat.feature.session.p2p.fragment.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.home.chat.adapter.BaseUIAdapter;
import com.tiocloud.chat.feature.main.MainActivity;
import com.tiocloud.chat.feature.session.common.adapter.model.TioMsgType;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioP2PErrorMsg;
import com.tiocloud.chat.feature.session.group.GroupSessionActivity;
import com.tiocloud.chat.feature.session.group.fragment.msg.GroupMsg;
import com.tiocloud.chat.feature.session.group.fragment.msg.HistoryGroupMsg;
import com.tiocloud.chat.feature.session.p2p.P2PSessionActivity;
import com.tiocloud.chat.feature.session.p2p.fragment.msg.HistoryP2PMsg;
import com.tiocloud.chat.feature.session.p2p.fragment.msg.P2PMsg;
import com.tiocloud.chat.preferences.TioCache;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.db.dao.GroupMsgTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.db.table.GroupMsgTable;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.callback.TioCallbackImpl;
import com.watayouxiang.httpclient.model.request.OnP2PChatInputReq;
import com.watayouxiang.httpclient.model.request.OperReq;
import com.watayouxiang.httpclient.model.request.ReadAckReq;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.event.ClearChatMsg;
import com.watayouxiang.imclient.model.body.wx.WxFriendChatNtf;
import com.watayouxiang.imclient.model.body.wx.WxFriendErrorNtf;
import com.watayouxiang.imclient.model.body.wx.WxFriendMsgResp;
import com.watayouxiang.imclient.model.body.wx.WxHandshakeResp;
import com.watayouxiang.imclient.model.body.wx.WxSessionOperReq;
import com.watayouxiang.imclient.model.body.wx.WxUserOperNtf;
import com.watayouxiang.imclient.packet.TioPacketBuilder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-11
 * desc :
 */
public class P2PFragmentPresenter extends P2PFragmentContract.Presenter {

    private final String currUid = String.valueOf(TioDBPreferences.getCurrUid());
    private final String currNick = CurrUserTableCrud.curr_getNick();
    private String mNextStartMid;


    public P2PFragmentPresenter(P2PFragmentContract.View view) {
        super(new P2PFragmentModel(), view, true);
        // 进入房间
        TioIMClient.getInstance().sendPacket(TioPacketBuilder.getWxSessionOperReq(WxSessionOperReq.in(getView().getChatLinkIds().get(0))));
        TioCache.setInChatLinkId(getView().getChatLinkIds().get(0));
        if (TioConfig.OpenCloseConfig.showInputStatus()){
            EditText editText = getView().getActivity().findViewById(R.id.editTextMessage);
            if (editText == null){
                LogUtils.e("zlb:edittext 不存在");
                return;
            }
            View emoji_button = getView().getActivity().findViewById(R.id.emoji_button);
            View buttonMoreFunctionInText = getView().getActivity().findViewById(R.id.buttonMoreFunctionInText);
            TextView textView = getView().getActivity().findViewById(R.id.audioRecord);
            final long[] inputTime = {0};
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    inputTime[0] = System.currentTimeMillis();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        if (getView().getActivity() == null || getView().getActivity().isFinishing()){
                            return;
                        }
                        String toUid = getToUid();
                        if (toUid == null){
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            continue;
                        }
                        if (toUid.equals(String.valueOf(TioDBPreferences.getCurrUid()))){
                            return;
                        }
                        if (System.currentTimeMillis() - inputTime[0] < 5000||emoji_button.isSelected()||buttonMoreFunctionInText.isSelected()||textView.getText().toString().equals("松开发送")){
                            OnP2PChatInputReq onP2PChatInputReq = new OnP2PChatInputReq(getView().getChatLinkIds().get(0), toUid);
                            onP2PChatInputReq.setCancelTag(P2PFragmentPresenter.this);
                            onP2PChatInputReq.post(new TioCallback<String>() {
                                @Override
                                public void onTioSuccess(String s) {

                                }

                                @Override
                                public void onTioError(String msg) {

                                }
                            });
                        }else {
//                            LogUtils.e("zlb,edittext未在输入状态");
                        }
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        if (TioConfig.OpenCloseConfig.showOnlineStatus()){
            setOnlineListner();
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        // 离开房间
        TioIMClient.getInstance().sendPacket(TioPacketBuilder.getWxSessionOperReq(WxSessionOperReq.out()));
        TioCache.setInChatLinkId(null);
        BaseUIAdapter.setOnlineStatusListener(null, null);
    }

    // ====================================================================================
    // event
    // ====================================================================================

    // 私聊通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxFriendChatNtf(WxFriendChatNtf wxFriendChatNtf) {
        if (!getView().getChatLinkIds().get(0).equals(wxFriendChatNtf.chatlinkid)) return;
        P2PMsg p2PMsg = new P2PMsg(wxFriendChatNtf, currUid, currNick);
        GroupMsgTableCrud.insertOrUpdate(2,String.valueOf(wxFriendChatNtf.uid),
                getView().getChatLinkIds().get(0), getToName(), wxFriendChatNtf.avatar,
                wxFriendChatNtf.sendbysys, wxFriendChatNtf.sysmsgkey,
                Integer.parseInt(wxFriendChatNtf.mid), 1,
                wxFriendChatNtf.msgtype, wxFriendChatNtf.c, p2PMsg);
        getView().getMsgListProxy().sendMsg(p2PMsg);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChatMsgClear(ClearChatMsg clearChatMsg){
        if (clearChatMsg.getChatLinckId().equals(getView().getChatLinkIds().get(0))){
            GroupMsgTableCrud.clear(getView().getChatLinkIds().get(0));
            getView().getMsgListProxy().clearList();
        }
    }

    // 异常通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxFriendErrorNtf(WxFriendErrorNtf ntf) {
        if (!StringUtils.equals(ntf.chatlinkid, getView().getChatLinkIds().get(0))) return;
        if (ntf.getCode() == WxFriendErrorNtf.Code.NO_LINK) {
            String toUid = getToUid();
            if (toUid == null) {
                TioToast.showShort("好友id获取失败");
            } else {
                // 好友删除了你
                // 提示你还不是ta的好友，发送好友验证
                getView().getMsgListProxy().sendMsg(new TioP2PErrorMsg(ntf, toUid));
            }
        } else {
            // 统一返回首页
            MainActivity.start(getView().getActivity());
        }
    }

    private int showTime = 0;

    // 用户操作通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxUserOperNtf(WxUserOperNtf ntf) {
//        LogUtils.e("ntf.chatlinkid==>"+ntf.chatlinkid+"\tgetView().getChatLinkIds().get(0)==>"+getView().getChatLinkIds().get(0));
        if (!StringUtils.equals(ntf.chatlinkid, getView().getChatLinkIds().get(0))) return;
        if (ntf.oper == 9) {
            // 撤回消息
            GroupMsgTableCrud.deleteMsg(getView().getChatLinkIds().get(0), ntf.operbizdata);
            getView().getMsgListProxy().deleteMsg(Long.parseLong(ntf.operbizdata));
        } else if (ntf.oper == 10) {
            // 删除消息
            GroupMsgTableCrud.deleteMsg(getView().getChatLinkIds().get(0), ntf.operbizdata);
            getView().getMsgListProxy().deleteMsg(Long.parseLong(ntf.operbizdata));
        } else if (ntf.oper == 7) {
            // 已读通知
            getView().getMsgListProxy().readAllMsg();
            // 会话已读ack
            new ReadAckReq(getView().getChatLinkIds().get(0)).get(new TioCallbackImpl<>());
        }else if (ntf.oper == 55){
            if (!TioConfig.OpenCloseConfig.showInputStatus()){
                return;
            }
            //正在输入
            Activity activity = getView().getActivity();
            if (activity instanceof P2PSessionActivity){
                ((P2PSessionActivity)activity).getTitleBar().showInputStatus();
                if (showTime > 0){
                    showTime = 5;
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            showTime = 8;
                            while (true){
                                Thread.sleep(1000);
                                showTime = showTime - 1;
                                if (showTime <= 0){
                                    break;
                                }
                            }
                            if (activity.isFinishing()){
                                return;
                            }
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        ((P2PSessionActivity)activity).getTitleBar().hideInputStatus();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
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
        // 初始化列表
        getView().initList();
        // 加载数据
        refresh();
    }

    private void setOnlineListner(){
        if (getToUid() == null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setOnlineListner();
                }
            }, 300);
            return;
        }
        BaseUIAdapter.setOnlineStatusListener(new BaseUIAdapter.OnlineStatusListener() {
            @Override
            public void back(String uid, boolean isOnline, String lastOnlineTime) {
                if (getView().getActivity() == null || getView().getActivity().isFinishing()){
                    return;
                }
                if (!uid.equals(getToUid())){
                    return;
                }
                ((P2PSessionActivity)getView().getActivity()).getTitleBar().setOnlineStatus(true,
                        isOnline, BaseUIAdapter.getOfflineTime(getView().getActivity(),lastOnlineTime));
            }
        }, getToUid());
    }

    @Override
    public void refresh() {
        loadFromcache();
        getMsgList(null);
    }

    @Override
    public void loadMore() {
        if (mNextStartMid != null) {
            getMsgList(mNextStartMid);
        }
    }

    private void getMsgList(String startMid) {
        getModel().getTioP2PMsgList(getView().getChatLinkIds().get(0), startMid, new BaseModel.DataProxy<TioP2PMsgList>() {
            @Override
            public void onSuccess(TioP2PMsgList msgList) {
                WxFriendMsgResp msgResp = msgList.msgResp;
                List<TioMsg> tioMsgList = msgList.tioMsgList;

                for (TioMsg tioMsg : tioMsgList){
                    HistoryP2PMsg historyP2PMsg = (HistoryP2PMsg) tioMsg;
                    GroupMsgTableCrud.insertOrUpdate(1,String.valueOf(tioMsg.getUid()),
                            getView().getChatLinkIds().get(0), getToName(), historyP2PMsg.getAvatar(),
                            historyP2PMsg.getMsgType() == TioMsgType.tip  ? 1 : 2,
                            historyP2PMsg.getSyskey(), Integer.parseInt(tioMsg.getId()),
                            1, historyP2PMsg.getMsgType().getValue(), historyP2PMsg.getContent(), historyP2PMsg);
                }

                // 获取下一页 mid
                List<WxFriendMsgResp.DataBean> data = msgResp.data;
                if (data.size() > 0) {
                    if (mNextStartMid == null){
                        mNextStartMid = data.get(data.size() - 1).mid;
                    }else {
                        mNextStartMid = String.valueOf(Math.min(Integer.parseInt(mNextStartMid), Integer.parseInt(data.get(data.size() - 1).mid)));
                    }
                }

//                // 如果是刷新，则先清除旧数据
//                if (startMid == null) {
//                    getView().getMsgListProxy().clearList();
//                }
//
//                // 设置新数据
//                if (msgResp.lastPage || tioMsgList.size() == 0) {
//                    getView().getMsgListProxy().fetchMoreEnd(tioMsgList);
//                } else {
//                    getView().getMsgListProxy().fetchMoreComplete(tioMsgList);
//                }

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
        });
    }

    // ====================================================================================
    // private
    // ====================================================================================

    private String getToUid() {
        String toUid = null;
        P2PSessionActivity activity = getView().getP2PSessionActivity();
        if (activity != null) {
            toUid = activity.getUid();
        }
        return toUid;
    }

    private String getToName(){
        String toName = null;
        P2PSessionActivity activity = getView().getP2PSessionActivity();
        if (activity != null) {
            toName = activity.toName;
        }
        return toName;
    }
    public int getMsgId(){
        return getView().getP2PSessionActivity().getMsgId();
    }
    private void loadFromcache(){
        if (TioConfig.OpenCloseConfig.enterGroupChatCheck()){
            //如果需要群验证的，则不使用消息缓存
            return;
        }
        Integer msgId = getMsgId();
        List<GroupMsgTable> p2pMsgTableList = GroupMsgTableCrud.queryList(1, getView().getChatLinkIds().get(0), msgId == 0 ? null : msgId);
        List<TioMsg> tioMsgList = new ArrayList<>();
        if (p2pMsgTableList == null || p2pMsgTableList.size() <= 0){
            return;
        }
        int msgIdPosition = 0;
        int index = 0;
        for (GroupMsgTable groupMsgTable : p2pMsgTableList){
            TioMsg tioMsg = null;
            if (groupMsgTable.getSourceType() == 1){
                tioMsg = new Gson().fromJson(groupMsgTable.getMsgEntity(), HistoryP2PMsg.class);
            }else if (groupMsgTable.getSourceType() == 2){
                tioMsg = new Gson().fromJson(groupMsgTable.getMsgEntity(), P2PMsg.class);
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
