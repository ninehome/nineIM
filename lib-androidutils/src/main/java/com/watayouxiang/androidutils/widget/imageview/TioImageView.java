package com.watayouxiang.androidutils.widget.imageview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SizeUtils;
import com.watayouxiang.httpclient.prefernces.HttpCache;

/**
 * author : TaoWang
 * date : 2020-01-22
 * desc :
 * https://frescolib.org/docs/using-simpledraweeview.html
 */
public class TioImageView extends WtImageView {

    public TioImageView(Context context) {
        super(context);
    }

    public TioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TioImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected String getResUrl(String url) {
        return HttpCache.getResUrl(url);
    }

    /**
     * 消息内页 - 名片
     */
    public void load_msg_card(@Nullable String url) {
        // 加载静态图片
        loadStatic(url);
        // 设置圆角
        setRoundRadius(4);
        // 设置边框
        setBorder(Color.WHITE, 1);
    }

    /**
     * 消息内页 - 图片
     */
    public void load_msg_pic(@Nullable String url, int width, int height) {
        // 加载图片
        load(url, true);
//        loadStatic(url);
        // 设置圆角
        setRoundRadius(14);
        // 设置宽高
        setWH(width, height, 1.3f, SizeUtils.dp2px(160));
        // 设置边框
        setBorder(Color.WHITE, 1);
    }

    public void load_msg_pic_limit(@Nullable String url, int width, int height) {
        // 加载图片
        load(url, true);
//        loadStatic(url);
        // 设置圆角
        setRoundRadius(14);
        // 设置宽高
        setWH(width, height, 1f, SizeUtils.dp2px(160));
        // 设置边框
        setBorder(Color.WHITE, 1);
    }

    /**
     * tio - 圆角头像
     */
    public void tio_roundAvatar(@Nullable String url) {
        // 加载静态图片
        loadStatic(url);
        // 圆角
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params.width != params.height) {
            return;
        }
//        int dpWH = SizeUtils.px2dp(params.width);
//        switch (dpWH) {
//            case 38:
//            case 42:
//            case 44:
//            case 48:
//            case 58:
//                setRoundRadius(4);
//                break;
//            case 67:
//                setRoundRadius(8);
//                break;
//            case 77:
//                setRoundRadius(8);
//                setBorder(Color.WHITE, 4);
//                break;
//        }
    }

    public void loadStatic(@Nullable String url, float radius) {
        super.loadStatic(url);
        super.setRoundRadius(radius);
    }

    public void loadBorderCircle(@Nullable String url, int color, float width) {
        super.loadStatic(url);
        super.setRoundCircle();
        super.setBorder(color, width);
    }
}
