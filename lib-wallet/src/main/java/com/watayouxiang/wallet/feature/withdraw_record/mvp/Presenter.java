package com.watayouxiang.wallet.feature.withdraw_record.mvp;

import com.blankj.utilcode.util.StringUtils;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.PayWithholdListReq;
import com.watayouxiang.httpclient.model.response.PayWithholdListResp;
import com.watayouxiang.wallet.feature.withdraw_record.adapter.ListModel;
import com.watayouxiang.wallet.feature.withdraw_record.adapter.NormalItem;
import com.watayouxiang.wallet.tools.MoneyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        getWithholdList(1);
    }

    @Override
    public void loadMoreData() {
        getWithholdList(++pageNumber);
    }

    private void getWithholdList(int pageNumber) {
        this.pageNumber = pageNumber;

        PayWithholdListReq withholdListReq = new PayWithholdListReq(pageNumber + "");
        withholdListReq.setCancelTag(this);
        withholdListReq.get(new TioCallback<PayWithholdListResp>() {
            @Override
            public void onTioSuccess(PayWithholdListResp resp) {
                List<PayWithholdListResp.ListBean> list = resp.getList();
                boolean firstPage = resp.isFirstPage();
                boolean lastPage = resp.isLastPage();

                ArrayList<ListModel> models = getModels(list);

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

    private ArrayList<ListModel> getModels(List<PayWithholdListResp.ListBean> list) {
        if (list == null) list = new ArrayList<>();
        int size = list.size();

        ArrayList<ListModel> models = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            PayWithholdListResp.ListBean bean = list.get(i);

            NormalItem item = new NormalItem();
            item.setTitleTxt(String.format(Locale.getDefault(), "提现 到%s（%s）", bean.getBankname(), getBankCardNo(bean.getBankcardnumber())));
            item.setSubtitleTxt(StringUtils.null2Length0(bean.getBizcreattime()));
            item.setMoney(MoneyUtils.fen2yuan(bean.getArrivalamount() + ""));
            item.setStatus(bean.getStatus());

            models.add(new ListModel(item, bean));
        }
        return models;
    }

    private String getBankCardNo(String bankcardnumber) {
        if (bankcardnumber != null && bankcardnumber.length() >= 4) {
            return bankcardnumber.substring(bankcardnumber.length() - 4);
        }
        return "";
    }
}
