package com.tiocloud.chat.feature.session.group;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.group.info.GroupInfoActivity;
import com.tiocloud.chat.feature.main.MainActivity;
import com.tiocloud.chat.feature.session.common.SessionActivity;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseAction;
import com.tiocloud.chat.feature.session.common.model.SessionExtras;
import com.tiocloud.chat.feature.session.common.model.SessionType;
import com.tiocloud.chat.feature.session.group.customization.GroupActions;
import com.tiocloud.chat.feature.session.group.fragment.GroupSessionFragment;
import com.tiocloud.chat.feature.session.group.mvp.GroupActivityContract;
import com.tiocloud.chat.feature.session.group.mvp.GroupActivityPresenter;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.model.body.wx.WxChatItemInfoResp;

import java.util.ArrayList;
import java.util.Locale;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc : 群聊
 */
public class GroupSessionActivity extends SessionActivity implements GroupActivityContract.View {

    public static void active(@NonNull Context context, @NonNull String groupId) {
        start(context, MainActivity.class, groupId, null, null);
    }

    public static void enter(@NonNull Context context, @NonNull String chatLinkId) {
        start(context, MainActivity.class, null, chatLinkId, null);
    }

    public static void enter(@NonNull Context context, @NonNull String chatLinkId, Integer msgId) {
        start(context, MainActivity.class, null, chatLinkId, msgId);
    }

    public static void start(@NonNull Context context,
                             @Nullable Class<? extends Activity> backToClass,
                             @Nullable String groupId,
                             @Nullable String chatLinkId, Integer msgId) {
        boolean handshake = TioIMClient.getInstance().isHandshake();
        TioLogger.i(String.format(Locale.getDefault(), "进入群聊页：groupId = %s, chatLinkId = %s, handshake = %b", groupId, chatLinkId, handshake));
        //TODO
        if (TioConfig.OpenCloseConfig.enterGroupChatCheck() && !handshake) {
            TioToast.showShort(context.getString(R.string.current_net_error));
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(SessionExtras.EXTRA_BACK_TO_CLASS, backToClass);
        intent.putExtra(SessionExtras.EXTRA_GROUP_ID, groupId);
        intent.putExtra(SessionExtras.EXTRA_CHAT_LINK_ID, chatLinkId);
        intent.putExtra("msgId", msgId);
        intent.putExtra(SessionExtras.EXTRA_ACTIONS, GroupActions.get());
        intent.setClass(context, GroupSessionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    // ====================================================================================
    // param
    // ====================================================================================

    @Nullable
    public String getGroupId() {
        return getIntent().getStringExtra(SessionExtras.EXTRA_GROUP_ID);
    }

    @Override
    public void setGroupId(String groupId) {
        getIntent().putExtra(SessionExtras.EXTRA_GROUP_ID, groupId);
    }

    public Integer getMsgId(){
        return getIntent().getIntExtra("msgId", 0);
    }

    @Nullable
    public String getChatLinkId() {
        return getIntent().getStringExtra(SessionExtras.EXTRA_CHAT_LINK_ID);
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

    @NonNull
    @Override
    public SessionType getSessionType() {
        return SessionType.GROUP;
    }

    // ====================================================================================
    // init
    // ====================================================================================

    private GroupActivityContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GroupActivityPresenter(this);
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
        setTitle(R.string.session_group);

        String groupId = getGroupId();
        String chatLinkId = getChatLinkId();

        if (chatLinkId != null) {
            presenter.enterGroup(chatLinkId);
        } else if (groupId != null) {
            presenter.activeGroup(groupId);
        } else {
            TioToast.showShort(getString(R.string.init_error));
            finish();
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
        // 标题
        getTitleBar().setTitle(data.name);
        getTitleBar().setSubtitle("(" + data.joinnum + ")");
        // menu
        String groupId = data.bizid;
        boolean isEnableChat = data.linkflag == 1;
        if (isEnableChat) {
            getTitleBar().getMoreBtn().setVisibility(View.VISIBLE);
            getTitleBar().getMoreBtn().setOnClickListener(view -> GroupInfoActivity.start(getActivity(), groupId));
        } else {
            getTitleBar().getMoreBtn().setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void initFragment(String chatLinkId, String groupId) {
        GroupSessionFragment fragment = GroupSessionFragment.create(chatLinkId, groupId);
        this.replaceFragment(fragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
