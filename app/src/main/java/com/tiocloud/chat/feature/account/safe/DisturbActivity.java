package com.tiocloud.chat.feature.account.safe;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.UpdateRemindReq;

public class DisturbActivity extends TioActivity {

    private WtTitleBar titleBar;
    private CheckBox no_disturb;
    private CheckBox switch_new_information;
    private CheckBox switch_voice;
    private CheckBox switch_shake;
    private RelativeLayout rl_voice;
    private RelativeLayout rl_shake;
    private RelativeLayout rl_new_information;

    public static void startActivity(Context context){
        Intent intent = new Intent(context, DisturbActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_disturb_activity);
        initView();
    }

    private void initView(){
        titleBar = findViewById(R.id.titleBar);
        rl_voice = findViewById(R.id.rl_voice);
        rl_shake = findViewById(R.id.rl_shake);
        rl_new_information = findViewById(R.id.rl_new_information);
        no_disturb = findViewById(R.id.no_disturb);
        switch_new_information = findViewById(R.id.switch_new_information);
        switch_voice = findViewById(R.id.switch_voice);
        switch_shake = findViewById(R.id.switch_shake);
        titleBar.setTitle(getString(R.string.mian_disturb));
        no_disturb.setChecked(TioDBPreferences.getCurrNoDisturb().equals("1"));
        if(TioDBPreferences.getCurrNoDisturb().equals("1")){
            rl_shake.setVisibility(View.GONE);
            rl_voice.setVisibility(View.GONE);
            rl_new_information.setVisibility(View.GONE);
        }else {
            rl_shake.setVisibility(View.VISIBLE);
            rl_voice.setVisibility(View.VISIBLE);
            rl_new_information.setVisibility(View.VISIBLE);
        }
        no_disturb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
//                    vibrate(DisturbActivity.this,1000);
                    TioDBPreferences.saveCurrNoDisturb("1");
//                    switch_voice.setChecked(false);
//                    switch_shake.setChecked(false);
                    rl_shake.setVisibility(View.GONE);
                    rl_voice.setVisibility(View.GONE);
                    rl_new_information.setVisibility(View.GONE);
                }else {
                    rl_shake.setVisibility(View.VISIBLE);
                    rl_voice.setVisibility(View.VISIBLE);
                    rl_new_information.setVisibility(View.VISIBLE);
                    TioDBPreferences.saveCurrNoDisturb("0");
                }
            }
        });

        rl_new_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSet();
            }
        });

        switch_new_information.setChecked(CurrUserTableCrud.curr_isOpenMsgRemind(true));
        switch_new_information.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateMsgRemindFlagReq(isChecked,switch_new_information);
            }
        });
        switch_shake.setChecked(TioDBPreferences.getCurrShake().equals("1"));
        switch_shake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
//                    vibrate(DisturbActivity.this,1000);
                    TioDBPreferences.saveCurrShake("1");
                }else {
                    virateCancle(DisturbActivity.this);
                    TioDBPreferences.saveCurrShake("0");
                }
            }
        });
        switch_voice.setChecked(TioDBPreferences.getCurrVoice().equals("1"));
        switch_voice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
//                    playRing(DisturbActivity.this);
                    TioDBPreferences.saveCurrVoice("1");
                }else {
                    stopRing();
                    TioDBPreferences.saveCurrVoice("0");
                }
            }
        });
    }

    private void updateMsgRemindFlagReq(boolean isChecked, CheckBox switch_msgRemind) {
        // 消息提醒开关
        UpdateRemindReq updateRemindReq = new UpdateRemindReq(isChecked ? "1" : "2");
        updateRemindReq.setCancelTag(this);
        updateRemindReq.post(new TioCallback<Void>() {
            @Override
            public void onTioSuccess(Void aVoid) {
                try {
                    int remindflag = Integer.parseInt(updateRemindReq.getRemindflag());
                    // 更新当前用户的消息开关状态
                    CurrUserTableCrud.curr_update_msgremindflag(remindflag);
                } catch (Exception ignored) {
                }
            }

            @Override
            public void onTioError(String msg) {
                switch_msgRemind.setChecked(!isChecked);
            }
        });
    }

    //震动milliseconds毫秒
    public void vibrate(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    //取消震动
    public void virateCancle(final Activity activity){
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.cancel();
    }

    MediaPlayer mMediaPlayer;
    //开始播放
    public void playRing(final Activity activity){
        try {
            Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);//用于获取手机默认铃声的Uri
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(activity, alert);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);//告诉mediaPlayer播放的是铃声流
            mMediaPlayer.setLooping(true);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //停止播放
    public void stopRing(){
        if (mMediaPlayer!=null){
            if (mMediaPlayer.isPlaying()){
                mMediaPlayer.stop();
                mMediaPlayer.release();
            }
        }
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
}
