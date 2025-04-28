package com.tiocloud.jpush;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.NotificationUtils;
import com.tiocloud.jpush.listener.OnPushListener;
import com.tiocloud.jpush.utils.LogUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/25
 *     desc   :
 * </pre>
 */
public class PushLauncher {
    private Application mContext;
    private OnPushListener onPushListener;

    private static class Holder {
        private static final PushLauncher mHolder = new PushLauncher();
    }

    public static PushLauncher getInstance() {
        return Holder.mHolder;
    }

    private PushLauncher() {
    }

    /**
     * JPush 推送服务进行初始化
     * <p>
     * 建议在自定义的 Application 中的 onCreate 中调用
     */
    public PushLauncher init(@NonNull Application context) {
        mContext = context;
        // 该接口需在 init 接口之前调用，避免出现部分日志没打印的情况。
        // log TAG: jiguang
        JPushInterface.setDebugMode(true);
        // 初始化 SDK
        JPushInterface.init(mContext);
        // 最多显示的条数
        JPushInterface.setLatestNotificationNumber(mContext, 5);
        return this;
    }

    /**
     * 设置通道
     *
     * @param channel 通道
     */
    public PushLauncher setChannel(@Nullable String channel) {
        // 动态配置 channel
        // 动态配置 channel，优先级比 AndroidManifest 里配置的高
        // 传 null 表示依然使用 AndroidManifest 里配置的 channel
        if (mContext != null) {
            JPushInterface.setChannel(mContext, channel);
        }
        return this;
    }

    /**
     * 获取 registrationId
     */
    public String getRegistrationID() {
        if (mContext != null) {
            return JPushInterface.getRegistrationID(mContext);
        }
        return null;
    }

    /**
     * 设置角标数字(目前仅支持华为手机)
     *
     * @param num 新的角标数字，传入负数将会修正为0
     */
    public void setBadgeNumber(int num) {
        if (mContext != null) {
            JPushInterface.setBadgeNumber(mContext, num);
            LogUtils.d("设置角标：" + num);
        }
    }

    /**
     * 设置推送监听
     */
    public void setOnPushListener(OnPushListener onPushListener) {
        this.onPushListener = onPushListener;
    }

    public OnPushListener getOnPushListener() {
        return onPushListener;
    }

    /**
     * 清除所有 JPush 展现的通知（不包括非 JPush SDK 展现的）
     */
    public void clearAllNotifications() {
        if (mContext != null) {
            //JPushInterface.clearAllNotifications(mContext);
            NotificationUtils.cancelAll();
        }
    }

    /**
     * 清除通知
     *
     * @param notificationId 通知id
     */
    public void clearNotificationById(int notificationId) {
        if (mContext != null) {
            JPushInterface.clearNotificationById(mContext, notificationId);
        }
    }

    /**
     * 停止推送服务
     * <p>
     * 不能通过 JPushInterface.init 恢复，需要调用 resumePush 恢复
     */
    @Deprecated
    private void stopPush() {
        if (mContext != null) {
            JPushInterface.stopPush(mContext);
        }
    }

    /**
     * 恢复推送服务
     */
    @Deprecated
    private void resumePush() {
        if (mContext != null) {
            JPushInterface.resumePush(mContext);
        }
    }

    /**
     * 检查 Push Service 是否已经被停止
     */
    @Deprecated
    public boolean isPushStopped() {
        if (mContext != null) {
            return JPushInterface.isPushStopped(mContext);
        }
        return true;
    }
}
