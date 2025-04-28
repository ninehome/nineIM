package com.tiocloud.chat.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.Utils;

import java.lang.reflect.Field;

public class ScreenUtil {
    public static float density;
    public static float scaleDensity;

    public static int screenWidth;
    public static int screenHeight;
    public static int screenMin;// 宽高中，小的一边
    public static int screenMax;// 宽高中，较大的值

    public static float xdpi;
    public static float ydpi;
    public static int densityDpi;

    static {
        init(Utils.getApp());
    }

    private static void init(Context context) {
        if (null == context) {
            return;
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        density = dm.density;
        scaleDensity = dm.scaledDensity;

        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        screenMin = Math.min(screenWidth, screenHeight);
        screenMax = Math.max(screenWidth, screenHeight);

        xdpi = dm.xdpi;
        ydpi = dm.ydpi;
        densityDpi = dm.densityDpi;
    }

    public static int dp2px(float dipValue) {
        return (int) (dipValue * density + 0.5f);
    }

    public static int px2dp(float pxValue) {
        return (int) (pxValue / density + 0.5f);
    }

    public static int sp2px(float spValue) {
        return (int) (spValue * scaleDensity + 0.5f);
    }

    public static int px2sp(float pxValue) {
        return (int) (pxValue / scaleDensity + 0.5f);
    }

    /**
     * 对话框宽度
     */
    public static int getDialogWidth() {
        final double RATIO = 0.85;
        return (int) (screenMin * RATIO);
    }

    /**
     * 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (statusBarHeight == 0) {
            statusBarHeight = ScreenUtil.dp2px(25);
        }
        return statusBarHeight;
    }

    /**
     * 导航栏高度
     */
    public static int getNavBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 设置 view margin
     */
    public static void setMargins(View v, int left, int top, int right, int bottom) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            v.requestLayout();
        }
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return bp
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        if (bmp == null) {
            return null;
        }
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, bmp.getWidth(), bmp.getHeight() - statusBarHeight);
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);

        return bp;
    }
}
