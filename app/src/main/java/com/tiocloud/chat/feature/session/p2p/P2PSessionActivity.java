package com.tiocloud.chat.feature.session.p2p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.session.common.SessionActivity;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseAction;
import com.tiocloud.chat.feature.session.common.model.SessionExtras;
import com.tiocloud.chat.feature.session.common.model.SessionType;
import com.tiocloud.chat.feature.session.p2p.customization.P2PActions;
import com.tiocloud.chat.feature.session.p2p.fragment.P2PSessionFragment;
import com.tiocloud.chat.feature.session.p2p.mvp.P2PActivityContract;
import com.tiocloud.chat.feature.session.p2p.mvp.P2PActivityPresenter;
import com.tiocloud.chat.util.StringUtil;
import com.tiocloud.chat.widget.dialog.tio2.P2PMoreInfoBottomDialog;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.model.body.wx.WxChatItemInfoResp;

import java.util.ArrayList;
import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020-02-09
 * desc : 私聊
 */
public class P2PSessionActivity extends SessionActivity implements P2PActivityContract.View {

    public String toName;

    public static void active(@NonNull Context context, @NonNull String uid) {
        start(context, null, uid, null, null);
    }

    public static void enter(@NonNull Context context, @NonNull String chatLinkId) {
        start(context, null, null, chatLinkId, null);
    }

    public static void enter(@NonNull Context context, @NonNull String chatLinkId, Integer msgId) {
        start(context, null, null, chatLinkId, msgId);
    }

    public static void start(@NonNull Context context,
                             @Nullable Class<? extends Activity> backToClass,
                             @Nullable String uid,
                             @Nullable String chatLinkId, Integer msgId) {
        boolean handshake = TioIMClient.getInstance().isHandshake();
        TioLogger.i(String.format(Locale.getDefault(), "进入私聊页：uid = %s, chatLinkId = %s, handshake = %b", uid, chatLinkId, handshake));
        //TODO
        if (TioConfig.OpenCloseConfig.enterGroupChatCheck() && !handshake) {

            TioToast.showShort(context.getString(R.string.current_net_error));
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(SessionExtras.EXTRA_BACK_TO_CLASS, backToClass);
        intent.putExtra(SessionExtras.EXTRA_USER_ID, uid);
        intent.putExtra(SessionExtras.EXTRA_CHAT_LINK_ID, chatLinkId);
        intent.putExtra(SessionExtras.EXTRA_ACTIONS, P2PActions.get());
        intent.putExtra("msgId", msgId);
        intent.setClass(context, P2PSessionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    // ====================================================================================
    // param
    // ====================================================================================

    @Nullable
    public String getUid() {
        return getIntent().getStringExtra(SessionExtras.EXTRA_USER_ID);
    }

    @Override
    public void setUid(String uid) {
        getIntent().putExtra(SessionExtras.EXTRA_USER_ID, uid);
    }

    public int getMsgId(){
        return getIntent().getIntExtra("msgId", 0);
    }

    @Nullable
    public String getChatLinkId() {
        return getIntent().getStringExtra(SessionExtras.EXTRA_CHAT_LINK_ID);
    }

    @NonNull
    @Override
    public SessionType getSessionType() {
        return SessionType.P2P;
    }

    @Nullable
    @Override
    protected Class<? extends Activity> getBackToClass() {
        return (Class<? extends Activity>) getIntent().getSerializableExtra(SessionExtras.EXTRA_BACK_TO_CLASS);
    }

    @Nullable
    @Override
    public ArrayList<BaseAction> getActions() {
        return (ArrayList<BaseAction>) getIntent().getSerializableExtra(SessionExtras.EXTRA_ACTIONS);
    }

    // ====================================================================================
    // init
    // ====================================================================================

    private P2PActivityPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new P2PActivityPresenter(this);
        presenter.init();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        presenter.init();
    }

    @Override
    public void initUI() {
        setTitle(getString(R.string.p2p_talk));

        String uid = getUid();
        String chatLinkId = getChatLinkId();
        if (uid != null) {
            presenter.active(uid);
        } else if (chatLinkId != null) {
            presenter.enter(chatLinkId);
        } else {
            TioToast.showShort(getString(R.string.init_error));
            getActivity().finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getChatInfo();
    }

    @Override
    public void onChatInfoResp(WxChatItemInfoResp resp) {
        WxChatItemInfoResp.DataBean data = resp.data;
        // 设置uid
        setUid(data.bizid);
        // 标题
        getTitleBar().setTitle(StringUtil.nonNull(data.name));
        toName = StringUtil.nonNull(data.name);
        // menu
        getTitleBar().getMoreBtn().setOnClickListener(v -> {
//            P2PMoreInfoBottomDialog dialog = new P2PMoreInfoBottomDialog(v.getContext());
//            dialog.initFriendInfo(data.bizid);
//            dialog.initTopChat(data.topflag, resp.chatlinkid, topFlag -> resp.data.topflag = topFlag);
//            dialog.initReportInfo(resp.chatlinkid);
//            dialog.show();
            P2PMoreInfoActivity.start(this, data.bizid, resp.chatlinkid, data.topflag);
        });
    }

    @Override
    public void showFragment(String chatLinkId) {
        replaceFragment(P2PSessionFragment.create(chatLinkId));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
