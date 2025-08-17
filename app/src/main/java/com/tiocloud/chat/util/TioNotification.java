package com.tiocloud.chat.util;

import android.app.Activity;
import android.app.PendingIntent;

import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.NotificationUtils;
import com.blankj.utilcode.util.Utils;
import com.tiocloud.chat.R;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/06/18
 *     desc   :
 * </pre>
 */
public class TioNotification {
    private int notifyId = 0;

    private static class InstanceHolder {
        private static TioNotification holder = new TioNotification();
    }

    public static TioNotification getInstance() {
        return InstanceHolder.holder;
    }

    private TioNotification() {
    }

    public void notification(Activity activity, CharSequence title, CharSequence text) {
        NotificationUtils.notify(notifyId++, new Utils.Consumer<NotificationCompat.Builder>() {
            @Override
            public void accept(NotificationCompat.Builder builder) {
                builder.setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setContentIntent(PendingIntent.getActivity(activity, 0, activity.getIntent(), PendingIntent.FLAG_UPDATE_CURRENT))
                        .setAutoCancel(true);
            }
        });
    }

    public void cancelAll() {
        NotificationUtils.cancelAll();
        notifyId = 0;
    }
}
