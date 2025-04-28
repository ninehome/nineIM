package com.tiocloud.chat.feature.group.removemember.mvp;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public interface ActivityRemoveMemberContract {
    interface View extends BaseView {
        void installFragment();

        void initEditText();
    }

    class Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(View view) {
            super(new Model(), view);
        }

        public abstract void init();
    }
}
