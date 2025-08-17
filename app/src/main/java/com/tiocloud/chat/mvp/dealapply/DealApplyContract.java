package com.tiocloud.chat.mvp.dealapply;

import android.content.Context;

import com.watayouxiang.httpclient.model.response.DealApplyResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public interface DealApplyContract {
    interface View extends BaseView {
        Context getContext();
    }

    abstract class Model extends BaseModel {
        public abstract void dealApply(String applyId, String remarkName, DataProxy<DealApplyResp> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void start(String applyId, String remarkName, DealApplyProxy proxy);

        public abstract static class DealApplyProxy {
            public abstract void onSuccess(DealApplyResp data);

            public void onFailure(String msg) {

            }

            public void onCancel() {

            }

            public void onFinish() {

            }
        }
    }
}
