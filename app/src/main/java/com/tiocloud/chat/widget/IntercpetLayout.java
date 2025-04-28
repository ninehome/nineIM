package com.tiocloud.chat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class IntercpetLayout extends RelativeLayout {

    private boolean isIntercept;

    public IntercpetLayout(Context context) {
        super(context);
    }

    public IntercpetLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IntercpetLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public IntercpetLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isIntercept){
            return isIntercept;
        }
        return super.onInterceptTouchEvent(ev);
    }


    public void setIsIntercept(boolean isIntercept){
        this.isIntercept = isIntercept;
    }
}
