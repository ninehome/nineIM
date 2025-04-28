package com.tiocloud.chat.widget.popupwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.IdRes;

import com.tiocloud.chat.R;

/**
 * author : TaoWang
 * date : 2020-02-19
 * desc :
 * https://www.runoob.com/w3cnote/android-tutorial-popupwindow.html
 */
abstract class BasePopupWindow extends PopupWindow {

    private final View anchor;

    public BasePopupWindow(View anchor) {
        this.anchor = anchor;
        setContentView(LayoutInflater.from(anchor.getContext()).inflate(getViewLayout(), null));
        initViews();
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        setTouchable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        setBackgroundDrawable(new ColorDrawable(0x00000000));//要为popWindow设置一个背景才有效
    }

    protected abstract int getViewLayout();

    protected abstract void initViews();

    public abstract void show();

    // ====================================================================================
    // 拓展方法
    // ====================================================================================

    protected final <T extends View> T findViewById(@IdRes int id) {
        View view = getContentView();
        if (view == null)
            throw new NullPointerException("rootView is null");
        return view.findViewById(id);
    }

    public View getAnchor() {
        return anchor;
    }

    public Activity getActivity() {
        Context context = anchor.getContext();
        if (!(context instanceof Activity)) {
            throw new NullPointerException("anchor context not Activity");
        }
        return (Activity) context;
    }

    /**
     * 遮罩
     *
     * @param alpha 遮罩透明底
     */
    public void bgAlpha(float alpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = alpha;// 0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    /**
     * 显示在锚点的 "中下位置"
     */
    protected void showAnchorCenterDown() {
        // window 宽高
        View window = getContentView();
        window.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int windowHeight = window.getMeasuredHeight();
        int windowWidth = window.getMeasuredWidth();
        // 获取 Anchor 宽高
        int anchorWidth = getAnchor().getWidth();
        int anchorHeight = getAnchor().getHeight();
        // 获取 Anchor 坐标
        int[] location = new int[2];
        getAnchor().getLocationOnScreen(location);
        int anchorX = location[0];
        int anchorY = location[1];
        // 计算 Anchor 中心点坐标
        int anchorCenterX = anchorX + anchorWidth / 2;
        int anchorCenterY = anchorY + anchorHeight / 2;
        // 显示
        int showX = anchorCenterX - windowWidth / 2;
        int showY = anchorCenterY;
        super.showAtLocation(getAnchor(), Gravity.NO_GRAVITY, showX, showY);
    }

    private Drawable originalBackground;

    protected void setAnchorBg() {
        originalBackground = getAnchor().getBackground();
        getAnchor().setBackground(new ColorDrawable(getAnchor().getResources().getColor(R.color.gray_e6e6e6)));
    }

    protected void cancelAnchorBg() {
        getAnchor().setBackground(originalBackground);
    }
}
