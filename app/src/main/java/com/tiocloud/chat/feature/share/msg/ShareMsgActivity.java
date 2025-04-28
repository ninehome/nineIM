package com.tiocloud.chat.feature.share.msg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.StringUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.databinding.ActivityShareMsgBinding;
import com.tiocloud.chat.feature.share.msg.feature.recent.RecentFragment;
import com.tiocloud.chat.feature.share.msg.feature.result.ResultFragment;
import com.tiocloud.chat.feature.share.msg.model.MsgForwardEntity;
import com.tiocloud.chat.feature.share.msg.model.MsgForwardFrom;
import com.tiocloud.chat.feature.share.msg.model.MsgForwardTo;
import com.tiocloud.chat.feature.share.msg.mvp.ShareMsgContract;
import com.tiocloud.chat.feature.share.msg.mvp.ShareMsgPresenter;
import com.tiocloud.chat.widget.dialog.base.CardDialog;
import com.watayouxiang.androidutils.listener.SimpleTextWatcher;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.httpclient.model.response.ChatListResp;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/20
 *     desc   : 把 "消息" 转发给 "好友/群聊"（消息转发）
 * </pre>
 */
public class ShareMsgActivity extends TioActivity implements ShareMsgContract.View {

    private ShareMsgPresenter presenter;
    private ActivityShareMsgBinding binding;
    private RecentFragment recentFragment;
    private ResultFragment resultFragment;
    String msgIds;
    private ChatListResp chatListResp;

    /**
     * @param chatlinkid 会话id
     * @param mids       消息id（多条 "," 分割）
     */
    public static void start(Context context, String chatlinkid, String mids) {
        Intent starter = new Intent(context, ShareMsgActivity.class);
        starter.putExtra(TioExtras.CHAT_LINK_ID, chatlinkid);
        starter.putExtra(TioExtras.MIDS, mids);
        starter.putExtra("msgIds", mids);
        context.startActivity(starter);
    }


    public static void multiStart(Context context, String chatlinkid, String msgIds,String chatMode) {
        Intent starter = new Intent(context, ShareMsgActivity.class);
        starter.putExtra(TioExtras.CHAT_LINK_ID, chatlinkid);
        starter.putExtra("msgIds", msgIds);
        starter.putExtra("chatMode", chatMode);
        context.startActivity(starter);
    }

    @Override
    public String getChatLinkId() {
        return getIntent().getStringExtra(TioExtras.CHAT_LINK_ID);
    }

    @Override
    public String getMids() {
        return getIntent().getStringExtra(TioExtras.MIDS);
    }

    @Override
    public String getChatMode() {
        return getIntent().getStringExtra("chatMode");
    }

    @Override
    public String getBizid() {
        return getIntent().getStringExtra("bizid");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ShareMsgPresenter(this);
        presenter.initUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void bindContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_share_msg);
    }

    boolean isMultiChoose;
    @Override
    public void initEditText() {
        binding.titleBar.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMultiChoose = !isMultiChoose;
                binding.titleBar.getTvRight().setText(isMultiChoose ? "单选":"复选");
                switchChoose(isMultiChoose);
            }
        });
        binding.etInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                presenter.onEtTextChanged(s);
            }
        });
    }

    @Override
    public void initFragmentContainers() {
        recentFragment = new RecentFragment();
        recentFragment.setContainerId(binding.frameLayout.getId());
        addFragment(recentFragment);

        resultFragment = new ResultFragment();
        resultFragment.setContainerId(binding.frameLayout.getId());
        addFragment(resultFragment);
    }

    @Override
    public void showRecentPage() {
        showFragment(recentFragment);
        hideFragment(resultFragment);
    }

    @Override
    public void showSearchResultPage(String s) {
        resultFragment.setSearchKey(s);
        showFragment(resultFragment);
        hideFragment(recentFragment);
    }

    @Override
    public void forwardMsg(MsgForwardTo entity) {
        if (presenter != null) {
            presenter.forwardMsg(entity);
        }
    }


    @Override
    public void showMsgForwardDialog(MsgForwardEntity entity,MsgForwardTo msgForwardTo) {
        MsgForwardFrom from = entity.from;
        MsgForwardTo to = entity.to;

        String msgIds = getIntent().getStringExtra("msgIds");
        CardDialog cardDialog = new CardDialog(getActivity());
        cardDialog.tv_title.setText("转发给");
        cardDialog.hiv_avatar.tio_roundAvatar(to.toAvatar);
        cardDialog.tv_usrName.setText(StringUtils.null2Length0(to.toName));
        cardDialog.tv_positiveBtn.setText("转发消息");
        cardDialog.tv_positiveBtn.setOnClickListener(view -> {
            from.mids = msgIds;
            String[] str = from.mids.split(",");
            if(str.length > 1){
                if(getChatMode().equals("1")){
                    presenter.reqMultiMsgForward(msgForwardTo.chatlinkid,msgForwardTo.bizid,getChatMode(),from.mids);
                }else {
                    presenter.reqMultiMsgForward(msgForwardTo.chatlinkid,msgForwardTo.bizid,getChatMode(),from.mids);
                }
            }else {
                presenter.reqMsgForward(from.chatLinkId, to.toUid, to.toGroupId, from.mids);
            }

        });
        cardDialog.show();
    }

    @Override
    public void switchChoose(boolean isMultiChoose) {
        if (presenter != null) {
            presenter.switchCHoose(isMultiChoose);
        }
    }
}
