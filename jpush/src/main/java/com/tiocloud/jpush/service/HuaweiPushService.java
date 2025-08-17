package com.tiocloud.jpush.service;

import android.content.Intent;
import android.os.IBinder;

import com.huawei.hms.push.RemoteMessage;
import com.tiocloud.jpush.utils.LogUtils;

import cn.jpush.android.service.PluginHuaweiPlatformsService;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/29
 *     desc   : 华为推送服务
 * </pre>
 */
public class HuaweiPushService extends PluginHuaweiPlatformsService {

    private final static String TAG = "[HuaweiPushService]";

    // ====================================================================================
    // PluginHuaweiPlatformsService
    // ====================================================================================

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        LogUtils.d(TAG + " onNewToken: " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        LogUtils.d(TAG + " onMessageReceived");
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        LogUtils.d(TAG + " onMessageSent");
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
        LogUtils.d(TAG + " onSendError");
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        LogUtils.d(TAG + " onDeletedMessages");
    }

    // ====================================================================================
    // HmsMessageService
    // ====================================================================================

    @Override
    public void onMessageDelivered(String s, Exception e) {
        super.onMessageDelivered(s, e);
        LogUtils.d(TAG + " onMessageDelivered");
    }

    @Override
    public void onTokenError(Exception e) {
        super.onTokenError(e);
        LogUtils.d(TAG + " onTokenError");
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.d(TAG + " onBind");
        return super.onBind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int i, int i1) {
        LogUtils.d(TAG + " onStartCommand");
        return super.onStartCommand(intent, i, i1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG + " onDestroy");
    }

}
