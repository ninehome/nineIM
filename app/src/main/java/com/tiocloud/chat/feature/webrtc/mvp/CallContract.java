package com.tiocloud.chat.feature.webrtc.mvp;

import android.content.Intent;

import com.tiocloud.chat.databinding.TioCallActivityBinding;
import com.tiocloud.chat.feature.webrtc.data.CallNtf;
import com.tiocloud.chat.feature.webrtc.data.CallReq;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.page.TioFragment;

public interface CallContract {
    interface View extends BaseView {
        CallNtf getCallNtf();

        CallReq getCallReq();

        boolean isOpenMsgRemind();

        TioActivity getActivity();

        TioCallActivityBinding getBinding();

        void replaceFragment(TioFragment fragment);
    }

    abstract class Model extends BaseModel {
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void init();

        public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

        public abstract void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
    }
}
