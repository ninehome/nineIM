package com.tiocloud.chat.feature.session.group.fragment.mvp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.group.fragment.msg.HistoryGroupMsg;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.model.body.wx.WxGroupMsgReq;
import com.watayouxiang.imclient.model.body.wx.WxGroupMsgResp;
import com.watayouxiang.imclient.packet.TioPacketBuilder;
import com.watayouxiang.imclient.utils.IMConst;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-18
 * desc :
 */
public class GroupFragmentModel extends GroupFragmentContract.Model {

    public GroupFragmentModel() {
        super(true);
    }

    @Override
    public void detachModel() {
        super.detachModel();
        cancelTask();
        mChatLinkId = null;
        mGroupMsgListRespProxy = null;
    }

    // ====================================================================================
    // 群消息列表
    // ====================================================================================

    private String mChatLinkId;
    private DataProxy<WxGroupMsgResp> mGroupMsgListRespProxy;

    @Override
    public void getGroupMsgList(String chatLinkId, String startMid, DataProxy<WxGroupMsgResp> proxy) {
        if (chatLinkId != null) {
            mChatLinkId = chatLinkId;
            mGroupMsgListRespProxy = proxy;
            WxGroupMsgReq wxGroupMsgReq = WxGroupMsgReq.startMid(chatLinkId, startMid);
            TioIMClient.getInstance().sendPacket(TioPacketBuilder.getWxGroupMsgReq(wxGroupMsgReq));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxGroupMsgResp(WxGroupMsgResp resp) {
        if (IMConst.isSyncing){
            return;
        }
        // 如果不是本次请求的响应则丢弃
        if (!resp.chatlinkid.equals(mChatLinkId)) {
            return;
        }
        if (mGroupMsgListRespProxy != null) {
            mGroupMsgListRespProxy.onSuccess(resp);
        }
    }

    // ====================================================================================
    // 消息转换
    // ====================================================================================

    private AsyncTask<Void, Void, List<TioMsg>> task_tcp;

    @SuppressLint("StaticFieldLeak")
    @Override
    public void convertTioGroupMsgList(final List<WxGroupMsgResp.DataBean> resp, String chatlinkid, final DataProxy<List<TioMsg>> proxy) {
        cancelTask();
        task_tcp = new AsyncTask<Void, Void, List<TioMsg>>() {
            @Override
            protected List<TioMsg> doInBackground(Void... params) {

                List<TioMsg> tioMsgList = new ArrayList<>(resp.size());
                String currUid = String.valueOf(TioDBPreferences.getCurrUid());
                String currNick = CurrUserTableCrud.curr_getNick();

                for (int i = resp.size() - 1; i >= 0; i--) {
                    WxGroupMsgResp.DataBean bean = resp.get(i);

                    // 过滤独有消息
                    if (bean.sigleflag == 1 && !String.valueOf(bean.sigleuid).equals(currUid)) {
                        continue;
                    }

                    // 消息过滤标志
                    if (bean.whereflag == 1 && String.valueOf(bean.whereuid).contains(currUid)) {
                        continue;
                    }

                    // 添加需要展示的消息
                    tioMsgList.add(new HistoryGroupMsg(bean, currUid, currNick, chatlinkid));
                }

                return tioMsgList;
            }

            @Override
            protected void onPostExecute(List<TioMsg> tioMsgs) {
                super.onPostExecute(tioMsgs);
                proxy.onSuccess(tioMsgs);
            }
        };
        task_tcp.execute();
    }

    private void cancelTask() {
        if (task_tcp != null) {
            task_tcp.cancel(false);
        }
    }

    // ====================================================================================
    // tio群消息列表
    // ====================================================================================

    @Override
    public void getTioGroupMsgList(String chatLinkId, String startMid, DataProxy<TioGroupMsgList> proxy) {
        if (!TioIMClient.getInstance().isHandshake()){
            proxy.onFailure("");
            return;
        }
        // 获取消息
        getGroupMsgList(chatLinkId, startMid, new BaseModel.DataProxy<WxGroupMsgResp>() {
            @Override
            public void onSuccess(WxGroupMsgResp resp) {
                super.onSuccess(resp);
                // 转换消息
                convertTioGroupMsgList(resp.data, resp.chatlinkid, new DataProxy<List<TioMsg>>() {
                    @Override
                    public void onSuccess(List<TioMsg> tioMsgs) {
                        super.onSuccess(tioMsgs);
                        proxy.onSuccess(new TioGroupMsgList(resp, tioMsgs));
                    }
                });
            }
        });
    }

}
