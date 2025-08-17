package com.watayouxiang.imclient;

import android.app.Activity;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.Utils;
import com.watayouxiang.imclient.app.AppIMClient;
import com.watayouxiang.imclient.model.body.HandshakeReq;
import com.watayouxiang.imclient.model.body.wx.WxFriendMsgReq;
import com.watayouxiang.imclient.model.body.wx.WxFriendMsgResp;
import com.watayouxiang.imclient.model.body.wx.WxGroupMsgReq;
import com.watayouxiang.imclient.model.body.wx.WxGroupMsgResp;
import com.watayouxiang.imclient.model.body.wx.WxHandshakeResp;
import com.watayouxiang.imclient.model.body.wx.WxUpdateTokenReq;
import com.watayouxiang.imclient.model.body.wx.WxUpdateTokenResp;
import com.watayouxiang.imclient.packet.TioCommand;
import com.watayouxiang.imclient.packet.TioPacket;
import com.watayouxiang.imclient.utils.ExceptionUtils;
import com.watayouxiang.imclient.utils.FileLogUtils;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/9/11
 *     desc   : 单例、文件日志
 * </pre>
 */
public class TioIMClient extends AppIMClient {
    private static final String FILE_LOG_NAME = "TioIMClient.log";

    private static class IMClientHolder {
        private static final TioIMClient holder = new TioIMClient();
    }

    public static TioIMClient getInstance() {
        return IMClientHolder.holder;
    }

    private TioIMClient() {
        // 初始化log日志
        FileLogUtils.getInstance().initApp(Utils.getApp());
    }

    @Override
    public void onConnecting() {
        super.onConnecting();
        getFileLogUtils().write(">>> onConnecting");
    }

    @Override
    public void onConnected() {
        super.onConnected();
        getFileLogUtils().write(">>> onConnected");
    }

    @Override
    public void onDisconnected() {
        super.onDisconnected();
        getFileLogUtils().write(">>> onDisconnected");
    }

    @Override
    public void onError(Exception e) {
        super.onError(e);
        getFileLogUtils().write(">>> onError");
        getFileLogUtils().write(ExceptionUtils.getStackTrace(e));
    }

    @Override
    public void onSendPacketEnd(TioPacket packet) {
        super.onSendPacketEnd(packet);
        short command = packet.getCommand();
        Object body = packet.getBody();

        if (command == TioCommand.WX_HANDSHAKE_REQ) {
            HandshakeReq handshakeReq = (HandshakeReq) body;
            getFileLogUtils().write(String.format(Locale.getDefault(), "WX_HANDSHAKE_REQ(599): %s", handshakeReq.toString()));
        } else if (command == TioCommand.WX_UPDATE_TOKEN_REQ) {
            WxUpdateTokenReq wxUpdateTokenReq = (WxUpdateTokenReq) body;
            getFileLogUtils().write(String.format(Locale.getDefault(), "WX_UPDATE_TOKEN_REQ(760): %s", wxUpdateTokenReq.toString()));
        } else if (command == TioCommand.WX_GROUP_MSG_REQ) {
            WxGroupMsgReq wxGroupMsgReq = (WxGroupMsgReq) body;
            getFileLogUtils().write(String.format(Locale.getDefault(), "WX_GROUP_MSG_REQ(620): %s", wxGroupMsgReq.toString()));
        } else if (command == TioCommand.WX_FRIEND_MSG_REQ) {
            WxFriendMsgReq wxFriendMsgReq = (WxFriendMsgReq) body;
            getFileLogUtils().write(String.format(Locale.getDefault(), "WX_FRIEND_MSG_REQ(604): %s", wxFriendMsgReq.toString()));
        }
    }

    @Override
    public void onReceivePacketEnd(TioPacket packet) {
        super.onReceivePacketEnd(packet);
        short command = packet.getCommand();
        Object body = packet.getBody();

        if (command == TioCommand.WX_HANDSHAKE_RESP) {// 握手响应
            WxHandshakeResp wxHandshakeResp = (WxHandshakeResp) body;
            getFileLogUtils().write(String.format(Locale.getDefault(), "WX_HANDSHAKE_RESP(600): %s", wxHandshakeResp.toString()));
        } else if (command == TioCommand.WX_UPDATE_TOKEN_RESP) {
            WxUpdateTokenResp wxUpdateTokenResp = (WxUpdateTokenResp) body;
            getFileLogUtils().write(String.format(Locale.getDefault(), "WX_UPDATE_TOKEN_RESP(761): %s", wxUpdateTokenResp.toString()));
        } else if (command == TioCommand.WX_GROUP_MSG_RESP) {
            WxGroupMsgResp wxGroupMsgResp = (WxGroupMsgResp) body;
            getFileLogUtils().write(String.format(Locale.getDefault(), "WX_GROUP_MSG_RESP(621): %s", wxGroupMsgResp.toString()));
        } else if (command == TioCommand.WX_FRIEND_MSG_RESP) {
            WxFriendMsgResp wxFriendMsgResp = (WxFriendMsgResp) body;
            getFileLogUtils().write(String.format(Locale.getDefault(), "WX_FRIEND_MSG_RESP(605): %s", wxFriendMsgResp.toString()));
        }
    }

    private FileLogUtils getFileLogUtils() {
        return FileLogUtils.getInstance().setFileName(FILE_LOG_NAME);
    }

    @Override
    protected void onNetworkStatusChangedListener_onDisconnected() {
        super.onNetworkStatusChangedListener_onDisconnected();
        getFileLogUtils().write("onNetworkStatusChangedListener_onDisconnected");
    }

    @Override
    protected void onNetworkStatusChangedListener_onConnected(NetworkUtils.NetworkType networkType) {
        super.onNetworkStatusChangedListener_onConnected(networkType);
        getFileLogUtils().write("onNetworkStatusChangedListener_onConnected: " + networkType);
    }

    @Override
    protected void onAppStatusChangedListener_onBackground(Activity activity) {
        super.onAppStatusChangedListener_onBackground(activity);
        getFileLogUtils().write("onAppStatusChangedListener_onBackground: " + activity);
    }

    @Override
    protected void onAppStatusChangedListener_onForeground(Activity activity) {
        super.onAppStatusChangedListener_onForeground(activity);
        getFileLogUtils().write("onAppStatusChangedListener_onForeground: " + activity);
    }
}
