package com.tiocloud.chat.feature.main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.databinding.TioMainActivityBinding;
import com.tiocloud.chat.feature.main.adapter.MainTabPagerAdapter;
import com.tiocloud.chat.feature.main.model.MainTab;
import com.tiocloud.chat.feature.main.mvp.MainContract;
import com.tiocloud.chat.feature.main.mvp.MainPresenter;
import com.tiocloud.chat.feature.session.group.GroupSessionActivity;
import com.tiocloud.chat.feature.session.p2p.P2PSessionActivity;
import com.tiocloud.chat.feature.webrtc.CallActivity;
import com.tiocloud.chat.feature.webrtc.data.CallNtf;
import com.tiocloud.chat.service.LiveSerice;
import com.tiocloud.chat.util.AndroidBug5497Workaround;
import com.tiocloud.chat.util.PreferencesUtil;
import com.tiocloud.chat.util.SoftKeyboardFixerForFullscreen;

import com.tiocloud.jpush.PushLauncher;
import com.tiocloud.jpush.utils.LogUtils;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.dialog.oper.EasyOperDialog;
import com.watayouxiang.androidutils.widget.dialog.oper.TioOperDialog;
import com.watayouxiang.db.dao.GroupMsgTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.engine.TioEventEngine;
import com.watayouxiang.imclient.event.NoticeClearBean;
import com.watayouxiang.imclient.model.body.wx.WxFriendChatNtf;
import com.watayouxiang.imclient.model.body.wx.WxGroupChatNtf;
import com.watayouxiang.imclient.model.body.wx.WxGroupOperNtf;
import com.watayouxiang.imclient.model.body.wx.internal.ChatItems;
import com.watayouxiang.wallet.TioWallet;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * author : TaoWang
 * date : 2019-12-31
 * desc :
 */
public class MainActivity extends TioActivity implements MainContract.View {

    public MainPresenter presenter;
    private TioMainActivityBinding binding;

    public static Context context;

    public static void start(Context context) {
        MainActivity.start(context, null);
    }

    /**
     * @param tabIndex 再次打开，希望所处的页签索引
     */
    public static void start(Context context, int tabIndex) {
        Intent intent = new Intent();
        intent.putExtra(TioExtras.EXTRA_TAB_INDEX, tabIndex);
        MainActivity.start(context, intent);
    }

    @Override
    public boolean getNeedSetStatusBar() {
        return true;
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void goToSet(){
//        if (isNotificationEnabled(this)){
//            return;
//        }
        try {
            // 根据通知栏开启权限判断结果，判断是否需要提醒用户跳转系统通知管理页面
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, getString(R.string.new_message_inform));
            //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
            intent.putExtra("app_package", getPackageName());
            intent.putExtra("app_uid", getString(R.string.new_message_inform));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常则跳转到应用设置界面
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setTranslucent(this);
        TioIMClient.getInstance().getEventEngine().register(this);
        binding = DataBindingUtil.setContentView(this, R.layout.tio_main_activity);
        presenter = new MainPresenter(this);
        presenter.init();
        LogUtils.d("zlb启动保活服务");
//        AndroidBug5497Workaround.assistActivity(this);
        SoftKeyboardFixerForFullscreen.assistActivity(this);
        context = this;
//
        if (PreferencesUtil.getBoolean("msg_notice", true)){
            LiveSerice.start(this);
        }
//        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        LogUtils.d("channel.getImportance()==>"+mNotificationManager.getNotificationChannel("新消息通知").getImportance());
        if (TioConfig.OpenCloseConfig.needPush() && PreferencesUtil.getBoolean("enter_first", true)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    PreferencesUtil.saveBoolean("enter_first", false);
                    new EasyOperDialog.Builder(getString(R.string.app_background_tip))
                            .setNegativeBtnTxt(getString(R.string.notips))
                            .setPositiveBtnTxt(getString(R.string.tips))
                            .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                                @Override
                                public void onClickPositive(View view, EasyOperDialog dialog) {
                                    goToSet();
                                    PreferencesUtil.saveBoolean("msg_notice", true);
                                    dialog.dismiss();
                                }

                                @Override
                                public void onClickNegative(View view, EasyOperDialog dialog) {
                                    PreferencesUtil.saveBoolean("msg_notice", false);
                                    dialog.dismiss();
                                }
                            })
                            .build().show_unCancel(MainActivity.this);
                }
            }, 1000);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 再次打开，希望所处的页签索引
        int tabIndex = intent.getIntExtra(TioExtras.EXTRA_TAB_INDEX, -1);
        if (tabIndex != -1) {
            binding.viewPager.setCurrentItem(tabIndex);
        }
        if (intent.hasExtra("callNtf")){
            CallNtf callNtf = (CallNtf) intent.getSerializableExtra("callNtf");
            CallActivity.start(Utils.getApp(), callNtf);
            return;
        }
        if (intent.hasExtra("chatLinkId")){
            String chatLinkId = intent.getStringExtra("chatLinkId");
            if (TextUtils.isEmpty(chatLinkId)){
                return;
            }
            if (Integer.parseInt(chatLinkId) > 0){
                P2PSessionActivity.enter(this, chatLinkId);
            }else {
                GroupSessionActivity.enter(this, chatLinkId);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 检测更新
        presenter.checkAppUpdate();
        // 清除所有通知
        presenter.clearAllNotifications();
        TioWallet.KEY = TioConfig.HAND_SHAKE_KEY_ONLINE;

        new TioEventEngine().post(new NoticeClearBean(false));

        if (PreferencesUtil.getBoolean("msg_notice", true)){
            LiveSerice.start(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        binding.unbind();
        TioIMClient.getInstance().getEventEngine().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        int currentItem = binding.viewPager.getCurrentItem();
//        if (currentItem == 2){
//
//        }
//
        // 不关闭应用，返回主界面
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public MainActivity getMainActivity() {
        return this;
    }

    @Override
    public void initViews() {
        // 初始化 pager
        MainTabPagerAdapter adapter = new MainTabPagerAdapter(getSupportFragmentManager(), binding.viewPager, this);
        binding.viewPager.setCurrentItem(MainTab.CHAT.tabIndex, false);
        // 初始化 tab
        binding.tabStrip.setViewPager(binding.viewPager);
        binding.tabStrip.setOnTabClickListener(adapter);
        binding.tabStrip.setOnTabDoubleTapListener(adapter);
        binding.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                hideInput();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void updateRedDot(int pageIndex, int count) {
        if (binding == null) return;
        binding.tabStrip.updateTab(pageIndex, count);

        // 设置角标数字
        if (pageIndex == MainTab.CHAT.tabIndex) {
            PushLauncher.getInstance().setBadgeNumber(count);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2){
            if (binding == null) return;
            binding.tabStrip.setCurrentPosition(0);
        }else if (requestCode == 10) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
//                    Toast.makeText(MainActivity.this, "not granted", Toast.LENGTH_SHORT).show();
                }else {
                    startService(new Intent(this, LiveSerice.class));
                }

            }
        }
    }
    private int count = 1;
    NotificationManager notificationManager;
    private NoticeClearBean noticeClearBean = new NoticeClearBean(false);
    private void addNotify(String title, String msg, String chatLinkId){
        if (!TioConfig.OpenCloseConfig.needPush()){
            return;
        }
        if (AppUtils.isAppForeground()){
            LogUtils.d("zlb===>app在前台，不通知");
            return;
        }
        String CHANNEL_ONE_ID = getString(R.string.new_message_inform);
        NotificationChannel notificationChannel= null;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("chatLinkId", chatLinkId);
        PendingIntent pendingIntent= PendingIntent.getActivity(this, ++count, intent, PendingIntent.FLAG_CANCEL_CURRENT);
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
//        startForeground(2, builder.build());
        notificationManager.notify(count, builder.build());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxGroupChatNtf(WxGroupChatNtf wxGroupChatNtf) {
        if (!TioConfig.OpenCloseConfig.needPush()){
            return;
        }
        if (wxGroupChatNtf.sendbysys == 1){
            return;
        }
        if (!noticeClearBean.isShowNotice()){
            return;
        }
        if (!PreferencesUtil.getBoolean("msg_notice", true)){
            return;
        }
        addNotify(getString(R.string.from_group_member)+wxGroupChatNtf.nick, wxGroupChatNtf.getShowContent(), wxGroupChatNtf.chatlinkid);
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
        if (!TioConfig.OpenCloseConfig.needPush()){
            return;
        }
//        ToastUtils.showShort("1111111111111");
        if (wxFriendChatNtf.sendbysys == 1){
            return;
        }
        if (!noticeClearBean.isShowNotice()){
            LogUtils.d("zlb不显示通知");
            return;
        }
        if (!PreferencesUtil.getBoolean("msg_notice", true)){
            LogUtils.d("zlb不显示通知2");
            return;
        }
//        getView().getMsgListProxy().sendMsg(new P2PMsg(wxFriendChatNtf, currUid, currNick));
        addNotify(getString(R.string.from_friend)+wxFriendChatNtf.nick, wxFriendChatNtf.getShowContent(), wxFriendChatNtf.chatlinkid);
    }

//    // 群操作通知
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onWxGroupOperNtf(WxGroupOperNtf ntf) {
//        WxGroupOperNtf.Oper oper = WxGroupOperNtf.Oper.valueOf(ntf.oper);
//        if (oper == null) return;
//        switch (oper) {
//            case BACK_MSG:
//            case DEL_MSG:
//                GroupMsgTableCrud.deleteMsg(ntf.chatlinkid, ntf.bizdata);
//                break;
//        }
//    }
}
