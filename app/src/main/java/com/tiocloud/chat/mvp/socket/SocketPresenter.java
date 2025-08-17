package com.tiocloud.chat.mvp.socket;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.tiocloud.chat.R;
import com.tiocloud.jpush.PushLauncher;
import com.watayouxiang.httpclient.prefernces.CookieUtils;
import com.tiocloud.chat.preferences.ConfigPreferences;
import com.tiocloud.chat.preferences.TioCache;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.model.response.ImServerResp;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.client.IMConfig;
import com.watayouxiang.imclient.packet.TioCommand;
import com.watayouxiang.imclient.packet.TioHandshake;
//import com.tiocloud.jpush.PushLauncher;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.androidutils.widget.TioToast;

/**
 * author : TaoWang
 * date : 2020-02-12
 * desc : 长连接表现层
 */
public class SocketPresenter extends SocketContract.Presenter {

    public SocketPresenter(SocketContract.View view) {
        super(view);
        autoUpdateSocketToken();
    }

    @Override
    public boolean isConnected() {
        return TioIMClient.getInstance().isConnected();
    }

    @Override
    public void connectSocket(final ConnectCallback callback) {
        // 确保没有建立长连接
        if (isConnected()) {
            TioLogger.i("connectSocket --> already connect");
            callback.onConnectSocketResp(ConnectResp.AlreadyConnect);
            return;
        }

        // 确保登录
        if (!getModel().isLogin()) {
            TioLogger.i("connectSocket --> not login");
            callback.onConnectSocketResp(ConnectResp.UnLogin);
            return;
        }

        // 确保存在token
        final String token = getModel().getSocketToken();
        if (token == null) {
            TioLogger.i("connectSocket --> token null");
            callback.onConnectSocketResp(ConnectResp.TokenNull);
            return;
        }

        // 确保长连接地址存在
        getModel().requestImServer(new BaseModel.DataProxy<ImServerResp>() {
            @Override
            public void onSuccess(ImServerResp data) {
                if (realConnectSocket(data.ip, data.port, data.ssl == 1, token)) {
                    callback.onConnectSocketResp(ConnectResp.ConnectSuccess);
                } else {
                    callback.onConnectSocketResp(ConnectResp.AlreadyConnect);
                }
            }

            @Override
            public void onFailure(String msg) {
                TioLogger.i("connectSocket --> ImServer null");
                TioToast.showShort(getView().getActivity().getString(R.string.get_imadress_fail) + msg);
                callback.onConnectSocketResp(ConnectResp.ImServerNull);
            }
        });
    }

    @Override
    public void exitApp() {
        AppUtils.exitApp();
    }

    @Override
    public void finishAllActivity() {
        ActivityUtils.finishAllActivities();
    }

    private boolean realConnectSocket(String ip, int port, boolean openSsl, String token) {
        // 确保没有建立长连接
        if (isConnected()) {
            TioLogger.i("connectSocket --> already connect");
            return false;
        }

        long imHeartbeatTimeout = ConfigPreferences.getImHeartbeatTimeout();
        if (imHeartbeatTimeout == -1) {
            TioLogger.i("connectSocket --> imHeartbeatTimeout = " + imHeartbeatTimeout);
            return false;
        }
        imHeartbeatTimeout = imHeartbeatTimeout / 2;
        if (imHeartbeatTimeout < IMConfig.HEARTBEAT_INTERVAL_MIN) {
            TioLogger.i("connectSocket --> imHeartbeatTimeout = " + imHeartbeatTimeout);
            return false;
        }

        // 服务器地址配置
        TioIMClient.getInstance().setConfig(new IMConfig.Builder(ip, port)
                .setHeartBeatInterval(imHeartbeatTimeout)
                .setOpenSsl(openSsl)
                .build());
        // 握手配置
        TioIMClient.getInstance().setHandshake(new TioHandshake.Builder(
                token,
                TioCache.getHandShakeKey(),
                TioCommand.WX_HANDSHAKE_REQ)
                .setActivity(getView().getActivity())
                .setCid("official")
                .setJpushinfo(PushLauncher.getInstance().getRegistrationID())
                .build());
        // 启动
        TioIMClient.getInstance().connect();

        TioLogger.i("connectSocket --> connect success");
        return true;
    }

    private void autoUpdateSocketToken() {
        TioHttpClient.getInstance().setOnCookieListener(tioCookies -> {
            // 获取新token
            String newTioCookie = CookieUtils.getCookie(tioCookies);
            // 更新token
            if (newTioCookie != null) {
                TioIMClient.getInstance().updateToken(newTioCookie);
            }
        });
    }
}
