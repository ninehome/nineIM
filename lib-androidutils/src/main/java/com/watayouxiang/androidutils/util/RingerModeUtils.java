package com.watayouxiang.androidutils.util;

import android.content.Context;
import android.media.AudioManager;

import androidx.annotation.IntDef;

import com.blankj.utilcode.util.Utils;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/11
 *     desc   : 手机模式（静音、响铃、震动）
 * </pre>
 */
public class RingerModeUtils {
    @RingerMode
    public static int getRingerMode() {
        int ringerMode = -1;

        // 获取当前手机模式
        AudioManager audio = (AudioManager) Utils.getApp().getSystemService(Context.AUDIO_SERVICE);

        switch (audio.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:// 静音
                ringerMode = RingerMode.SILENT;
                break;
            case AudioManager.RINGER_MODE_NORMAL:// 响铃
                ringerMode = RingerMode.NORMAL;
                break;
            case AudioManager.RINGER_MODE_VIBRATE:// 震动
                ringerMode = RingerMode.VIBRATE;
                break;
        }

        return ringerMode;
    }

    @IntDef({RingerMode.SILENT, RingerMode.NORMAL, RingerMode.VIBRATE})
    public @interface RingerMode {
        int SILENT = 0;
        int NORMAL = 2;
        int VIBRATE = 1;
    }
}
