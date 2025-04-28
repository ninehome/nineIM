package com.watayouxiang.wallet.feature.bill.fragment.mvp;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.PayGetWalletItemsResp;
import com.watayouxiang.wallet.feature.bill.fragment.BillVo;
import com.watayouxiang.wallet.feature.bill.fragment.adapter.BillItem;
import com.watayouxiang.wallet.feature.bill.fragment.adapter.BillModel;

import java.util.ArrayList;
import java.util.List;

public class Presenter extends Contract.Presenter {
    private int pageNumber;

    public Presenter(Contract.View view) {
        super(new Model(), view, false);
    }

    @Override
    public void init() {
        getView().resetUI();
        refreshData();
    }

    @Override
    public void refreshData() {
        getWalletItems(1);
    }

    @Override
    public void loadMoreData() {
        getWalletItems(++pageNumber);
    }

    private void getWalletItems(int pageNumber) {
        this.pageNumber = pageNumber;
        BillVo billVo = getView().getBillVo();

        getModel().getWalletItems(billVo.mode, pageNumber + "", new TioCallback<PayGetWalletItemsResp>() {
            @Override
            public void onTioSuccess(PayGetWalletItemsResp resp) {

//                ToastUtils.showShort(new Gson().toJson(resp));
                List<PayGetWalletItemsResp.ListBean> list = resp.getList();
                boolean firstPage = resp.isFirstPage();
                boolean lastPage = resp.isLastPage();

                ArrayList<BillModel> models = getBillModels(list);

                if (firstPage) {
                    getView().setRefreshData(true, models);
                    if (lastPage) {
                        getView().setLoadMoreData(true, true, null);
                    }
                } else {
                    getView().setLoadMoreData(true, lastPage, models);
                }
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
                if (pageNumber <= 1) {
                    getView().setRefreshData(false, null);
                } else {
                    getView().setLoadMoreData(false, false, null);
                }
            }
        });
    }

    private ArrayList<BillModel> getBillModels(List<PayGetWalletItemsResp.ListBean> list) {
        if (list == null) return new ArrayList<>();
        int size = list.size();
        ArrayList<BillModel> models = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            PayGetWalletItemsResp.ListBean bean = list.get(i);
            BillItem item = new BillItem();
            item.setTitle(bean.getRemark());
            item.setSubtitle(bean.getCreatetime());
            item.setRightTitle(bean);
            item.setRightSubtitle(bean);
            item.setRightTitleTextColor(bean);
            models.add(new BillModel(item, bean));
        }
        return models;
    }
}
