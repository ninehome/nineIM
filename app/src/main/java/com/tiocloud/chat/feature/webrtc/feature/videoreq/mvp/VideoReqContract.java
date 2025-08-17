package com.tiocloud.chat.feature.webrtc.feature.videoreq.mvp;

import androidx.fragment.app.FragmentActivity;

import com.tiocloud.chat.feature.webrtc.data.CallReq;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.webrtclib.model.RTCViewHolder;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/6/4
 *     desc   :
 * </pre>
 */
public interface VideoReqContract {
    interface View extends BaseView {
        void closePage();

        void showWaitingView();

        void showCallingView();

        FragmentActivity getActivity();

        void onUserInfoResp(UserInfoResp userInfoResp);

        void onCountDownTimer(long l);
    }

    abstract class Model extends BaseModel {
        public abstract CallReq getCallReq(FragmentActivity activity);

        public abstract int getToUid(FragmentActivity activity);

        public abstract void reqUserInfo(int uid, View view);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void initRTCViews(RTCViewHolder holder);

        public abstract void reqUserInfo();

        public abstract void call();
    }
}
