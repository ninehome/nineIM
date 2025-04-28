package com.tiocloud.chat.feature.session.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.session.common.action.util.ActionUtil;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.model.SessionContainer;
import com.tiocloud.chat.feature.session.common.model.SessionExtras;
import com.tiocloud.chat.feature.session.common.mvp.SessionFragmentContract;
import com.tiocloud.chat.feature.session.common.mvp.SessionFragmentPresenter;
import com.tiocloud.chat.feature.session.common.panel.MsgInputPanel;
import com.tiocloud.chat.feature.session.common.panel.MsgListPanel;
import com.tiocloud.chat.feature.session.common.proxy.MsgListProxy;
import com.tiocloud.chat.feature.session.common.proxy.SessionProxy;
import com.tiocloud.chat.util.ScreenUtil;
import com.tiocloud.chat.util.StringUtil;
import com.tiocloud.chat.util.TimeUtil;
import com.tiocloud.chat.yanxun.view.MarqueTextView;
import com.tiocloud.chat.yanxun.view.MarqueeHorizontalTextView;
import com.tiocloud.jpush.utils.LogUtils;
import com.watayouxiang.androidutils.page.TioFragment;
import com.watayouxiang.androidutils.util.SpanUtils;
import com.watayouxiang.androidutils.widget.dialog.confirm.TioConfirmDialog;
import com.watayouxiang.appupdate.utils.Md5Util;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.imclient.utils.MD5Utils;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc : 会话页
 */
public abstract class SessionFragment extends TioFragment implements SessionProxy, SessionFragmentContract.View {

    private MsgInputPanel inputPanel;
    private MsgListPanel listPanel;
    private SessionFragmentPresenter presenter;

    private MarqueeHorizontalTextView tvNotice;

    private String chatMode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.message_fragment, container, false);
    }

    public void setChatMode(String chatMode){
        this.chatMode = chatMode;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null){
            chatMode = getArguments().getString(SessionExtras.CHATMODE);
        }
        // mvp
        presenter = new SessionFragmentPresenter(this);
        // 设置 Actions
        ActionUtil.setParams(getSessionActivity().getActions(), this, getChatLinkIds());
        // 初始化页面
        SessionContainer container = new SessionContainer(getSessionActivity(), this, getView(),
                getSessionActivity().getActions(), getChatLinkIds());
        listPanel = new MsgListPanel(container,chatMode);
        inputPanel = new MsgInputPanel(container);
        tvNotice = getView().findViewById(R.id.tvNotice);

        tvNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvNotice.getText().toString().length() < 4){
                    return;
                }
                showNoticeDialog();
            }
        });
    }

    public void showNoticeDialog(){
        if (tvNotice == null){
            return;
        }
        SpannableStringBuilder format = SpanUtils
                // 标题
                .getBuilder(getString(R.string.group_notice))
                .setTexSize(ScreenUtil.sp2px(18))
                // 时间
                .setForegroundColor(Color.parseColor("#FF909090"))
                .setTexSize(ScreenUtil.sp2px(16))
                // 内容
                .append(String.format(Locale.getDefault(), "\n\n%s", StringUtil.nonNull(tvNotice.getText().toString().substring(4))))
                .setTexSize(ScreenUtil.sp2px(16))

                .create();

        new TioConfirmDialog(format, new TioConfirmDialog.OnConfirmListener() {
            @Override
            public void onConfirm(View view, TioConfirmDialog dialog) {
                dialog.dismiss();
            }
        }).show_unCancel(getContext());
    }

    public void refreshNotice(String notice){
        if (notice == null || notice.length() < 1){
            tvNotice.setVisibility(View.GONE);
            return;
        }
        tvNotice.setText(getString(R.string.group_notice)+notice);
        tvNotice.setVisibility(View.VISIBLE);
        tvNotice.setSelected(true);
        tvNotice.start();

        if (TioConfig.OpenCloseConfig.noticeShowDialog()){
            List<String> chatLinkIds = getChatLinkIds();
            if (chatLinkIds != null && chatLinkIds.size() > 0){
                String chatLinkId = chatLinkIds.get(0);
                String md5 = MD5Utils.getMd5(chatLinkId + notice);
                String noticeMd5 = TioDBPreferences.getString("noticeMd5", "");
                if (!noticeMd5.contains(md5)){
                    showNoticeDialog();
                    TioDBPreferences.saveString("noticeMd5", md5+noticeMd5);
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (inputPanel != null) {
            inputPanel.collapse(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActionUtil.onActivityResult(requestCode, resultCode, data, getSessionActivity().getActions());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ActionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults, getSessionActivity().getActions());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.d("zlb===========>onDestroyView");
        ActionUtil.release(getSessionActivity().getActions());
        presenter.detachView();
        inputPanel.release();
        listPanel.release();
    }

    // ====================================================================================
    // getter
    // ====================================================================================

    @NonNull
    public abstract List<String> getChatLinkIds();

    @Override
    @NonNull
    public SessionActivity getSessionActivity() {
        Activity activity = getActivity();
        return (SessionActivity) /*Objects.requireNonNull*/(activity);
    }

    @Override
    public MsgInputPanel getInputPanel() {
        return inputPanel;
    }

    @Override
    public MsgListProxy getMsgListProxy() {
        return listPanel;
    }

    // ====================================================================================
    // proxy
    // ====================================================================================

    @Override
    public boolean onBackPressed() {
        if (inputPanel != null) {
            return inputPanel.collapse(true);
        }
        return false;
    }

    @Override
    public void onInputPanelExpand() {
        if (listPanel != null) {
            listPanel.scrollToBottomDelayed();
        }
    }

    @Override
    public void collapseInputPanel() {
        if (inputPanel != null) {
            inputPanel.collapse(false);
        }
    }

    @Override
    public boolean onAvatarLongClick(View v, TioMsg msg) {
        return false;
    }
}
