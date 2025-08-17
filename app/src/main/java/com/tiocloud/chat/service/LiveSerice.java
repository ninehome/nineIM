package com.tiocloud.chat.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.main.MainActivity;
import com.tiocloud.chat.util.PreferencesUtil;
import com.tiocloud.jpush.utils.LogUtils;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.event.NoticeClearBean;
import com.watayouxiang.imclient.model.body.wx.WxFriendChatNtf;
import com.watayouxiang.imclient.model.body.wx.WxGroupChatNtf;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LiveSerice extends Service {
    private int count=1;

    public static boolean isRunning = false;

    NotificationManager notificationManager;

    private NoticeClearBean noticeClearBean = new NoticeClearBean(false);


    public static void start(Context context){
        if (isRunning){
            LogUtils.d("zlb,LiveSerice正在运行");
            return;
        }
        if (true){
            return;
        }
        context.startService(new Intent(context, LiveSerice.class));
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        isRunning = true;
        super.onCreate();
        TioIMClient.getInstance().getEventEngine().register(this);
    }



    private void addNotify(String title, String msg, String chatLinkId){
        String CHANNEL_ONE_ID = getString(R.string.new_message_inform);
        String CHANNEL_ONE_NAME= getString(R.string.new_message_inform);
        NotificationChannel notificationChannel= null;
        //进行8.0的判断
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel= new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jianshu.com/p/14ba95c6c3e2"));
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("chatLinkId", chatLinkId);
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ONE_ID)
                .setTicker("Nature")
                .setSmallIcon(R.mipmap.ic_launcher_new)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_new))
                .setAutoCancel(true)
                .setSubText(title)
                .setContentTitle(msg)
                .setContentIntent(pendingIntent);
//                .setFullScreenIntent(pendingIntent, true)
        Notification notification = builder.build();
        notification.flags|= Notification.FLAG_NO_CLEAR;
        if (notificationManager == null){
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        startForeground(2, builder.build());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxGroupChatNtf(WxGroupChatNtf wxGroupChatNtf) {
        if (wxGroupChatNtf.sendbysys == 1){
            return;
        }
        if (!noticeClearBean.isShowNotice()){
            return;
        }
        if (!PreferencesUtil.getBoolean("msg_notice", true)){
            return;
        }
        addNotify("来自群成员："+wxGroupChatNtf.nick, wxGroupChatNtf.getShowContent(), wxGroupChatNtf.chatlinkid);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNoticeClearBean(NoticeClearBean noticeClearBean){
        this.noticeClearBean = noticeClearBean;
        if (noticeClearBean.isShowNotice()){

        }else {
//            addNotify("IM进程守护中", "");
            if (notificationManager != null){
                notificationManager.cancelAll();
            }
        }
    }

    // 私聊通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxFriendChatNtf(WxFriendChatNtf wxFriendChatNtf) {
        if (wxFriendChatNtf.sendbysys == 1){
            return;
        }
        if (!noticeClearBean.isShowNotice()){
            return;
        }
        if (!PreferencesUtil.getBoolean("msg_notice", true)){
            return;
        }
//        getView().getMsgListProxy().sendMsg(new P2PMsg(wxFriendChatNtf, currUid, currNick));
        addNotify(wxFriendChatNtf.nick, wxFriendChatNtf.getShowContent(), wxFriendChatNtf.chatlinkid);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TioIMClient.getInstance().getEventEngine().unregister(this);
        isRunning = false;
    }
}
