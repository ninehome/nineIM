package com.watayouxiang.wallet.feature.redpaper.feature.single;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.StringUtils;
import com.watayouxiang.androidutils.listener.SimpleTextWatcher;
import com.watayouxiang.androidutils.page.TioFragment;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.model.body.wx.WxChatItemInfoReq;
import com.watayouxiang.imclient.model.body.wx.WxChatItemInfoResp;
import com.watayouxiang.imclient.packet.TioPacket;
import com.watayouxiang.imclient.packet.TioPacketBuilder;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.databinding.WalletRedpaperSingleFragmentBinding;
import com.watayouxiang.wallet.feature.redpaper.RedPaperActivity;
import com.watayouxiang.wallet.tools.MoneyInputFilter;
import com.watayouxiang.wallet.tools.MoneyUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/09
 *     desc   : 群：普通红包
 * </pre>
 */
public class SingleFragment extends TioFragment {

    private WalletRedpaperSingleFragmentBinding binding;
    private SingleViewModel viewModel;
    private String chatLinkId;

    public final ObservableField<String> money = new ObservableField<>("");
    public final ObservableField<String> moneyHint = new ObservableField<>("输入总金额");
    public final ObservableField<String> amount = new ObservableField<>("");
    public final ObservableField<String> amountHint = new ObservableField<>("本群共0人");
    public final ObservableField<String> gift = new ObservableField<>("");
    public final ObservableField<String> giftHint = new ObservableField<>("恭喜发财，吉祥如意");
    public final ObservableField<String> totalMoney = new ObservableField<>("");

    public static SingleFragment newInstance() {
        return new SingleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = WalletRedpaperSingleFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TioIMClient.getInstance().getEventEngine().register(this);
        binding.setData(this);
        viewModel = new ViewModelProvider(this).get(SingleViewModel.class);
        binding.etMoney.setFilters(new InputFilter[]{new MoneyInputFilter(binding.etMoney)});
        binding.etMoney.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                onTotalMoneyChanged();
            }
        });
        binding.etAmount.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                onTotalMoneyChanged();
            }
        });
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
        amountHint.set(String.format(Locale.getDefault(), "本群共%d人", data.joinnum));
    }

    private void onTotalMoneyChanged() {
        String money = binding.etMoney.getText().toString();
        String amount = binding.etAmount.getText().toString();
        String _totalMoney;
        if (TextUtils.isEmpty(money) || TextUtils.isEmpty(amount)) {
            _totalMoney = StringUtils.getString(R.string.zero_money);
        } else {
            _totalMoney = MoneyUtils.formatYuan(money, amount);
        }
        totalMoney.set(_totalMoney);
    }

    public void clickOkBtn(View view) {
//        if (TioWallet.IS_TEST) {
//            SendRedPaperEvent sendRedPaperEvent = new SendRedPaperEvent(1);
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
