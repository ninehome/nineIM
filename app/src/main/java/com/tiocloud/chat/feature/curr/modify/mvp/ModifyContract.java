package com.tiocloud.chat.feature.curr.modify.mvp;

import android.app.Activity;

import com.tiocloud.chat.feature.curr.modify.model.ModifyType;
import com.tiocloud.chat.feature.curr.modify.model.PageUiModel;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020/4/1
 * desc :
 */
public interface ModifyContract {
    interface View extends BaseView {
        ModifyType getModifyType();

        String getGroupId();

        void resetUI();

        void setUIData(PageUiModel uiModel);

        String getEtContent();

        Activity getActivity();

        String getUid();
    }

    abstract class Model extends BaseModel {
        public abstract void reqModifyGroupName(String etContent, String groupId, DataProxy<String> proxy);

        public abstract void reqModifyGroupIntro(String etContent, String groupId, DataProxy<String> dataProxy);

        public abstract void reqModifyGroupNotice(String etContent, String groupId, DataProxy<String> dataProxy);

        public abstract void reqModifyGroupNick(String nick, String groupId, final DataProxy<String> proxy);

        public abstract void updateNickReq(String nick, DataProxy<String> dataProxy);

        public abstract void updateSignReq(String sign, DataProxy<String> proxy);

        public abstract void modifyRemarkNameReq(String remarkName, String uid, DataProxy<String> dataProxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void init();

        public abstract void onClickMenuBtn(android.view.View view);
    }
}
