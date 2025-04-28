package com.tiocloud.chat.mvp.socket;

import android.app.Activity;

import com.watayouxiang.httpclient.model.response.ImServerResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020-02-12
 * desc :
 */
public interface SocketContract {

    interface View extends BaseView {
        Activity getActivity();
    }

    abstract class Model extends BaseModel {
        public abstract void requestImServer(BaseModel.DataProxy<ImServerResp> imServerRespDataProxy);

        public abstract boolean isLogin();

        public abstract String getSocketToken();
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(View view) {
            super(new SocketModel(), view);
        }

        public abstract boolean isConnected();

        public abstract void connectSocket(ConnectCallback listener);

        public abstract void exitApp();

        public abstract void finishAllActivity();

        public interface ConnectCallback {
            void onConnectSocketResp(ConnectResp resp);
        }

        public enum ConnectResp {
            AlreadyConnect(1, "连接已建立"),
            ConnectSuccess(2, "连接成功"),
            UnLogin(3, "未登录"),
            TokenNull(4, "Token为空"),
            ImServerNull(5, "IM服务器地址为空");

            private final int type;
            private final String name;

            ConnectResp(int type, String name) {
                this.type = type;
                this.name = name;
            }
        }
    }
}
