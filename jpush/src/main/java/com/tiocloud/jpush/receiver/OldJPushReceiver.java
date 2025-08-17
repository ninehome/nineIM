package com.tiocloud.jpush.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tiocloud.jpush.utils.LogUtils;

import java.util.Locale;

import cn.jpush.android.api.JPushInterface;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/25
 *     desc   : 自定义接收器（老版本）
 *
 *      如果不定义这个 Receiver，则：
 *      1) 默认用户会打开主界面
 *      2) 接收不到自定义消息
 * </pre>
 */
public class OldJPushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();

            // SDK 向 JPush Server 注册所得到的注册 ID。
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);

                LogUtils.d("[OldJPushReceiver] 接收 RegistrationId: " + regId);

            }
            // 收到了自定义消息 Push。
            else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                String title = bundle.getString(JPushInterface.EXTRA_TITLE);
                String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);

                LogUtils.d(String.format(Locale.getDefault(),
                        "[OldJPushReceiver] 接收到推送下来的自定义消息: title = %s, message = %s, extras = %s, msgId = %s",
                        title, message, extras, msgId));

            }
            // 收到了通知 Push。
            else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                // 对应 Portal 推送通知界面上的“通知标题”字段。
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                // 对应 Portal 推送通知界面上的“通知内容”字段
                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
                // 对应 Portal 推送消息界面上的“可选设置”里的附加字段。
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                // 如果服务端内容（alert）字段为空，则 Notification ID 为 0
                int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                // 富媒体通知推送下载的 HTML 的文件路径，用于展现 WebView。
                String fileHtml = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_PATH);
                // 富媒体通知推送下载的图片资源的文件名，多个文件名用 “，” 分开。
                // 与 “JPushInterface.EXTRA_RICHPUSH_HTML_PATH” 位于同一个路径
                String fileStr = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_RES);
                if (fileStr == null) fileStr = "";
                String[] fileNames = fileStr.split(",");
                // 唯一标识通知消息的 ID，可用于上报统计等
                String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
                // 大文本通知样式中大文本的内容
                String bigText = bundle.getString(JPushInterface.EXTRA_BIG_TEXT);
                // 大图片通知样式中大图片的路径/地址
                String bigPicPath = bundle.getString(JPushInterface.EXTRA_BIG_PIC_PATH);
                // 收件箱通知样式中收件箱的内容
                String inboxJson = bundle.getString(JPushInterface.EXTRA_INBOX);
                // 通知的优先级
                String priority = bundle.getString(JPushInterface.EXTRA_NOTI_PRIORITY);
                // 通知分类
                String notifyCategory = bundle.getString(JPushInterface.EXTRA_NOTI_CATEGORY);

                LogUtils.d("[OldJPushReceiver] 接收到推送下来的通知");

            }
            // 用户点击了通知
            else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                // 通知标题
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                // 通知内容
                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
                // “可选设置”里的附加字段
                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
                // 通知栏的 Notification ID，可以用于清除 Notification
                int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                // 唯一标识调整消息的 ID，可用于上报统计等
                String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);

                LogUtils.d("[OldJPushReceiver] 用户点击打开了通知");

            }
            // 用户点击了通知栏中自定义的按钮
            else if (JPushInterface.ACTION_NOTIFICATION_CLICK_ACTION.equals(intent.getAction())) {
                LogUtils.d("[OldJPushReceiver] 用户点击了通知栏按钮");
                String nActionExtra = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA);

                // 开发者根据不同 Action 携带的 extra 字段来分配不同的动作。
                if (nActionExtra == null) {
                    LogUtils.d("ACTION_NOTIFICATION_CLICK_ACTION nActionExtra is null");
                    return;
                }
                if (nActionExtra.equals("my_extra1")) {
                    LogUtils.d("[OldJPushReceiver] 用户点击通知栏按钮一");
                } else if (nActionExtra.equals("my_extra2")) {
                    LogUtils.d("[OldJPushReceiver] 用户点击通知栏按钮二");
                } else if (nActionExtra.equals("my_extra3")) {
                    LogUtils.d("[OldJPushReceiver] 用户点击通知栏按钮三");
                } else {
                    LogUtils.d("[OldJPushReceiver] 用户点击通知栏按钮未定义");
                }
            }
            // JPush 服务的连接状态发生变化
            else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                LogUtils.d("[OldJPushReceiver] JPush 服务的连接状态发生变化: " + connected);
            }
            // 其他行为
            else {
                LogUtils.d("[OldJPushReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }
}