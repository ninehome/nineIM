package com.tiocloud.chat.feature.webrtc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.TouchUtils;

import org.webrtc.SurfaceViewRenderer;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/28
 *     desc   : 支持手势拖动的 SurfaceView
 * </pre>
 */
class LocalSurfaceView extends SurfaceViewRenderer {
    public LocalSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public LocalSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        TouchUtils.setOnTouchListener(this, new TouchUtils.OnTouchUtilsListener() {

            private int rootViewWidth;
            private int rootViewHeight;
            private int viewWidth;
            private int viewHeight;
            private int statusBarHeight;

            @Override
            public boolean onDown(View view, int x, int y, MotionEvent event) {
                viewWidth = view.getWidth();
                viewHeight = view.getHeight();
                View contentView = view.getRootView().findViewById(android.R.id.content);
                rootViewWidth = contentView.getWidth();
                rootViewHeight = contentView.getHeight();
                statusBarHeight = BarUtils.getStatusBarHeight();

                processScale(view, true);
                return true;
            }

            @Override
            public boolean onMove(View view, int direction, int x, int y, int dx, int dy, int totalX, int totalY, MotionEvent event) {
                view.setX(Math.min(Math.max(0, view.getX() + dx), rootViewWidth - viewWidth));
                view.setY(Math.min(Math.max(statusBarHeight, view.getY() + dy), rootViewHeight - viewHeight));
                return true;
            }

            @Override
            public boolean onStop(View view, int direction, int x, int y, int totalX, int totalY, int vx, int vy, MotionEvent event) {
                stick2HorizontalSide(view);
                processScale(view, false);
                return true;
            }

            private void stick2HorizontalSide(View view) {
                view.animate()
                        .setInterpolator(new DecelerateInterpolator())
                        .translationX(view.getX() + viewWidth / 2f > rootViewWidth / 2f ? rootViewWidth - viewWidth : 0)
                        .setDuration(100)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                //savePosition();
                            }
                        })
                        .start();
            }

            private void processScale(final View view, boolean isDown) {
                float value = isDown ? 1 - 0.1f : 1;
                view.animate()
                        .scaleX(value)
                        .scaleY(value)
                        .setDuration(100)
                        .start();
            }
        });
    }
}
