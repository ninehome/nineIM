package com.tiocloud.chat.widget.emotion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.util.EmojiManager;

public class EmojiGridViewAdapter extends BaseAdapter {

    public static final String KEY_DEL = "/DEL";
    public static final int PER_PAGE_EMOJI_COUNT = 27;
    public static final int PER_PAGE_DEL_COUNT = 1;

    public static final int GRID_VIEW_COLUMNS = 7;
    public static final int GRID_VIEW_ITEM_COUNT = PER_PAGE_EMOJI_COUNT + PER_PAGE_DEL_COUNT;

    private final Context context;
    private final int vpPosition;

    public EmojiGridViewAdapter(Context context, int vpPosition) {
        this.context = context;
        this.vpPosition = vpPosition;
    }

    @Override
    public int getCount() {
        int emojiCount = EmojiManager.getDisplayCount() - vpPosition * PER_PAGE_EMOJI_COUNT;
        emojiCount = Math.min(emojiCount, EmojiGridViewAdapter.PER_PAGE_EMOJI_COUNT);
        return emojiCount + PER_PAGE_DEL_COUNT;
    }

    @Override
    public Drawable getItem(int position) {
        int emojiCount = EmojiManager.getDisplayCount();
        int emojiIndex = vpPosition * PER_PAGE_EMOJI_COUNT + position;
        boolean isDel = position == PER_PAGE_EMOJI_COUNT || emojiIndex == emojiCount;

        return isDel ? context.getResources().getDrawable(R.drawable.emoji_del) :
                EmojiManager.getDisplayDrawable(context, emojiIndex);
    }

    @Override
    public long getItemId(int position) {
        return vpPosition + position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.emoji_item, null);
        }

        final View finalConvertView = convertView;
        parent.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = finalConvertView.getLayoutParams();
                layoutParams.height = parent.getContext().getResources().getDimensionPixelOffset(R.dimen.dp_200)
                        / (GRID_VIEW_ITEM_COUNT / GRID_VIEW_COLUMNS);
                finalConvertView.setLayoutParams(layoutParams);
            }
        });

        ((ImageView) convertView.findViewById(R.id.imgEmoji)).setBackgroundDrawable(getItem(position));
        return convertView;
    }

}