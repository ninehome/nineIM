package com.tiocloud.jpush.receiver;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;

import com.tiocloud.jpush.PushLauncher;
import com.tiocloud.jpush.listener.OnPushListener;
import com.tiocloud.jpush.utils.LogUtils;

import java.util.Locale;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/9/25
 *     desc   : 自定义接收器（新版本）
 * </pre>
 */
public class NewJPushReceiver extends JPushMessageReceiver {
    /**
     * 收到自定义消息回调
     *
     * @param context
     * @param customMessage
     */
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);
        LogUtils.d("[NewJPushReceiver] onMessage " + customMessage.toString());
    }

    /**
     * 收到通知回调
     *
     * @param context
     * @param notificationMessage
     */
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        LogUtils.d("[NewJPushReceiver] onNotifyMessageArrived " + notificationMessage.toString());
        // todo 收到通知
        String notificationTitle = notificationMessage.notificationTitle;
        String notificationContent = notificationMessage.notificationContent;

        LogUtils.d(String.format(Locale.getDefault(),
                "[NewJPushReceiver] onNotifyMessageArrived: title = %s, content = %s",
                notificationTitle, notificationContent));

    }

    /**
     * 点击通知回调
     *
     * @param context
     * @param notificationMessage
     */
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        LogUtils.d("[NewJPushReceiver] onNotifyMessageOpened " + notificationMessage.toString());

        // TODO: 2020/9/28 点击通知栏
        OnPushListener onPushListener = PushLauncher.getInstance().getOnPushListener();
        if (onPushListener != null) {
            onPushListener.onNotificationClick(context);
        }

//        try {
//            //打开自定义的Activity
//            Intent i = new Intent(context, TestActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString(JPushInterface.EXTRA_NOTIFICATION_TITLE, message.notificationTitle);
//            bundle.putString(JPushInterface.EXTRA_ALERT, message.notificationContent);
//            i.putExtras(bundle);
//            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            context.startActivity(i);
//        } catch (Throwable throwable) {
//
//        }
    }

    /**
     * 清除通知回调
     *
     * @param context
     * @param notificationMessage
     */
    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageDismiss(context, notificationMessage);
        LogUtils.d("[NewJPushReceiver] onNotifyMessageDismiss " + notificationMessage.toString());
    }

    /**
     * 通知未展示的回调
     *
     * @param context
     * @param notificationMessage
     */
    @Override
    public void onNotifyMessageUnShow(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageUnShow(context, notificationMessage);
        LogUtils.d("[NewJPushReceiver] onNotifyMessageUnShow " + notificationMessage.toString());
    }

    /**
     * 注册成功回调
     *
     * @param context
     * @param registrationId
     */
    @Override
    public void onRegister(Context context, String registrationId) {
        super.onRegister(context, registrationId);
        LogUtils.d("[NewJPushReceiver] onRegister registrationId = " + registrationId);
    }

    /**
     * 长连接状态回调
     *
     * @param context
     * @param isConnected
     */
    @Override
    public void onConnected(Context context, boolean isConnected) {
        super.onConnected(context, isConnected);
        LogUtils.d("[NewJPushReceiver] onConnected isConnected = " + isConnected);
    }

    /**
     * 注册失败 + 三方厂商注册回调
     *
     * @param context
     * @param cmdMessage
     */
    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        super.onCommandResult(context, cmdMessage);
        LogUtils.d("[NewJPushReceiver] onCommandResult " + cmdMessage.toString());

        // cmd为10000时说明为厂商token回调
        if (cmdMessage.cmd == 10000 && cmdMessage.extra != null) {
            String token = cmdMessage.extra.getString("token");
            int platform = cmdMessage.extra.getInt("platform");
            String deviceName = "unkown";
            switch (platform) {
                case 1:
                    deviceName = "小米";
                    break;
                case 2:
                    deviceName = "华为";
                    break;
                case 3:
                    deviceName = "魅族";
                    break;
                case 4:
                    deviceName = "OPPO";
                    break;
                case 5:
                    deviceName = "VIVO";
                    break;
                case 6:
                    deviceName = "ASUS";
                    break;
                case 8:
                    deviceName = "FCM";
                    break;
            }
            LogUtils.d("获取到 " + deviceName + " 的token:" + token);
        }
    }

    /**
     * 通知的MultiAction回调
     *
     * @param context
     * @param intent
     */
    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        super.onMultiActionClicked(context, intent);
        LogUtils.d("[NewJPushReceiver] onMultiActionClicked " + intent.toString());
    }

    /**
     * 通知开关的回调
     *
     * @param context
     * @param isOn
     * @param source
     */
    @Override
    public void onNotificationSettingsCheck(Context context, boolean isOn, int source) {
        super.onNotificationSettingsCheck(context, isOn, source);
        LogUtils.d("[NewJPushReceiver] onNotificationSettingsCheck");
    }

    /**
     * tag 增删查改的操作会在此方法中回调结果。
     *
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onTagOperatorResult(context, jPushMessage);
        LogUtils.d("[NewJPushReceiver] onTagOperatorResult");
    }

    /**
     * 查询某个 tag 与当前用户的绑定状态的操作会在此方法中回调结果。
     *
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onCheckTagOperatorResult(context, jPushMessage);
        LogUtils.d("[NewJPushReceiver] onCheckTagOperatorResult");
    }

    /**
     * alias 相关的操作会在此方法中回调结果。
     *
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
        LogUtils.d("[NewJPushReceiver] onAliasOperatorResult");
    }

    /**
     * 设置手机号码会在此方法中回调结果。
     *
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onMobileNumberOperatorResult(context, jPushMessage);
        LogUtils.d("[NewJPushReceiver] onMobileNumberOperatorResult");
    }

    @Override
    public Notification getNotification(Context context, NotificationMessage notificationMessage) {
        LogUtils.d("[NewJPushReceiver] getNotification");
        return super.getNotification(context, notificationMessage);
    }

    @Override
    public boolean isNeedShowNotification(Context context, NotificationMessage notificationMessage, String s) {
        LogUtils.d("[NewJPushReceiver] isNeedShowNotification");
        return super.isNeedShowNotification(context, notificationMessage, s);
    }

    @Override
    public void onInAppMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onInAppMessageArrived(context, notificationMessage);
        LogUtils.d("[NewJPushReceiver] onInAppMessageArrived");
    }

    @Override
    public void onInAppMessageClick(Context context, NotificationMessage notificationMessage) {
        super.onInAppMessageClick(context, notificationMessage);
        LogUtils.d("[NewJPushReceiver] onInAppMessageClick");
    }

    @Override
    public void onInAppMessageDismiss(Context context, NotificationMessage notificationMessage) {
        super.onInAppMessageDismiss(context, notificationMessage);
        LogUtils.d("[NewJPushReceiver] onInAppMessageDismiss");
    }

    @Override
    public void onInAppMessageUnShow(Context context, NotificationMessage notificationMessage) {
        super.onInAppMessageUnShow(context, notificationMessage);
        LogUtils.d("[NewJPushReceiver] onInAppMessageUnShow");
    }

    @Override
    public boolean isNeedShowInAppMessage(Context context, NotificationMessage notificationMessage, String s) {
        LogUtils.d("[NewJPushReceiver] isNeedShowInAppMessage");
        return super.isNeedShowInAppMessage(context, notificationMessage, s);
    }

    @Override
    public boolean onSspNotificationWillShow(Context context, NotificationMessage notificationMessage, String s) {
        LogUtils.d("[NewJPushReceiver] onSspNotificationWillShow");
        return super.onSspNotificationWillShow(context, notificationMessage, s);
    }

    @Override
    public void onPullInAppResult(Context context, JPushMessage jPushMessage) {
        super.onPullInAppResult(context, jPushMessage);
        LogUtils.d("[NewJPushReceiver] onPullInAppResult");
    }
}
