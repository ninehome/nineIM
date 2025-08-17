package com.watayouxiang.androidutils.engine;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.maning.imagebrowserlibrary.ImageEngine;

/**
 * author : TaoWang
 * date : 2020/4/9
 * desc : 搭配 https://github.com/maning0303/MNImageBrowser
 */
class MNImageBrowserEngine implements ImageEngine {
    @Override
    public void loadImage(Context context, String url, ImageView imageView, final View progressView) {
        progressView.setVisibility(View.VISIBLE);
        Glide.with(context).load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // 隐藏进度View,必须设置setCustomProgressViewLayoutID
                        progressView.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // 隐藏进度View,必须设置setCustomProgressViewLayoutID
                        progressView.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }
}
