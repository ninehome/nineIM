package com.tiocloud.chat.feature.main.fragment;

import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.home.chat.ChatFragment;
import com.tiocloud.chat.feature.main.base.MainTabFragment;
import com.tiocloud.chat.feature.search.curr.SearchActivity;
import com.tiocloud.chat.feature.session.common.adapter.model.TioMsgType;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.group.fragment.msg.HistoryGroupMsg;
import com.tiocloud.chat.feature.session.p2p.fragment.msg.HistoryP2PMsg;
import com.tiocloud.chat.util.ScreenUtil;
import com.tiocloud.chat.widget.titlebar.HomeTitleBar;
import com.watayouxiang.db.dao.CacheTableCrud;
import com.watayouxiang.db.dao.ChatListTableCrud;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.db.dao.GroupMsgTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.db.table.GroupMsgTable;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.ChatListReq;
import com.watayouxiang.httpclient.model.request.GroupInfoReq;
import com.watayouxiang.httpclient.model.response.ChatListResp;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.client.IMState;
import com.watayouxiang.imclient.event.TioStateEvent;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.imclient.model.body.wx.WxChatItemInfoReq;
import com.watayouxiang.imclient.model.body.wx.WxChatItemInfoResp;
import com.watayouxiang.imclient.model.body.wx.WxFriendMsgReq;
import com.watayouxiang.imclient.model.body.wx.WxFriendMsgResp;
import com.watayouxiang.imclient.model.body.wx.WxGroupMsgReq;
import com.watayouxiang.imclient.model.body.wx.WxGroupMsgResp;
import com.watayouxiang.imclient.packet.TioPacket;
import com.watayouxiang.imclient.packet.TioPacketBuilder;
import com.watayouxiang.imclient.utils.IMConst;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author : TaoWang
 * date : 2020-01-10
 * desc : 会话
 * <p>
 * tabData: {@link com.tiocloud.chat.feature.main.model.MainTab#CHAT}
 */
public class MainChatFragment extends MainTabFragment {

    private HomeTitleBar homeTitleBar;
    private ChatFragment fragment;

    @Override
    public boolean isRegisterEvent() {
        return true;
    }

    @Override
    protected void onInit() {
        setStatusBarCustom(findViewById(R.id.fl_statusBar));

        homeTitleBar = findViewById(R.id.homeTitleBar);
        homeTitleBar.setTitle(getString(R.string.talk));

        IMState imState = TioIMClient.getInstance().getState();
        boolean isConnect = imState == IMState.CONNECTED;
        setAppendTitle(isConnect ? getString(R.string.zaixian) : getString(R.string.weiliaojie));
        System.out.println("宽高："+ ScreenUtil.screenWidth+"\t\t\t"+ScreenUtil.screenHeight);
        System.out.println("宽高："+ ScreenUtils.getScreenDensityDpi());
        System.out.println("宽高："+ ScreenUtil.px2dp(ScreenUtil.screenWidth)+"\t\t\t"+ScreenUtil.px2dp(ScreenUtil.screenHeight));
        fragment = new ChatFragment();
        fragment.setContainerId(R.id.chat_fragment_container);
        TioActivity tioActivity = (TioActivity) getActivity();
        if (tioActivity != null) {
            tioActivity.replaceFragment(fragment);
        }

        findViewById(R.id.ll_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(v.getContext());
            }
        });
    }

    // ====================================================================================
    // 通知 fragment 刷新数据
    // ====================================================================================

    @Override
    public void onPageShow(int count, boolean isInit) {
        super.onPageShow(count, isInit);
        setStatusBarLightMode(true);
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();

    }

    @Override
    public void onTabDoubleTap() {
        super.onTabDoubleTap();

    }

    // ====================================================================================
    // 长连接状态
    // ====================================================================================

    Map<String, ChatListResp.List> chatMap = new HashMap<>();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTioStateEvent(TioStateEvent event) {
        switch (event.state) {
            case CONNECTING:
                setAppendTitle(getString(R.string.lianjiezhong));
                break;
            case CONNECT:
                //
                ChatListReq chatListReq = new ChatListReq();
                chatListReq.setCancelTag(this);
                chatListReq.setCacheMode(CacheMode.NO_CACHE);
                chatListReq.get(new TioCallback<ChatListResp>() {
                    @Override
                    public void onTioSuccess(ChatListResp data) {
                        if (data == null || data.size() == 0){
                            setAppendTitle(getString(R.string.zaixian));
                            return;
                        }
                        IMConst.isSyncing = true;
                        count = data.size();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (ChatListResp.List chat : data){
                                    chatMap.put(chat.id, chat);
                                    if (chat.chatmode == 1){
                                        //私聊
                                        TioIMClient.getInstance().sendPacket(TioPacketBuilder.getWxFriendMsgReq(
                                                WxFriendMsgReq.startMid(chat.id, null)
                                        ));
                                    }else {
                                        WxGroupMsgReq wxGroupMsgReq = WxGroupMsgReq.startMid(chat.id, null);
                                        TioIMClient.getInstance().sendPacket(TioPacketBuilder.getWxGroupMsgReq(wxGroupMsgReq));

                                        WxChatItemInfoResp wxChatItemInfoResp = CacheTableCrud.getValue("chatInfo" + chat.id, WxChatItemInfoResp.class);
                                        if (wxChatItemInfoResp == null){
                                            TioPacket wxChatItemInfoReq = TioPacketBuilder.getWxChatItemInfoReq(new WxChatItemInfoReq(chat.id));
                                            TioIMClient.getInstance().sendPacket(wxChatItemInfoReq);
                                        }
                                        GroupInfoResp data2 = CacheTableCrud.getValue("group_info_" + chat.bizid, GroupInfoResp.class);
                                        if (data2 == null){
                                            GroupInfoReq groupInfoReq = new GroupInfoReq("1", chat.bizid);
                                            TioHttpClient.get(this, groupInfoReq, new TaoCallback<BaseResp<GroupInfoResp>>() {
                                                @Override
                                                public void onSuccess(Response<BaseResp<GroupInfoResp>> response) {
                                                    GroupInfoResp data = response.body().getData();
                                                    if (data != null && data.group != null) {
                                                        if (getActivity() == null){
                                                            return;
                                                        }
                                                        CacheTableCrud.insertOrUpdate("group_info_"+chat.bizid, data);
                                                        //保存群主
                                                        CacheTableCrud.setGroupRole(chat.bizid, String.valueOf(data.group.uid), 1);
                                                        //保存我的角色
                                                        if (data.groupuser != null){
                                                            CacheTableCrud.setGroupRole(chat.bizid, String.valueOf(TioDBPreferences.getCurrUid()), data.groupuser.grouprole);
                                                        }
                                                        //保存管理员
                                                        if (data.manges != null){
                                                            for (Integer integer : data.manges){
                                                                CacheTableCrud.setGroupRole(chat.bizid, String.valueOf(integer), 3);
                                                            }
                                                        }

                                                    }
                                                }
                                            });
                                        }
                                    }
                                    try {
                                        Thread.sleep(30);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                int count2 = 0;
                                while (count > 0){
                                    if (count2 > 50){
                                        break;
                                    }
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    count2++;
                                }
                                IMConst.isSyncing = false;
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setAppendTitle(getString(R.string.zaixian));
                                    }
                                });
                            }
                        }).start();
                    }

                    @Override
                    public void onTioError(String msg) {
                        setAppendTitle(getString(R.string.zaixian));
                    }
                });


                /*.get(new TioCallback<ChatListResp>() {
                    @Override
                    public void onTioSuccess(ChatListResp resp) {
                        setAppendTitle("在线");
                    }

                    @Override
                    public void onTioError(String msg) {
                    }

                    @Override
                    public void onFinish() {
                    }
                });*/

                break;
            case ERROR:
            case DISCONNECT:
                setAppendTitle(getString(R.string.weiliaojie));
                break;
        }
    }

    private void setAppendTitle(String append) {
        if (homeTitleBar != null) {
//            homeTitleBar.setAppendTitle(append);
        }
    }

    int count = 0;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxFriendMsgResp(WxFriendMsgResp resp) {
        // 如果不是本次请求的响应则丢弃
        if (!IMConst.isSyncing){
            return;
        }
        count--;
        List<HistoryP2PMsg> tioMsgList = new ArrayList<>(resp.data.size());
        String currUid = String.valueOf(TioDBPreferences.getCurrUid());
        String currNick = CurrUserTableCrud.curr_getNick();

        for (int i = resp.data.size() - 1; i >= 0; i--) {
            WxFriendMsgResp.DataBean bean = resp.data.get(i);

            // 过滤独有消息
            if (bean.sigleflag == 1 && !String.valueOf(bean.sigleuid).equals(currUid)) {
                continue;
            }

            // 需要显示的消息
            tioMsgList.add(new HistoryP2PMsg(bean, currUid, currNick, resp.chatlinkid));
        }
        if (tioMsgList.size() <= 0){
            GroupMsgTableCrud.deleteLast(resp.chatlinkid, -1);
            return;
        }
        if (tioMsgList.size() > 0){
            GroupMsgTableCrud.deleteLast(resp.chatlinkid, Integer.parseInt(tioMsgList.get(0).getId()));
        }

        for (HistoryP2PMsg historyP2PMsg : tioMsgList){
            ChatListResp.List chat = chatMap.get(resp.chatlinkid);
            if (chat == null){
                continue;
            }
            GroupMsgTableCrud.insertOrUpdate(1,String.valueOf(historyP2PMsg.getUid()),
                    resp.chatlinkid, chat.name, chat.avatar,
                    historyP2PMsg.getMsgType() == TioMsgType.tip  ? 1 : 2,
                    historyP2PMsg.getSyskey(), Integer.parseInt(historyP2PMsg.getId()),
                    1, historyP2PMsg.getMsgType().getValue(), historyP2PMsg.getContent(), historyP2PMsg);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxGroupMsgResp(WxGroupMsgResp resp) {
        if (!IMConst.isSyncing){
            return;
        }
        count--;

        List<TioMsg> tioMsgList = new ArrayList<>(resp.data.size());
        String currUid = String.valueOf(TioDBPreferences.getCurrUid());
        String currNick = CurrUserTableCrud.curr_getNick();

        for (int i = resp.data.size() - 1; i >= 0; i--) {
            WxGroupMsgResp.DataBean bean = resp.data.get(i);

            // 过滤独有消息
            if (bean.sigleflag == 1 && !String.valueOf(bean.sigleuid).equals(currUid)) {
                continue;
            }

            // 消息过滤标志
            if (bean.whereflag == 1 && String.valueOf(bean.whereuid).contains(currUid)) {
                continue;
            }

            // 添加需要展示的消息
            tioMsgList.add(new HistoryGroupMsg(bean, currUid, currNick, resp.chatlinkid));
        }
        if (tioMsgList.size() <= 0){
            GroupMsgTableCrud.deleteLast(resp.chatlinkid, -1);
            return;
        }
        if (tioMsgList.size() > 0){
            GroupMsgTableCrud.deleteLast(resp.chatlinkid, Integer.parseInt(tioMsgList.get(0).getId()));
        }
//        Log.e("zlb", "正在拉取聊天缓存："+resp.chatlinkid);
        for (TioMsg tioMsg : tioMsgList){
            HistoryGroupMsg historyGroupMsg = (HistoryGroupMsg)tioMsg;
            ChatListResp.List chat = chatMap.get(resp.chatlinkid);
            if (chat == null){
                continue;
            }
//            Log.e("zlb", "正在保存聊天记录："+tioMsg.getId());
            GroupMsgTableCrud.insertOrUpdate(1,tioMsg.getUid(), resp.chatlinkid,
                    chat.name, chat.avatar,
                    historyGroupMsg.sysmsg(), historyGroupMsg.getSyskey(),
                    Integer.parseInt(tioMsg.getId()), 0,
                    historyGroupMsg.getMsgType().getValue(), historyGroupMsg.getContent(), historyGroupMsg);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxChatItemInfoResp(WxChatItemInfoResp event) {
        //
        CacheTableCrud.insertOrUpdate("chatInfo"+event.chatlinkid, event);
    }
}
