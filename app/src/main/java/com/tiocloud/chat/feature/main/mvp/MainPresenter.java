package com.tiocloud.chat.feature.main.mvp;

import android.graphics.Color;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.account.feature.login.LoginActivity;
import com.tiocloud.chat.MyActivityManager;
import com.tiocloud.chat.TioApplication;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.session.group.fragment.msg.GroupMsg;
import com.tiocloud.chat.feature.session.p2p.fragment.msg.P2PMsg;
import com.tiocloud.chat.mvp.socket.SocketPresenter;
import com.tiocloud.chat.preferences.TioCache;
import com.tiocloud.chat.util.AppUpdateTool;
import com.tiocloud.chat.util.ScreenUtil;
import com.tiocloud.chat.util.StringUtil;
import com.tiocloud.chat.util.TioBellVibrate;
import com.tiocloud.jpush.PushLauncher;
import com.watayouxiang.androidutils.util.SpanUtils;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.confirm.TioAllNoticeDialog;
import com.watayouxiang.androidutils.widget.dialog.confirm.TioConfirmDialog;
import com.watayouxiang.db.dao.CacheTableCrud;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.db.dao.GroupMsgTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.db.table.CacheTable;
import com.watayouxiang.db.table.GroupMsgTable;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.QueryAllNoticeReq;
import com.watayouxiang.httpclient.model.request.RegisterReq;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.model.body.MsgTip;
import com.watayouxiang.imclient.model.body.wx.WxFriendChatNtf;
import com.watayouxiang.imclient.model.body.wx.WxGroupChatNtf;
import com.watayouxiang.imclient.model.body.wx.WxGroupOperNtf;
import com.watayouxiang.imclient.model.body.wx.WxUserOperNtf;
import com.watayouxiang.imclient.model.body.wx.internal.ChatItems;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020-02-12
 * desc :
 */
public class MainPresenter extends MainContract.Presenter {
    private final SocketPresenter socketPresenter;
    private AppUpdateTool appUpdateTool;

    public MainPresenter(MainContract.View view) {
        super(new MainModel(), view, true);
        socketPresenter = new SocketPresenter(() -> getView().getActivity());
    }

    @Override
    public void detachView() {
        super.detachView();
        socketPresenter.detachView();
        if (appUpdateTool != null) {
            appUpdateTool.release();
        }
    }

    @Override
    public void init() {
        BarUtils.transparentStatusBar(getView().getActivity());
        getView().initViews();
        connectSocket();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ruquestAllNotice();
            }
        }, 1000);
    }

    private void ruquestAllNotice() {
        QueryAllNoticeReq queryAllNoticeReq = new QueryAllNoticeReq();
        queryAllNoticeReq.setCancelTag(this);
        queryAllNoticeReq.post(new TioCallback<Object>() {
            @Override
            public void onTioSuccess(Object o) {
                if (o == null){
                    return;
                }
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(o));
                String content = jsonObject.getString("content");
                if (content == null){
                    return;
                }
                showNoticeDialog(content);
            }

            @Override
            public void onTioError(String msg) {
            }
        });
    }

    @Override
    public void checkAppUpdate() {
        ThreadUtils.getMainHandler().postDelayed(() -> {
            if (appUpdateTool == null) {
                appUpdateTool = new AppUpdateTool(getView().getMainActivity());
            }
            appUpdateTool.checkUpdate();
        }, 300);
    }

    @Override
    public void clearAllNotifications() {
        PushLauncher.getInstance().clearAllNotifications();
    }

    private void connectSocket() {
        socketPresenter.connectSocket(resp -> {
            switch (resp) {
                case UnLogin:
                case TokenNull:
                    LoginActivity.start(getView().getActivity());
//                    OneKeyLoginActivity.start(getView().getActivity());
                    socketPresenter.finishAllActivity();
                    break;
                case ImServerNull:
                    socketPresenter.exitApp();
                    break;
                case AlreadyConnect:
                case ConnectSuccess:
                    break;
            }
        });
    }

    // ====================================================================================
    // 发言过快提示
    // ====================================================================================

    // 消息提示
    // 发言过快提示
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgTip(MsgTip ntf) {
        String msg = ntf.msg;
        if (msg == null) return;
        TioToast.showShort(msg);
    }

    // ====================================================================================
    // 群聊私聊消息，响铃实现
    // ====================================================================================

    // 私聊通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxFriendChatNtf(WxFriendChatNtf ntf) {

        GroupMsgTable one = GroupMsgTableCrud.getOne(1, ntf.chatlinkid);
        if (one != null){
            P2PMsg p2PMsg = new P2PMsg(ntf, String.valueOf(TioDBPreferences.getCurrUid()), CurrUserTableCrud.curr_getNick());
            GroupMsgTableCrud.insertOrUpdate(2,String.valueOf(ntf.uid),
                    ntf.chatlinkid, one.getChatName(), ntf.avatar,
                    ntf.sendbysys, ntf.sysmsgkey,
                    Integer.parseInt(ntf.mid), 1,
                    ntf.msgtype, ntf.c, p2PMsg);
        }
        // 未读消息 && 不是自己发的消息 && 不在会话中
        if (ntf.readflag == 2 && ntf.uid != TioDBPreferences.getCurrUid() && !ntf.chatlinkid.equals(TioCache.getInChatLinkId())) {
            boolean noDisturb = TioConfig.isNoDisturb(1, true, String.valueOf(ntf.uid));
            if (noDisturb){
                return;
            }
//            if (!CurrUserTableCrud.curr_isOpenMsgRemind(true)){
//                return;
//            }
            // 长连接已建立 && 打开通知，则响铃
            if (TioIMClient.getInstance().isHandshake() && CurrUserTableCrud.curr_isOpenMsgRemind(true)) {
                TioBellVibrate.getInstance().start(TioBellVibrate.Bell.MSG_NTF_P2P);
            }
            // 在后台，则给出通知
//            if (!AppUtils.isAppForeground()) {
//                TioNotification.getInstance().notification(getView().getActivity(), ntf.nick, ntf.getShowContent());
//            }
        }
    }

    // 群聊通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxGroupChatNtf(WxGroupChatNtf ntf) {
        GroupMsgTable one = GroupMsgTableCrud.getOne(0, ntf.chatlinkid);
        if (one != null){
            GroupMsg groupMsg = new GroupMsg(ntf, String.valueOf(TioDBPreferences.getCurrUid()), CurrUserTableCrud.curr_getNick());
            GroupMsgTableCrud.insertOrUpdate(2,groupMsg.getUid(), ntf.chatlinkid, one.getChatName(), groupMsg.getAvatar(), ntf.sendbysys, ntf.sysmsgkey, ntf.mid.intValue(), 0, ntf.ct, ntf.c, groupMsg);
        }


        if (ntf.sendbysys == 1 && ntf.sysmsgkey != null){
            if (ntf.sysmsgkey.equals("setManager")){
                String operbizdata = ntf.operbizdata;
                if (!TextUtils.isEmpty(operbizdata)){
                    CacheTableCrud.setGroupRole(String.valueOf(ntf.g), operbizdata, 3);
                }
            }else if (ntf.sysmsgkey.equals("cancleManager")){
                String operbizdata = ntf.operbizdata;
                if (!TextUtils.isEmpty(operbizdata)){
                    CacheTableCrud.setGroupRole(String.valueOf(ntf.g), operbizdata, 2);
                }
            }
        }
//        if (!CurrUserTableCrud.curr_isOpenMsgRemind(true)){
//            return;
//        }
        String chatlinkid = ntf.chatlinkid;
        boolean noDisturb = TioConfig.isNoDisturb(2, false, chatlinkid);
        if (noDisturb){
            return;
        }

        // 不是自己发的消息 && 不在会话中
        if (ntf.f != TioDBPreferences.getCurrUid() && !ntf.chatlinkid.equals(TioCache.getInChatLinkId())) {
            // 长连接已建立 && 打开通知，则响铃
            if (TioIMClient.getInstance().isHandshake() && CurrUserTableCrud.curr_isOpenMsgRemind(true)) {
                if (ntf.sendbysys == 1){
                    String syskey = ntf.sysmsgkey;
                    if (syskey == null){
                        syskey = "";
                    }
                    if (syskey.equals("showTime")){
                        return;
                    }
                    if (!TioConfig.OpenCloseConfig.showInviteKickTips()){
                        if (CacheTableCrud.getGroupRoleMy(String.valueOf(ntf.g)) == 2){
                            return;
                        }
                    }
//                    int fromUid = ntf.f;
//                    int fromRole = CacheTableCrud.getGroupRole(String.valueOf(ntf.g), fromUid);
//                    if ((fromRole == 1 || fromRole == 3) && CacheTableCrud.getGroupRoleMy(String.valueOf(ntf.g)) == 2){
//                        if (ntf.sysmsgkey.equals("operkick")){
//                            return;
//                        }
//                        if (ntf.sysmsgkey.contains("join") || ntf.sysmsgkey.contains("managermsgback")){
//                            String operbizdata = null;
//                            operbizdata = ntf.operbizdata;
//                            if (operbizdata == null){
//                                return;
//                            }
//                            String[] splits = operbizdata.split(",");
//                            boolean isHasMy = false;
//                            for (String split : splits){
//                                if (split.equals(String.valueOf(TioDBPreferences.getCurrUid()))){
//                                    isHasMy = true;
//                                    break;
//                                }
//                            }
//                            if (!isHasMy){
//                                return;
//                            }
//                        }
//                    }
                }
                TioBellVibrate.getInstance().start(TioBellVibrate.Bell.MSG_NTF_GROUP);
            }
            // 在后台，则给出通知
//            if (!AppUtils.isAppForeground()){
//                TioNotification.getInstance().notification(getView().getActivity(), ntf.nick, ntf.getShowContent());
//            }
        }
    }

    // 群操作通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxGroupOperNtf(WxGroupOperNtf ntf) {
        WxGroupOperNtf.Oper oper = WxGroupOperNtf.Oper.valueOf(ntf.oper);
        if (oper == null) return;
        switch (oper) {
            case BACK_MSG:
                GroupMsgTableCrud.deleteMsg(ntf.chatlinkid, ntf.bizdata);
                break;
            case DEL_MSG:
                GroupMsgTableCrud.deleteMsg(ntf.chatlinkid, ntf.bizdata);
                break;
        }
    }

    // 用户操作通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxUserOperNtf(WxUserOperNtf ntf) {
        if (ntf.oper == 81){
            String noticeId = ntf.operbizdata;
            ruquestAllNotice();
        }
    }
    public void showNoticeDialog(String content){
//        SpannableStringBuilder format = SpanUtils
//                // 标题
//                .getBuilder("平台公告")
//                .setTexSize(ScreenUtil.sp2px(18))
//                // 时间
//                .setForegroundColor(Color.parseColor("#FF909090"))
//                .setTexSize(ScreenUtil.sp2px(16))
//                // 内容
//                .append(String.format(Locale.getDefault(), "\n\n%s", StringUtil.nonNull("111111111111111")))
//                .setTexSize(ScreenUtil.sp2px(16))
//
//                .create();

        TioAllNoticeDialog dialog = new TioAllNoticeDialog(content, new TioAllNoticeDialog.OnConfirmListener() {
            @Override
            public void onConfirm(View view, TioAllNoticeDialog dialog) {
                dialog.dismiss();
            }
        });
        dialog.show_canceledOnTouchOutside(MyActivityManager.getInstance().getCurrentActivity());
    }

}
