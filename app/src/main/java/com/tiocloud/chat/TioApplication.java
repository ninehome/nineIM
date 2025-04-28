package com.tiocloud.chat;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;

import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.BuildConfig;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ProcessUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.tiocloud.account.TioAccount;
import com.tiocloud.account.mvp.logout.LogoutPresenter;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.main.MainActivity;
import com.tiocloud.chat.feature.splash.SplashActivity;
import com.tiocloud.chat.feature.webrtc.CallActivity;
import com.tiocloud.chat.feature.webrtc.data.CallNtf;
import com.tiocloud.chat.mvp.card.CardContract;
import com.tiocloud.chat.mvp.card.CardPresenter;
import com.tiocloud.chat.util.CrashLogUtils;
import com.tiocloud.chat.util.MultiLanguageService;
import com.tiocloud.chat.widget.YxRefreshLayout;
import com.tiocloud.chat.yanxun.map.helper.BdLocationHelper;
import com.tiocloud.chat.yanxun.map.helper.MapHelper;
import com.tiocloud.jpush.PushLauncher;
import com.watayouxiang.androidutils.AndroidUtils;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.androidutils.widget.dialog.confirm.SingletonConfirmDialog;
import com.watayouxiang.androidutils.yanxun.ConstantUtils;
import com.watayouxiang.db.TioDBHelper;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.prefernces.HttpPreferences;
import com.watayouxiang.httpclient.utils.Constants;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.model.DeviceType;
import com.watayouxiang.imclient.model.body.webrtc.WxCall02Ntf;
import com.watayouxiang.qrcode.QRCodeBridge;
import com.watayouxiang.qrcode.TioQRCode;
import com.watayouxiang.webrtclib.TioWebRTC;
import com.watayouxiang.webrtclib.listener.OnSimpleRTCListener;

import java.util.List;
import java.util.Locale;

public class TioApplication extends Application {
    private static TioApplication INSTANCE = null;

    @Override
    public void onCreate() {
        super.onCreate();

//        ToastUtils.showShort("当前语言："+Locale.getDefault().getDisplayName());
        Locale locale = getResources().getConfiguration().locale;
        if (locale.getLanguage().startsWith("vi")){
            TioMap.currentLau = "vi";
        }
        INSTANCE = this;
        init(this);
        ConstantUtils.context = this;
        Constants.context = this;
        com.watayouxiang.imclient.utils.ConstantUtils.context = this;
//        ToastUtils.showShort(getString(R.string.find_pwd));
    }

    public static TioApplication getInstance() {
        return INSTANCE;
    }

    private void init(Application app) {
        // 多进程APP，当非主进程时，无需初始化
        if (!ProcessUtils.isMainProcess()) {
            return;
        }
        // 配置 baseUrl
        HttpPreferences.saveBaseUrl(TioConfig.BASE_URL_ONLINE);
        // 账号模块
        TioAccount.init(MainActivity::start);
//        if (TioConfig.needOnkeyLogin){
//            TioAccount.initUmVerify(this, TioConfig.um_appkey, TioConfig.um_secry);
//        }
        // 二维码模块
        initQRCodeModule();
        // 数据库
        TioDBHelper.init(app);
        // AndroidUtils
        AndroidUtils.init(app);
        //初始化地图
        initMap();
        // jpush
//        PushLauncher.getInstance().init(app);
//        PushLauncher.getInstance().setOnPushListener(context -> MainActivity.start(context, 0));
        // 踢出登录
        TioIMClient.getInstance().setKickOutListener(this::handleKickOut);
        TioHttpClient.getInstance().getRespInterceptor().setKickOutListener(this::handleKickOut);
        // WebRTC 来电监听
        setWebRtcListener();
        // 崩溃日志
        CrashLogUtils.getInstance().listener();
        CrashLogUtils.getInstance().upload();
        // debug
        try {
            if (BuildConfig.DEBUG) {
                Class.forName("com.tiocloud.chat.test.debug.DebugUtils");
            }
        } catch (ClassNotFoundException ignore) {
        }
        TioHttpClient.getInstance().setDebug(true);
        TioIMClient.setDebug(true);
        TioWebRTC.setDebug(true);
        TioLogger.setIsLogEnable(true);


        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                MyActivityManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

        //初始化刷新控件
//        YxRefreshLayout.init();
        MultiLanguageService.INSTANCE.init(this);
    }

    private void setWebRtcListener() {
        TioWebRTC.getInstance().setOnGlobalRTCListener(new OnSimpleRTCListener() {
            @Override
            public void onCall(WxCall02Ntf call) {
                super.onCall(call);

                DeviceType fromDevice = DeviceType.from((byte) call.fromdevice);
                if (call.fromuid == call.touid && fromDevice == DeviceType.ANDROID) {
                    return;
                }
                CallNtf callNtf = new CallNtf(call.fromuid, call.type);
                // 如果App在后台，那么来电不给响应
                if (!AppUtils.isAppForeground()) {
//                    ToastUtils.showShort("唤醒APP");
//                    setTopApp(Utils.getApp(), callNtf);

                    addNotify("[通话邀请]","新的通话邀请，点击查看", callNtf);

                    return;
                }
                CallActivity.start(Utils.getApp(), callNtf);
            }
        });
    }





    /**
     * 将本应用置顶到最前端
     * 当本应用位于后台时，则将它切换到最前端
     *
     * @param context
     */
    public static void setTopApp(Context context) {
//        addNotify("通话", "", null);

//        Intent fullScreenIntent = new Intent(context, CallActivity.class);
//        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
//                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(context, "新消息通知")
//                        .setSmallIcon(R.mipmap.ic_launcher_new)
//                        .setContentTitle("Incoming call")
//                        .setContentText("(919) 555-1234")
//                        .setPriority(NotificationCompat.PRIORITY_HIGH)
//                        .setCategory(NotificationCompat.CATEGORY_CALL)
//
//                        // Use a full-screen intent only for the highest-priority alerts where you
//                        // have an associated activity that you would like to launch after the user
//                        // interacts with the notification. Also, if your app targets Android 10
//                        // or higher, you need to request the USE_FULL_SCREEN_INTENT permission in
//                        // order for the platform to invoke this notification.
//                        .setFullScreenIntent(fullScreenPendingIntent, true);
//
//        Notification incomingCallNotification = notificationBuilder.build();
//
//        ((NotificationManager) INSTANCE.getSystemService(NOTIFICATION_SERVICE)).notify(9999, incomingCallNotification);
//
//        /**获取ActivityManager*/
//        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
//
//        /**获得当前运行的task(任务)*/
//        List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(100);
//        for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
//            /**找到本应用的 task，并将它切换到前台*/
//            if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
//                activityManager.moveTaskToFront(taskInfo.id, ActivityManager.MOVE_TASK_WITH_HOME);
//                break;
//            }
//        }

//        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(20);
//        /**枚举进程*/
//
//        for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
//            //*找到本应用的 task，并将它切换到前台
//            if (taskInfo.baseActivity.getPackageName().equals(context.getPackageName())) {
//                Log.e("timerTask", "timerTask  pid " + taskInfo.id);
//                Log.e("timerTask", "timerTask  processName " + taskInfo.topActivity.getPackageName());
//                Log.e("timerTask", "timerTask  getPackageName " + context.getPackageName());
//                activityManager.moveTaskToFront(taskInfo.id, ActivityManager.MOVE_TASK_WITH_HOME);
//                Intent intent = new Intent(context, SplashActivity.class);
//                intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                intent.setAction(Intent.ACTION_MAIN);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                context.startActivity(intent);
//                break;
//            }
//        }

//        Intent intent = new Intent("android.intent.action.MAIN");
//        intent.setComponent(new ComponentName(INSTANCE.getPackageName(), MainActivity.class.getName()));
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        INSTANCE.startActivity(intent);



    }
    static NotificationManager notificationManager;
    private static void addNotify(String title, String msg, CallNtf callNtf) {
        String CHANNEL_ONE_ID = "新消息通知";
        NotificationChannel notificationChannel = null;
        Intent intent = new Intent(INSTANCE, MainActivity.class);
        intent.putExtra("callNtf", callNtf);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.context, 9999, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(INSTANCE, CHANNEL_ONE_ID)
                .setTicker("Nature")
                .setSmallIcon(R.mipmap.ic_launcher_new)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_new))
                .setAutoCancel(true)
                .setSubText(title)
                .setContentTitle(msg)
                .setContentIntent(pendingIntent);
//                .setFullScreenIntent(pendingIntent, true);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        if (notificationManager == null) {
            notificationManager = (NotificationManager) INSTANCE.getSystemService(NOTIFICATION_SERVICE);
        }
//        startForeground(2, builder.build());
        notificationManager.notify(9999, builder.build());
    }

    private void handleKickOut(String msg) {
        LogoutPresenter.clearLoginInfo();

        Activity activity = ActivityUtils.getTopActivity();
        if (activity == null) {
            LogoutPresenter.openLoginClearOthers();
            return;
        }

        new SingletonConfirmDialog.ShowHelper(activity)
                .setMessage("当前账号已在其他设备登录")
                .setCancelable(false)
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setOnConfirmListener((view, dialog) -> {
                    dialog.dismiss();
                    LogoutPresenter.openLoginClearOthers();
                })
                .show();
    }

    private void initQRCodeModule() {
        TioQRCode.init(new QRCodeBridge() {
            private final CardPresenter cardPresenter = new CardPresenter();

            @Override
            public void openP2PCard(Context context, String uid) {
                cardPresenter.openP2PCard(context, uid);
            }

            @Override
            public void openGroupCard(Context context, String groupId, String shareFromUid, OpenGroupCardListener listener) {
                cardPresenter.openGroupCard(context, groupId, shareFromUid, new CardContract.OpenGroupCardListener() {
                    @Override
                    public void onOpenGroupCardSuccess() {
                        listener.onOpenGroupCardSuccess();
                    }

                    @Override
                    public void onOpenGroupCardError() {
                        listener.onOpenGroupCardError();
                    }
                });
            }
        });
    }
    private BdLocationHelper mBdLocationHelper;
    public BdLocationHelper getBdLocationHelper() {
        if (mBdLocationHelper == null) {
            mBdLocationHelper = new BdLocationHelper(this);
        }
        return mBdLocationHelper;
    }
    private void initMap() {
        MapHelper.initContext(this);
        // 默认为百度地图，
//        PrivacySetting privacySetting = PrivacySettingHelper.getPrivacySettings(this);
//        boolean isGoogleMap = privacySetting.getIsUseGoogleMap() == 1;
//        if (isGoogleMap) {
//            MapHelper.setMapType(MapHelper.MapType.GOOGLE);
//        } else {
//        }
        MapHelper.setMapType(MapHelper.MapType.BAIDU);
    }
    /**
     * 在程序内部关闭时，调用此方法
     */
    public void destory() {
        // 结束百度定位
        if (mBdLocationHelper != null) {
            mBdLocationHelper.release();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void destoryRestart() {
        // 结束百度定位
        if (mBdLocationHelper != null) {
            mBdLocationHelper.release();
        }
    }
}
