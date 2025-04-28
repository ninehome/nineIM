package com.watayouxiang.wallet.feature.redpacket.feature.receive;

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
import com.watayouxiang.httpclient.model.response.PayGrabRedPacketStatResp;
import com.watayouxiang.httpclient.prefernces.HttpCache;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.databinding.WalletRedpacketReceiveFragmentBinding;
import com.watayouxiang.wallet.feature.redpacket.RedPacketActivity;
import com.watayouxiang.wallet.feature.redpacket.feature.receive.adapter.ReceiveListAdapter;
import com.watayouxiang.wallet.feature.redpacket.feature.receive.adapter.ReceiveModel;
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
public class ReceiveFragment extends TioFragment implements SwipeRefreshLayout.OnRefreshListener {

    public WalletRedpacketReceiveFragmentBinding binding;
    public ReceiveListAdapter listAdapter;
    private ReceiveViewModel viewModel;

    public final ObservableField<String> userInfo = new ObservableField<>("李渝共收到");
    public final ObservableField<String> money = new ObservableField<>("183.05");
    public final ObservableField<String> packetAmount = new ObservableField<>("22");
    public final ObservableField<String> bestAmount = new ObservableField<>("11");

    public static ReceiveFragment newInstance() {
        return new ReceiveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = WalletRedpacketReceiveFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setData(this);
        viewModel = new ViewModelProvider(this).get(ReceiveViewModel.class);
        resetUI();
        onRefresh();
    }

    private void resetUI() {
        // init bill list
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listAdapter = new ReceiveListAdapter(null);
        listAdapter.setOnLoadMoreListener(() -> viewModel.loadMoreData(ReceiveFragment.this), binding.recyclerView);
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
        // 最佳手气数
        bestAmount.set("");

        // 无数据，不开放显示
        binding.tvTipRight.setVisibility(View.GONE);
        binding.tvAmountRight.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        viewModel.refreshData(ReceiveFragment.this);
    }

    public void setRefreshData(List<ReceiveModel> models) {
        listAdapter.setNewData(models);
        if (CollectionUtils.isEmpty(models)) {
            View notDataView = getLayoutInflater().inflate(R.layout.wallet_redpacket_list_empty, (ViewGroup) binding.recyclerView.getParent(), false);
            listAdapter.setEmptyView(notDataView);
        }
    }

    public RedPacketActivity getRedPacketActivity() {
        return (RedPacketActivity) getActivity();
    }

    public void onHeaderInfoResp(PayGrabRedPacketStatResp resp) {
        int amount = resp.getAmount();
        String redNum = resp.getNum();
        String nick = resp.getNick();
        binding.ivAvatar.loadStatic(HttpCache.getResUrl(resp.getAvatar()));
        money.set(MoneyUtils.fen2yuan(amount + ""));
        packetAmount.set(StringUtils.null2Length0(redNum));
        bestAmount.set("");
        userInfo.set(String.format(Locale.getDefault(), getString(R.string.gongshoudao), StringUtils.null2Length0(nick)));
    }
}
