package com.tiocloud.chat.widget.emotion;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.tiocloud.chat.R;
import com.tiocloud.chat.util.EmojiManager;

public class EmoticonViewPaperAdapter extends PagerAdapter {
    private final ViewPager emotionPager;
    private final EmoticonSelectedListener listener;

    private EmotionType emotionType;

    public EmoticonViewPaperAdapter(EmoticonSelectedListener listener, ViewPager emotionPager) {
        this.listener = listener;
        this.emotionPager = emotionPager;
    }

    public boolean setEmotionType(EmotionType emotionType) {
        if (this.emotionType == emotionType) {
            return false;
        }
        this.emotionType = emotionType;
        notifyDataSetChanged();
        return true;
    }

    @Override
    public int getCount() {
        if (emotionType == EmotionType.EMOJI) {
            return (int) Math.ceil(EmojiManager.getDisplayCount() / (float) EmojiGridViewAdapter.PER_PAGE_EMOJI_COUNT);
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (emotionType == EmotionType.EMOJI) {
            GridView gridView = new GridView(container.getContext());
            gridView.setOnItemClickListener(emojiGridViewListener);
            gridView.setAdapter(new EmojiGridViewAdapter(container.getContext(), position));
            gridView.setNumColumns(EmojiGridViewAdapter.GRID_VIEW_COLUMNS);
            gridView.setHorizontalSpacing(0);
            gridView.setVerticalSpacing(0);
            gridView.setGravity(Gravity.CENTER);
            gridView.setSelector(R.drawable.emoji_item_selector);
            container.addView(gridView);
            return gridView;
        } else {
            return super.instantiateItem(container, position);
        }
    }

    private AdapterView.OnItemClickListener emojiGridViewListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int vpPosition = emotionPager.getCurrentItem();
            int emojiIndex = vpPosition * EmojiGridViewAdapter.PER_PAGE_EMOJI_COUNT + position;
            boolean isDel = position == EmojiGridViewAdapter.PER_PAGE_EMOJI_COUNT
                    || emojiIndex == EmojiManager.getDisplayCount();

            if (isDel) {
                if (listener != null) {
                    listener.onEmojiSelected(EmojiGridViewAdapter.KEY_DEL);
                }
            } else {
                if (listener != null) {
                    String text = EmojiManager.getDisplayText(emojiIndex);
                    if (!TextUtils.isEmpty(text)) {
                        listener.onEmojiSelected(text);
                    }
                }
            }
        }
    };

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        View layout = (View) object;
        container.removeView(layout);
    }

    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}