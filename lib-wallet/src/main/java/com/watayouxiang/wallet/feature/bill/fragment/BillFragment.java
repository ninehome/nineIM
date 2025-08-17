package com.watayouxiang.wallet.feature.bill.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.CollectionUtils;
import com.watayouxiang.androidutils.page.TioFragment;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.httpclient.model.response.PayGetWalletItemsResp;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.databinding.WalletBillFragmentBinding;
import com.watayouxiang.wallet.feature.bill.fragment.adapter.BillListAdapter;
import com.watayouxiang.wallet.feature.bill.fragment.adapter.BillModel;
import com.watayouxiang.wallet.feature.bill.fragment.mvp.Contract;
import com.watayouxiang.wallet.feature.bill.fragment.mvp.Presenter;
import com.watayouxiang.wallet.feature.bill_detail.BillDetailActivity;
import com.watayouxiang.wallet.feature.bill_detail.BillDetailVo;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/03
 *     desc   :
 * </pre>
 */
public class BillFragment extends TioFragment implements Contract.View {

    private static final String KEY_BILL_VO = "KEY_BILL_VO";
    private WalletBillFragmentBinding binding;
    private BillListAdapter billListAdapter;
    private Presenter presenter;

    public static BillFragment newInstance(BillVo billVo) {
        BillFragment fragment = new BillFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BILL_VO, billVo);
        fragment.setArguments(bundle);
        return fragment;
    }

    public BillVo getBillVo() {
        return (BillVo) getArguments().getSerializable(KEY_BILL_VO);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = WalletBillFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new Presenter(this);
        presenter.init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void setRefreshData(boolean ok, List<BillModel> models) {
        if (ok) {
            billListAdapter.setNewData(models);
            if (CollectionUtils.isEmpty(models)) {
                View notDataView = getLayoutInflater().inflate(R.layout.wallet_bill_list_empty, (ViewGroup) binding.recyclerView.getParent(), false);
                billListAdapter.setEmptyView(notDataView);
            }
        }
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setLoadMoreData(boolean ok, boolean lastPage, List<BillModel> models) {
        if (ok) {
            if (models != null) {
                billListAdapter.addData(models);
            }
            if (lastPage) {
                billListAdapter.loadMoreEnd();
            } else {
                billListAdapter.loadMoreComplete();
            }
        } else {
            billListAdapter.loadMoreFail();
        }
    }

    @Override
    public void resetUI() {
        // init bill list
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        billListAdapter = new BillListAdapter(null);
        billListAdapter.setOnLoadMoreListener(() -> presenter.loadMoreData(), binding.recyclerView);
        billListAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (ClickUtils.isViewSingleClick(view)) {
                PayGetWalletItemsResp.ListBean original = billListAdapter.getData().get(position).getOriginal();
                BillDetailVo billDetailVo = BillDetailVo.getInstance(original);
                BillDetailActivity.start(getTioActivity(), billDetailVo);
            }
        });
        binding.recyclerView.setAdapter(billListAdapter);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> presenter.refreshData());
    }
}
