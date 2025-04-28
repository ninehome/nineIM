package com.watayouxiang.audiorecord;

import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/07
 *     desc   :
 * </pre>
 */
public class TioAudioBubbleUtils {
    /**
     * 设置语音气泡宽
     *
     * @param bubble
     * @param seconds
     */
    public static void setAudioBubbleWidth(ViewGroup bubble, int seconds) {
        int currentBubbleWidth = TioAudioBubbleUtils.calculateBubbleWidth(seconds);
        ViewGroup.LayoutParams layoutParams = bubble.getLayoutParams();
        layoutParams.width = currentBubbleWidth;
        bubble.setLayoutParams(layoutParams);
    }

    /**
     * 计算语音气泡的宽度
     *
     * @param seconds
     * @return
     */
    public static int calculateBubbleWidth(long seconds) {
        int maxAudioBubbleWidth = getAudioMaxEdge();
        int minAudioBubbleWidth = getAudioMinEdge();

        int currentBubbleWidth;
        if (seconds <= 0) {
            currentBubbleWidth = minAudioBubbleWidth;
        } else if (seconds <= TioAudioRecorder.MAX_RECORD_SECOND) {
            currentBubbleWidth = (int) ((maxAudioBubbleWidth - minAudioBubbleWidth) * (2.0 / Math.PI)
                    * Math.atan(seconds / 10.0) + minAudioBubbleWidth);
        } else {
            currentBubbleWidth = maxAudioBubbleWidth;
        }

        if (currentBubbleWidth < minAudioBubbleWidth) {
            currentBubbleWidth = minAudioBubbleWidth;
        } else if (currentBubbleWidth > maxAudioBubbleWidth) {
            currentBubbleWidth = maxAudioBubbleWidth;
        }

        return currentBubbleWidth;
    }

    private static int getAudioMaxEdge() {
        return (int) (0.6 * getScreenMin());
    }

    private static int getAudioMinEdge() {
        return (int) (/*0.1875*/0.1 * getScreenMin());
    }

    private static double getScreenMin() {
        int height = ScreenUtils.getScreenHeight();
        int width = ScreenUtils.getScreenWidth();
        return Math.min(height, width);
    }
}
