package com.watayouxiang.wallet.feature.redpaper.feature.p2p;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.watayouxiang.androidutils.page.TioFragment;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.model.body.wx.WxChatItemInfoReq;
import com.watayouxiang.imclient.model.body.wx.WxChatItemInfoResp;
import com.watayouxiang.imclient.packet.TioPacket;
import com.watayouxiang.imclient.packet.TioPacketBuilder;
import com.watayouxiang.wallet.databinding.WalletRedpaperP2pFragmentBinding;
import com.watayouxiang.wallet.feature.redpaper.RedPaperActivity;
import com.watayouxiang.wallet.tools.MoneyInputFilter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/09
 *     desc   : 私聊：一对一红包
 * </pre>
 */
public class P2PFragment extends TioFragment {

    public final ObservableField<String> money = new ObservableField<>("");
    public final ObservableField<String> moneyHint = new ObservableField<>("输入总金额");
    public final ObservableField<String> gift = new ObservableField<>("");
    public final ObservableField<String> giftHint = new ObservableField<>("恭喜发财，吉祥如意");
    public final ObservableField<String> toWhoInfo = new ObservableField<>("发给 小生");

    private WalletRedpaperP2pFragmentBinding binding;
    private P2PViewModel viewModel;
    private String chatLinkId;

    public static P2PFragment newInstance() {
        return new P2PFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = WalletRedpaperP2pFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TioIMClient.getInstance().getEventEngine().register(this);
        binding.setData(this);
        viewModel = newViewModel(P2PViewModel.class);
        binding.etMoney.setFilters(new InputFilter[]{new MoneyInputFilter(binding.etMoney)});
        toWhoInfo.set("");
        getChatInfo(getRedPaperActivity().getRedPaperVo().chatlinkid);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        TioIMClient.getInstance().getEventEngine().unregister(this);
    }

    // 获取聊天信息
    private void getChatInfo(String chatLinkId) {
        this.chatLinkId = chatLinkId;
        TioPacket wxChatItemInfoReq = TioPacketBuilder.getWxChatItemInfoReq(new WxChatItemInfoReq(chatLinkId));
        TioIMClient.getInstance().sendPacket(wxChatItemInfoReq);
    }

    // 聊天信息响应
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChatInfoResp(WxChatItemInfoResp resp) {
        if (!resp.chatlinkid.equals(chatLinkId)) {
            return;
        }
        WxChatItemInfoResp.DataBean data = resp.data;
        toWhoInfo.set(String.format(Locale.getDefault(), "发给 %s", data.name));
    }

    public void clickOkBtn(View view) {
//        if (TioWallet.IS_TEST) {
//            SendRedPaperEvent sendRedPaperEvent = new SendRedPaperEvent(2);
//            TioIMClient.getInstance().getEventEngine().post(sendRedPaperEvent);
//        }
        if (ClickUtils.isViewSingleClick(view)) {
            viewModel.postSendRedPacket(this);
        }
    }

    public RedPaperActivity getRedPaperActivity() {
        return (RedPaperActivity) getActivity();
    }

    public String getGiftTxt() {
        String _gift = this.gift.get();
        String _giftHint = this.giftHint.get();
        if (!TextUtils.isEmpty(_gift)) {
            return _gift;
        } else {
            return _giftHint;
        }
    }
}
