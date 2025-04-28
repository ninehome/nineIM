package com.tiocloud.chat.widget.emotion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.tiocloud.chat.R;

import co.ceryle.fitgridview.FitGridAdapter;
import co.ceryle.fitgridview.FitGridView;

public class EmojiPager2Adapter extends PagerAdapter {
    private int[][] idMatrix;
    // 表情符号所代表的英文字符
    private String[][] strMatrix;
    private EmoticonSelectedListener listener;
    private Context ctx;

    public EmojiPager2Adapter(Context ctx, int[][] idMatrix, String[][] strMatrix, EmoticonSelectedListener listener) {
        this.ctx = ctx;
        this.idMatrix = idMatrix;
        this.strMatrix = strMatrix;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return idMatrix.length;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int pagePosition) {
        int[] idList = idMatrix[pagePosition];
        String[] strList = strMatrix[pagePosition];
        FitGridView gridView = (FitGridView) LayoutInflater.from(ctx).inflate(R.layout.chat_face_gridview, container, false);
        container.addView(gridView);
        gridView.setSelector(R.drawable.chat_face_bg);
        gridView.setFitGridAdapter(new GifAdapter(ctx, idList));
        gridView.setOnItemClickListener((parent, view, itemPosition, id) -> {
            if (listener != null) {
                String text = strList[itemPosition];
                listener.onGifFaceClick(text);
            }
        });
        return gridView;
    }

    static class GifAdapter extends FitGridAdapter {
        private final Context ctx;
        private final int[] idList;

        GifAdapter(Context ctx, int[] idList) {
            super(ctx, R.layout.item_face_gif);
            this.ctx = ctx;
            this.idList = idList;
        }

        @Override
        public int getCount() {
            return idList.length;
        }

        @Override
        public Object getItem(int position) {
            return idList[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void onBindView(int position, View view) {
            ImageView ivEmoji = (ImageView) view;
            int res = idList[position];
            ivEmoji.setImageResource(res);
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
