package com.watayouxiang.imclient.packet;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.Nullable;

import com.watayouxiang.imclient.model.DeviceType;
import com.watayouxiang.imclient.model.body.HandshakeReq;
import com.watayouxiang.imclient.utils.DeviceUtils;
import com.watayouxiang.imclient.utils.MD5Utils;

public class TioBodyBuilder {
    /**
     * 握手请求
     *
     * @param context      Context
     * @param activity     Activity
     * @param cid          渠道
     * @param token        客户端通过http登录后，服务器返回给客户端的token值，没登录则为空串
     * @param handshakeKey 握手密钥
     * @param jpushinfo    极光推送 registerId
     * @return 消息体
     */
    public static HandshakeReq getHandshakeReq(Context context, Activity activity, String cid, String token, String handshakeKey, @Nullable String jpushinfo) {
        String imei = null;
        String appVersion = null;
        String resolution = null;
        String operator = null;
        String size = null;
        Context ctx = context != null ? context : activity;
        if (ctx != null) {
            imei = DeviceUtils.getImei(ctx);
            appVersion = DeviceUtils.getAppVersion(ctx);
            resolution = DeviceUtils.getResolution(ctx);
            operator = DeviceUtils.getOperator(ctx);
        }
        if (activity != null) {
            size = DeviceUtils.getSize(activity);
        }
        //mobileInfo
        HandshakeReq.MobileInfo info = new HandshakeReq.MobileInfo();
        info.devicetype = DeviceType.ANDROID.getValue();
        info.deviceinfo = DeviceUtils.getDeviceInfo();
        info.imei = imei;
        info.appversion = appVersion;
        info.cid = cid;
        info.resolution = resolution;
        info.size = size;
        info.operator = operator;
        //handshakeReq
        HandshakeReq handshakeReq = new HandshakeReq();
        handshakeReq.token = token;
        handshakeReq.devicetype = DeviceType.ANDROID.getValue();
        handshakeReq.mobileInfo = info;
        handshakeReq.sign =
                //sign = Md5(token+imei+deviceinfo+devicetype+cid+密钥)
                MD5Utils.getMd5(handshakeReq.token
                        + info.imei
                        + info.deviceinfo
                        + info.devicetype
                        + info.cid
                        + handshakeKey);
        // 极光推送 registerId
        handshakeReq.jpushinfo = jpushinfo;
        return handshakeReq;
    }
}
