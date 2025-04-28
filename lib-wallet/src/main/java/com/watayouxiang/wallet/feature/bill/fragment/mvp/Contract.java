package com.watayouxiang.wallet.feature.bill.fragment.mvp;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.PayGetWalletItemsResp;
import com.watayouxiang.wallet.feature.bill.fragment.BillVo;
import com.watayouxiang.wallet.feature.bill.fragment.adapter.BillModel;

import java.util.List;

public interface Contract {
    interface View extends BaseView {
        void resetUI();

        void setRefreshData(boolean ok, List<BillModel> models);

        void setLoadMoreData(boolean ok, boolean lastPage, List<BillModel> models);

        BillVo getBillVo();
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void getWalletItems(String mode, String pageNumber, TioCallback<PayGetWalletItemsResp> callback);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void init();

        public abstract void refreshData();

        public abstract void loadMoreData();
    }
}
