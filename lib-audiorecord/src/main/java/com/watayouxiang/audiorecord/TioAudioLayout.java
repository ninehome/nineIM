package com.watayouxiang.audiorecord;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Chronometer;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;

import com.watayouxiang.audiorecord.databinding.WtTioAudioLayoutBinding;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/07
 *     desc   :
 *     {@link Chronometer}参考: https://juejin.im/post/6844903656337178631
 * </pre>
 */
public class TioAudioLayout extends RelativeLayout {

    private WtTioAudioLayoutBinding binding;
    private boolean isCountDown;

    public TioAudioLayout(Context context) {
        this(context, null);
    }

    public TioAudioLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TioAudioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wt_tio_audio_layout, this, true);
        stop();
    }

    public void start() {
        start(0);
    }

    /**
     * @param millisecond 起始时间，值必须大于等于0，单位毫秒。
     *                    比如：希望从5秒开始计时，那么就是5000
     */
    public void start(long millisecond) {
        setVisibility(VISIBLE);
        binding.clCancel.setVisibility(GONE);
        binding.clNormal.setVisibility(VISIBLE);

        if (!isCountDown) {
            isCountDown = true;
            millisecond = millisecond >= 0 ? millisecond : 0;
            binding.timer.setBase(SystemClock.elapsedRealtime() - millisecond);
            binding.timer.start();
            binding.gifImage.load(R.drawable.gif_voice_input, true);
        }
    }

    public void stop() {
        setVisibility(GONE);

        if (isCountDown) {
            isCountDown = false;
            binding.timer.stop();
            binding.timer.setBase(SystemClock.elapsedRealtime());
            binding.gifImage.load(R.drawable.gif_voice_input, false);
        }
    }

    public void cancel() {
        setVisibility(VISIBLE);
        binding.clNormal.setVisibility(GONE);
        binding.clCancel.setVisibility(VISIBLE);
    }
}
