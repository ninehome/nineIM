package com.watayouxiang.imclient.packet;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.watayouxiang.imclient.model.body.HandshakeReq;

public class TioHandshake {
    // 客户端通过http登录后，服务器返回给客户端的token值
    private String token;
    // 握手请求密钥
    private String handshakeKey;
    // 渠道
    private String cid;
    // 命令码
    private short command;
    // 极光推送 registerId
    private String jpushinfo;

    private Context context;
    private Activity activity;

    private TioHandshake(String token,
                         String handshakeKey,
                         Activity activity,
                         String cid,
                         Context context,
                         short command,
                         String jpushinfo) {
        this.token = token;
        this.handshakeKey = handshakeKey;
        this.activity = activity;
        this.cid = cid;
        this.context = context;
        this.command = command;
        this.jpushinfo = jpushinfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(@NonNull String token) {
        this.token = token;
    }

    public String getHandshakeKey() {
        return handshakeKey;
    }

    public Activity getActivity() {
        return activity;
    }

    public String getCid() {
        return cid;
    }

    public Context getContext() {
        return context;
    }

    public short getCommand() {
        return command;
    }

    public TioPacket getPacket() {
        HandshakeReq handshakeReq = TioBodyBuilder.getHandshakeReq(context, activity, cid, token, handshakeKey, jpushinfo);
        return TioPacketBuilder.getPacket(handshakeReq, command);
    }

    @Override
    public String toString() {
        return "TioHandshake{" +
                "token='" + token + '\'' +
                ", handshakeKey='" + handshakeKey + '\'' +
                ", cid='" + cid + '\'' +
                ", command=" + command +
                ", jpushinfo='" + jpushinfo + '\'' +
                ", context=" + context +
                ", activity=" + activity +
                '}';
    }

    public static class Builder {
        private String token;
        private String handshakeKey;
        private Activity activity;
        private String cid;
        private Context context;
        private short command;
        private String jpushinfo;

        public Builder(String token, String handshakeKey, short command) {
            this.token = token;
            this.handshakeKey = handshakeKey;
            this.command = command;
        }

        public Builder setActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder setCid(String cid) {
            this.cid = cid;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setJpushinfo(String jpushinfo) {
            this.jpushinfo = jpushinfo;
            return this;
        }

        public TioHandshake build() {
            return new TioHandshake(token,
                    handshakeKey,
                    activity,
                    cid,
                    context,
                    command,
                    jpushinfo);
        }
    }
}
