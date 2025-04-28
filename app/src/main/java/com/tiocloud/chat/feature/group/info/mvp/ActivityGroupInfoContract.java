package com.tiocloud.chat.feature.group.info.mvp;

import com.watayouxiang.httpclient.model.response.GroupInfoResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.androidutils.page.TioFragment;

/**
 * author : TaoWang
 * date : 2020-02-26
 * desc :
 */
public interface ActivityGroupInfoContract {
    interface View extends BaseView {
        String getGroupId();

        void addFragment(TioFragment fragment);
    }

    abstract class Model extends BaseModel {
        public abstract void getGroupInfo(String userFlag, String groupId, DataProxy<GroupInfoResp> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void showFragment();
    }
}
