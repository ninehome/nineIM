package com.watayouxiang.wallet.feature.withdraw_record.mvp;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.wallet.feature.withdraw_record.adapter.ListModel;

import java.util.List;

public interface Contract {
    interface View extends BaseView {
        void resetUI();

        void setRefreshData(boolean ok, List<ListModel> models);

        void setLoadMoreData(boolean ok, boolean lastPage, List<ListModel> models);
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }
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
