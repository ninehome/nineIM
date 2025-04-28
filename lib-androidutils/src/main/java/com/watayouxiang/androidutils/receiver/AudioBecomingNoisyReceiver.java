package com.watayouxiang.androidutils.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/06/18
 *     desc   : 耳机拨出广播
 *
 *     当用户拔出耳机的时候，很多程序会选择暂停播放，这个是通过响应AUDIO_BECOMING_NOISY 的intent来实现的。
 *     方法很简单，在你的程序中注册广播：
 *
 *     <receiver android:name=".MusicIntentReceiver">
 *       <intent-filter>
 *          <action android:name="android.media.AUDIO_BECOMING_NOISY" />
 *       </intent-filter>
 *     </receiver>
 *
 * </pre>
 */
public class AudioBecomingNoisyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
            // signal your service to stop playback
            // (via an Intent, for instance)
        }
    }
}
