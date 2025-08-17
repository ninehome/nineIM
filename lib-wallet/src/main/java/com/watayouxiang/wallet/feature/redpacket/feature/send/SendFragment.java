package com.watayouxiang.wallet.feature.redpacket.feature.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.StringUtils;
import com.watayouxiang.androidutils.page.TioFragment;
import com.watayouxiang.httpclient.model.response.PaySendRedPacketStatResp;
import com.watayouxiang.httpclient.prefernces.HttpCache;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.databinding.WalletRedpacketSendFragmentBinding;
import com.watayouxiang.wallet.feature.redpacket.RedPacketActivity;
import com.watayouxiang.wallet.feature.redpacket.feature.send.adapter.SendListAdapter;
import com.watayouxiang.wallet.feature.redpacket.feature.send.adapter.SendModel;
import com.watayouxiang.wallet.tools.MoneyUtils;

import java.util.List;
import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/03
 *     desc   :
 * </pre>
 */
public class SendFragment extends TioFragment implements SwipeRefreshLayout.OnRefreshListener {

    public WalletRedpacketSendFragmentBinding binding;
    public SendListAdapter listAdapter;
    private SendViewModel viewModel;

    public final ObservableField<String> userInfo = new ObservableField<>("李渝共发出");
    public final ObservableField<String> money = new ObservableField<>("83.05");
    public final ObservableField<String> packetAmount = new ObservableField<>("152个");

    public static SendFragment newInstance() {
        return new SendFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = WalletRedpacketSendFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setData(this);
        viewModel = new ViewModelProvider(this).get(SendViewModel.class);
        resetUI();
        onRefresh();
    }

    private void resetUI() {
        // init bill list
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listAdapter = new SendListAdapter(null);
        listAdapter.setOnLoadMoreListener(() -> viewModel.loadMoreData(SendFragment.this), binding.recyclerView);
        binding.recyclerView.setAdapter(listAdapter);
        binding.swipeRefreshLayout.setOnRefreshListener(this);

        // 监听切换日期
        getRedPacketActivity().setOnSpinnerSelectedListener(this::onRefresh);

        // 头像
//        binding.ivAvatar.loadStatic(null, 6);
        // 用户信息
        userInfo.set("");
        // 总金额
        money.set("");
        // 收到红包数
        packetAmount.set("");
    }

    @Override
    public void onRefresh() {
        viewModel.refreshData(this);
    }

    public void setRefreshData(List<SendModel> models) {
        listAdapter.setNewData(models);
        if (CollectionUtils.isEmpty(models)) {
            View notDataView = getLayoutInflater().inflate(R.layout.wallet_redpacket_list_empty, (ViewGroup) binding.recyclerView.getParent(), false);
            listAdapter.setEmptyView(notDataView);
        }
    }

    public RedPacketActivity getRedPacketActivity() {
        return (RedPacketActivity) getActivity();
    }

    public void onHeaderInfoResp(PaySendRedPacketStatResp resp) {
        int amount = resp.getAmount();
        String redNum = resp.getNum();
        String nick = resp.getNick();

        money.set(MoneyUtils.fen2yuan(amount + ""));
        packetAmount.set(String.format(Locale.getDefault(), getString(R.string.ge), StringUtils.null2Length0(redNum)));
        userInfo.set(String.format(Locale.getDefault(), getString(R.string.gefachu), StringUtils.null2Length0(nick)));
        binding.ivAvatar.loadStatic(HttpCache.getResUrl(resp.getAvatar()));
    }
}
