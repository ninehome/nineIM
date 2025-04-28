package com.tiocloud.chat.feature.session.p2p.fragment.mvp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.p2p.fragment.msg.HistoryP2PMsg;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.model.body.wx.WxFriendMsgReq;
import com.watayouxiang.imclient.model.body.wx.WxFriendMsgResp;
import com.watayouxiang.imclient.packet.TioPacketBuilder;
import com.watayouxiang.imclient.utils.IMConst;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-11
 * desc :
 */
public class P2PFragmentModel extends P2PFragmentContract.Model {

    public P2PFragmentModel() {
        super(true);
    }

    @Override
    public void detachModel() {
        super.detachModel();
        cancelTask_tcp();
        mChatLinkId = null;
        mP2PMsgListRespProxy = null;
    }

    // ====================================================================================
    // 消息列表
    // ====================================================================================

    private String mChatLinkId;
    private DataProxy<WxFriendMsgResp> mP2PMsgListRespProxy;

    @Override
    public void getP2PMsgList(String chatLinkId, String startMid, DataProxy<WxFriendMsgResp> proxy) {
        if (chatLinkId != null) {
            mChatLinkId = chatLinkId;
            mP2PMsgListRespProxy = proxy;
            TioIMClient.getInstance().sendPacket(TioPacketBuilder.getWxFriendMsgReq(
                    WxFriendMsgReq.startMid(chatLinkId, startMid)
            ));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxFriendMsgResp(WxFriendMsgResp resp) {
        if (IMConst.isSyncing){
            return;
        }
        // 如果不是本次请求的响应则丢弃
        if (!resp.chatlinkid.equals(mChatLinkId)) {
            return;
        }
        if (mP2PMsgListRespProxy != null) {
            mP2PMsgListRespProxy.onSuccess(resp);
        }
    }

    // ====================================================================================
    // 消息转换
    // ====================================================================================

    private AsyncTask<Void, Void, List<TioMsg>> task_tcp;

    @SuppressLint("StaticFieldLeak")
    @Override
    public void convertTioP2PMsgList(final List<WxFriendMsgResp.DataBean> resp, String chatlinkid, final DataProxy<List<TioMsg>> proxy) {
        cancelTask_tcp();
        task_tcp = new AsyncTask<Void, Void, List<TioMsg>>() {
            @Override
            protected List<TioMsg> doInBackground(Void... voids) {

                List<TioMsg> tioMsgList = new ArrayList<>(resp.size());
                String currUid = String.valueOf(TioDBPreferences.getCurrUid());
                String currNick = CurrUserTableCrud.curr_getNick();

                for (int i = resp.size() - 1; i >= 0; i--) {
                    WxFriendMsgResp.DataBean bean = resp.get(i);

                    // 过滤独有消息
                    if (bean.sigleflag == 1 && !String.valueOf(bean.sigleuid).equals(currUid)) {
                        continue;
                    }

                    // 需要显示的消息
                    tioMsgList.add(new HistoryP2PMsg(bean, currUid, currNick, chatlinkid));
                }

                return tioMsgList;
            }

            @Override
            protected void onPostExecute(List<TioMsg> msgs) {
                super.onPostExecute(msgs);
                proxy.onSuccess(msgs);
            }
        };
        task_tcp.execute();
    }

    private void cancelTask_tcp() {
        if (task_tcp != null) {
            task_tcp.cancel(false);
        }
    }

    // ====================================================================================
    // tio群消息列表
    // ====================================================================================

    @Override
    public void getTioP2PMsgList(String chatLinkId, String startMid, DataProxy<TioP2PMsgList> proxy) {
        if (!TioIMClient.getInstance().isHandshake()){
            proxy.onFailure("");
            return;
        }
        // 获取消息
        getP2PMsgList(chatLinkId, startMid, new BaseModel.DataProxy<WxFriendMsgResp>() {
            @Override
            public void onSuccess(WxFriendMsgResp resp) {
                super.onSuccess(resp);
                // 转换消息
                convertTioP2PMsgList(resp.data, resp.chatlinkid, new DataProxy<List<TioMsg>>() {
                    @Override
                    public void onSuccess(List<TioMsg> tioMsgs) {
                        super.onSuccess(tioMsgs);
                        proxy.onSuccess(new TioP2PMsgList(resp, tioMsgs));
                    }
                });
            }
        });
    }
}
