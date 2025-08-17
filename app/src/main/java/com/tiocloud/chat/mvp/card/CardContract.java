package com.tiocloud.chat.mvp.card;

import android.content.Context;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

public interface CardContract {
    abstract class Model extends BaseModel {
    }

    abstract class Presenter extends BasePresenter<Model, BaseView> {
        public Presenter(Model model) {
            super(model);
        }

        public abstract void openP2PCard(Context context, String uid);

        public abstract void openGroupCard(Context context, String groupId, String shareFromUid, OpenGroupCardListener listener);

        public abstract void openGroupCard(Context context, String groupId, String shareFromUid);
    }

    interface OpenGroupCardListener {
        void onOpenGroupCardSuccess();

        void onOpenGroupCardError();
    }
}
