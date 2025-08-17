package com.tiocloud.chat.widget.emotion;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.widget.emotion.emoji.EmojiView;

/**
 * author : TaoWang
 * date : 2019-12-27
 * desc : 表情选择控件（emoji/贴图）
 */
public class EmoticonPickerView extends LinearLayout {

    private View rootView;
    private EmoticonSelectedListener listener;
    private EmojiView emojiView;

    public EmoticonPickerView(Context context) {
        super(context);
        init(context);
    }

    public EmoticonPickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmoticonPickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.emoji_layout, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void show(EmoticonSelectedListener listener) {
        this.listener = listener;
        showEmojiView();
    }

    private void showEmojiView() {
        if (emojiView == null) {
            emojiView = new EmojiView(rootView, listener);
        }
        emojiView.show();
    }
}
