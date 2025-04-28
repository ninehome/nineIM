package com.tiocloud.chat.feature.session.p2p.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tiocloud.chat.feature.session.common.SessionFragment;
import com.tiocloud.chat.feature.session.common.model.SessionExtras;
import com.tiocloud.chat.feature.session.p2p.P2PSessionActivity;
import com.tiocloud.chat.feature.session.p2p.fragment.mvp.P2PFragmentContract;
import com.tiocloud.chat.feature.session.p2p.fragment.mvp.P2PFragmentPresenter;
import com.watayouxiang.androidutils.util.HtmlUtils;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.model.body.wx.WxFriendChatReq;
import com.watayouxiang.imclient.packet.TioPacket;
import com.watayouxiang.imclient.packet.TioPacketBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-09
 * desc : 私聊
 */
public class P2PSessionFragment extends SessionFragment implements P2PFragmentContract.View {

    public static P2PSessionFragment create(@NonNull String chatLinkId) {
        P2PSessionFragment fragment = new P2PSessionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SessionExtras.EXTRA_CHAT_LINK_ID, chatLinkId);
        bundle.putString(SessionExtras.CHATMODE, "1");
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public P2PSessionActivity getP2PSessionActivity() {
        return (P2PSessionActivity) getActivity();
    }

    @NonNull
    @Override
    public List<String> getChatLinkIds() {
        String string = getArguments().getString(SessionExtras.EXTRA_CHAT_LINK_ID);
        List<String> strings = new ArrayList<>();
        strings.add(string);
        return strings;
    }


    @Override
    public boolean sendMessage(String msg) {
        WxFriendChatReq wxP2PChatReq = new WxFriendChatReq(HtmlUtils.escapeHtml(msg), Integer.parseInt(getChatLinkIds().get(0)));
        TioPacket packet = TioPacketBuilder.getWxFriendChatReq(wxP2PChatReq);
        return TioIMClient.getInstance().sendPacket(packet);
    }

    // ====================================================================================
    // init
    // ====================================================================================

    private P2PFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new P2PFragmentPresenter(this);
        presenter.init();
        setChatMode("1");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void initList() {
        getMsgListProxy().setOnFetchMoreListener(() -> presenter.loadMore());
    }

}
