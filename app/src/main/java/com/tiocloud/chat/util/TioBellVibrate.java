package com.tiocloud.chat.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.VibrateUtils;
import com.watayouxiang.androidutils.util.RingerModeUtils;
import com.watayouxiang.audiorecord.WtMediaPlayer;
import com.watayouxiang.db.prefernces.TioDBPreferences;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/06/17
 *     desc   :
 * </pre>
 */
public class TioBellVibrate {
    private static final String MP3_DIR = "mp3/";

    @NonNull
    private final WtMediaPlayer mPlayer = new WtMediaPlayer();

    private static class InstanceHolder {
        private final static TioBellVibrate holder = new TioBellVibrate();
    }

    public static TioBellVibrate getInstance() {
        return InstanceHolder.holder;
    }

    private TioBellVibrate() {
        mPlayer.setAudioFocusFlag(true);
    }

    public void start(@NonNull Bell bell) {
        stop();

        // 手机模式
        int ringerMode = RingerModeUtils.getRingerMode();

        // 播放音频
        if (!TioDBPreferences.getCurrNoDisturb().equals("1") && TioDBPreferences.getCurrShake().equals("1")){
            if (ringerMode == RingerModeUtils.RingerMode.NORMAL) {
                if (bell.mp3File != null) {
                    mPlayer.initAssert(MP3_DIR + bell.mp3File);
                    mPlayer.setLooping(bell.looping);
                    mPlayer.start();
                }
            }
        }

        // 开始震动
        if (!TioDBPreferences.getCurrNoDisturb().equals("1") && TioDBPreferences.getCurrVoice().equals("1")){
            if (ringerMode == RingerModeUtils.RingerMode.NORMAL ||
                    ringerMode == RingerModeUtils.RingerMode.VIBRATE) {
                if (bell.vibratePattern != null) {
                    VibrateUtils.vibrate(bell.vibratePattern, bell.vibrateRepeat);
                }
            }
        }
    }

    public void stop() {
        // 停止播放
        mPlayer.stop();
        // 停止震动
        VibrateUtils.cancel();
    }

    public enum Bell {
        CALL_NTF("bell_call_ntf.mp3", true, new long[]{0, 1000, 1000, 1000, 1000, 1000, 1000}, 0),
        CALL_END("bell_call_end.mp3", false, new long[]{500, 500}, -1),
        MSG_NTF_P2P("bell_p2p_msg_ntf.mp3", false, new long[]{300, 500}, -1),
        MSG_NTF_GROUP("bell_group_msg_ntf.mp3", false, new long[]{200, 500}, -1);

        public @Nullable
        String mp3File;
        public boolean looping;
        public @Nullable
        long[] vibratePattern;
        public int vibrateRepeat;

        Bell(@Nullable String mp3File, boolean looping, @Nullable long[] vibratePattern, int vibrateRepeat) {
            this.mp3File = mp3File;
            this.looping = looping;
            this.vibratePattern = vibratePattern;
            this.vibrateRepeat = vibrateRepeat;
        }
    }
}
