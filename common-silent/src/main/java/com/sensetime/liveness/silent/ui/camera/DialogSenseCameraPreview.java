package com.sensetime.liveness.silent.ui.camera;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sensetime.senseid.sdk.liveness.silent.common.type.Size;

import java.io.IOException;

/**
 * Created on 2018/03/13.
 *
 * @author Zhu Xiangdong
 */
public class DialogSenseCameraPreview extends FrameLayout {

    public Rect scaledRect = null;

    private Context mContext;

    private SurfaceView mSurfaceView;

    private boolean mStartRequested;

    private boolean mSurfaceAvailable;

    private SenseCamera mCamera;

    private StartListener mStartListener;

    /**
     * SenseCameraPreview.
     *
     * @param context context.
     * @param attrs attrs.
     */
    public DialogSenseCameraPreview(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        this.mContext = context;
        this.mStartRequested = false;
        this.mSurfaceAvailable = false;

        this.mSurfaceView = new SurfaceView(context);
        this.mSurfaceView.getHolder().addCallback(new SurfaceCallback());
        addView(this.mSurfaceView);
    }


    @Override
    public void draw(Canvas canvas) {
        Path path = new Path();
        //设置裁剪的圆心，半径
        path.addCircle(this.getWidth()/2, this.getWidth()/2, this.getWidth()/2, Path.Direction.CCW);
        //裁剪画布，并设置其填充方式
        if(Build.VERSION.SDK_INT >= 26){
            canvas.clipPath(path);
        }else {
            canvas.clipPath(path, Region.Op.REPLACE);
        }
        super.draw(canvas);
    }



    public void setStartListener(final StartListener listener) {
        this.mStartListener = listener;
    }

    /**
     * start with SenseCamera.
     *
     * @param senseCamera senseCamera.
     */
    public void start(final SenseCamera senseCamera) throws IOException, RuntimeException {
        if (senseCamera == null) {
            this.stop();
        }

        this.mCamera = senseCamera;

        if (this.mCamera != null) {
            this.mStartRequested = true;
            this.startIfReady();
        }
    }

    /**
     * stop.
     */
    public void stop() {
        if (this.mCamera != null) {
            this.mCamera.stop();
        }
    }

    /**
     * release.
     */
    public void release() {
        if (this.mCamera != null) {
            this.mCamera.release();
            this.mCamera = null;
        }
    }

    private void startIfReady() throws IOException, RuntimeException {
        if (this.mStartRequested && this.mSurfaceAvailable) {
            this.mCamera.start(this.mSurfaceView.getHolder());
            this.requestLayout();
            this.mStartRequested = false;
        }
    }

//    @Override
//    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec) {
//        //获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
//        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
//        int layoutWidth = 0;
//        int layoutHeight = 0;
//        // 计算出所有的childView的宽和高
//        measureChildren(widthMeasureSpec, heightMeasureSpec);
//
//        int cWidth = 0;
//        int cHeight = 0;
//        int count = getChildCount();
//
//        if (widthMode == MeasureSpec.EXACTLY) {
//            //如果布局容器的宽度模式是确定的（具体的size或者match_parent），直接使用父窗体建议的宽度
//            layoutWidth = sizeWidth;
//        } else {
//            //如果是未指定或者wrap_content，我们都按照包裹内容做，宽度方向上只需要拿到所有子控件中宽度做大的作为布局宽度
//            for (int i = 0; i < count; i++) {
//                View child = getChildAt(i);
//                cWidth = child.getMeasuredWidth();
//                //获取子控件最大宽度
//                layoutWidth = cWidth > layoutWidth ? cWidth : layoutWidth;
//            }
//        }
//        //高度很宽度处理思想一样
//        if (heightMode == MeasureSpec.EXACTLY) {
//            layoutHeight = sizeHeight;
//        } else {
//            for (int i = 0; i < count; i++) {
//                View child = getChildAt(i);
//                cHeight = child.getMeasuredHeight();
//                layoutHeight = cHeight > layoutHeight ? cHeight : layoutHeight;
//            }
//        }
//
//        // 测量并保存layout的宽高
//        setMeasuredDimension(layoutWidth, layoutHeight);
//
//    }

//        @Override
//    protected void onLayout(final boolean changed, final int left, final int top, final int right, final int bottom) {
//        if (this.mCamera != null) {
//            Size size = this.mCamera.getPreviewSize();
//            if (size != null) {
//                int width = size.getWidth();
//                int height = size.getHeight();
//
//                if (this.isPortraitMode()) {
//                    int tmp = width;
//                    //noinspection SuspiciousNameCombination
//                    width = height;
//                    height = tmp;
//                }
//
//                final int layoutWidth = right - left;
//                final int layoutHeight = bottom - top;
//
//                int childWidth;
//                int childHeight;
//
//                final float layoutAspectRatio = layoutWidth / (float) layoutHeight;
//                final float cameraPreviewAspectRatio = width / (float) height;
//
//                if (Float.compare(layoutAspectRatio, cameraPreviewAspectRatio) <= 0) {
//                    childWidth = (int) (layoutHeight * cameraPreviewAspectRatio);
//                    childHeight = layoutHeight;
//                } else {
//                    childWidth = layoutWidth;
//                    childHeight = (int) (layoutWidth / cameraPreviewAspectRatio);
//                }
//
//                for (int i = 0; i < this.getChildCount(); ++i) {
//                    this.getChildAt(i).layout(0, 0, childWidth, childHeight);
//                }
//
//                try {
//                    this.startIfReady();
//                } catch (SecurityException se) {
//                    se.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    private boolean isPortraitMode() {
        final int orientation = this.mContext.getResources().getConfiguration().orientation;
        return orientation != Configuration.ORIENTATION_LANDSCAPE && orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * convert viewRect.
     *
     * @param viewRect viewRect.
     * @return converted Rect.
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public Rect convertViewRectToPicture(final Rect viewRect) {
        final int viewWidth = this.getWidth();
        final int viewHeight = this.getHeight();
        final int cameraRotationDegrees = this.mCamera.getRotationDegrees();
        final int imageWidth = this.mCamera.getPreviewSize().getWidth();
        final int imageHeight = this.mCamera.getPreviewSize().getHeight();

        float widthRatio;
        float heightRatio;

        switch (cameraRotationDegrees) {
            case 90:
            case 270:
                widthRatio = (float) imageHeight / viewWidth;
                heightRatio = (float) imageWidth / viewHeight;
                break;
            case 0:
            case 180:
            default:
                widthRatio = (float) imageWidth / viewWidth;
                heightRatio = (float) imageHeight / viewHeight;
                break;
        }

        final float scale = widthRatio < heightRatio ? widthRatio : heightRatio;

        scaledRect = new Rect((int) (viewRect.left * scale + 0.5f), (int) (viewRect.top * scale + 0.5f),
                (int) (viewRect.right * scale + 0.5f), (int) (viewRect.bottom * scale + 0.5f));

        Rect rotateRect = new Rect(scaledRect);
        switch (cameraRotationDegrees) {
            case 90:
                rotateRect = new Rect(scaledRect.top, imageHeight - scaledRect.right, scaledRect.bottom,
                        imageHeight - scaledRect.left);
                break;
            case 180:
                rotateRect = new Rect(imageWidth - scaledRect.right, imageHeight - scaledRect.bottom,
                        imageWidth - scaledRect.left, imageHeight - scaledRect.top);
                break;
            case 270:
                rotateRect = new Rect(imageWidth - scaledRect.bottom, scaledRect.left, imageWidth - scaledRect.top,
                        scaledRect.right);
                break;
            case 0:
            default:
                break;
        }

        Rect resultRect = new Rect(rotateRect);
        if (this.mCamera.getCameraFacing() == SenseCamera.CAMERA_FACING_FRONT) {
            switch (cameraRotationDegrees) {
                case 90:
                case 270:
                    resultRect = new Rect(rotateRect.left, imageHeight - rotateRect.bottom, rotateRect.right,
                            imageHeight - rotateRect.top);
                    break;
                case 0:
                case 180:
                    resultRect = new Rect(imageWidth - rotateRect.right, rotateRect.top, imageWidth - rotateRect.left,
                            rotateRect.bottom);
                    break;
                default:
                    break;
            }
        }

        return resultRect;
    }

    private class SurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(final SurfaceHolder surface) {
            DialogSenseCameraPreview.this.mSurfaceAvailable = true;
            try {
                DialogSenseCameraPreview.this.startIfReady();
            } catch (Exception e) {
                if (DialogSenseCameraPreview.this.mStartListener != null) {
                    DialogSenseCameraPreview.this.mStartListener.onFail();
                }
            }
        }

        @Override
        public void surfaceDestroyed(final SurfaceHolder surface) {
            DialogSenseCameraPreview.this.mSurfaceAvailable = false;
        }

        @Override
        public void surfaceChanged(final SurfaceHolder holder, final int format, final int width, final int height) {
            // noting.
        }
    }

    public interface StartListener {
        void onFail();
    }
}

