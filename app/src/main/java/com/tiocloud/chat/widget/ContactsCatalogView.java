package com.tiocloud.chat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;

import com.blankj.utilcode.util.SizeUtils;
import com.tiocloud.chat.R;

/**
 * author : TaoWang
 * date : 2020-01-15
 * desc : 字母索引
 */
public class ContactsCatalogView extends View {

    //--------------- 气泡 ---------------

    // 图片资源id
    private int mBubbleId = R.drawable.ic_bubble;
    // 图片
    private Drawable mBubble;
    // 图片宽高
    private int mBubbleWidth;
    private int mBubbleHeight;
    // 字体颜色
    private int mBubbleTextColor = Color.WHITE;
    // 字号
    private float mBubbleTextSize = 18f;

    //--------------- 字母索引 ---------------

    // 字体颜色
    private int mLetterNormalColor = 0xFF333333;
    private int mLetterTouchColor = 0xFF56D89A;
    // 字号
    private float mLetterTextSize = 12f;
    // padding
    private float mLetterPaddingLeft = 5f;
    private float mLetterPaddingRight = 5f;
    // margin
    private float mLetterMarginLeft = 5f;
    private float mLetterMarginRight = 0f;

    // 字母表的宽度空间
    private float mLetterWidthSpace;
    // 一个字母的高度
    private float mOneLetterHeight;
    // 一个字母的宽度
    private float mOneLetterWidth;
    // 字母表的起始Y坐标
    private float mLetterTopY;
    // 字母表的结束Y坐标
    private float mLetterBottomY;

    //--------------- 气泡带阴影，需要矫正 ---------------

    // margin
    private float mCorrectMarginY = SizeUtils.dp2px(15);

    //--------------- 其他 ---------------

    private Paint mPaint;
    private String[] mLetters;
    private OnTouchChangedListener mListener;

    private float mTouchY = 0f;// 触摸点Y坐标
    private boolean mIsDrawBubble;// 是否画出了气泡
    private boolean mIsTouchLetter;// 是否处在字母区域内
    private boolean mIsTouchDownLetter;// 按下时是否处在字母区域内
    private int mLetterPosition;// 触摸在哪个字母上


    public ContactsCatalogView(Context paramContext) {
        this(paramContext, null);
    }

    public ContactsCatalogView(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public static void tintDrawable(@NonNull Drawable drawable, int color) {
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
    }

    public ContactsCatalogView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);

        // 索引数据
//        mLetters = paramContext.getResources().getStringArray(R.array.letter_list);
        mLetters = paramContext.getResources().getStringArray(R.array.empty);

        DisplayMetrics dm = getResources().getDisplayMetrics();

        // 提示气泡
        mBubble = paramContext.getResources().getDrawable(mBubbleId);
//        mBubble = tintDrawable(mBubble, R.color.theme_color);
        tintDrawable(mBubble, getResources().getColor(R.color.theme_color));

        mBubbleWidth = mBubble.getIntrinsicWidth();
        mBubbleHeight = mBubble.getIntrinsicHeight();
        mBubbleTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mBubbleTextSize, dm);

        // 字母
        mLetterTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mLetterTextSize, dm);
        mLetterPaddingLeft = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mLetterPaddingLeft, dm);
        mLetterPaddingRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mLetterPaddingRight, dm);
        mLetterMarginLeft = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mLetterMarginLeft, dm);
        mLetterMarginRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mLetterMarginRight, dm);

        mLetterWidthSpace = mLetterTextSize + mLetterPaddingLeft + mLetterPaddingRight + mLetterMarginLeft + mLetterMarginRight;
        mOneLetterHeight = mLetterTextSize * 1.5f;
        mOneLetterWidth = mLetterTextSize;

        // 画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    // ====================================================================================
    // 画图
    // ====================================================================================

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画文字
        drawLetters(canvas);

        // 画气泡
        mIsDrawBubble = false;
        drawBubble(canvas);

    }

    /**
     * 画字母表
     *
     * @param canvas
     */
    private void drawLetters(Canvas canvas) {
        // 字体设置
        mPaint.setTextSize(mLetterTextSize);
        mPaint.setTypeface(mIsTouchLetter ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);

        // 字母表总高度
        float letterHeight = mOneLetterHeight * mLetters.length;
        mLetterTopY = (getHeight() - letterHeight) / 2;
        mLetterBottomY = mLetterTopY + letterHeight;

        // 字母起始点
        float letterTextStartX = getWidth() - mLetterPaddingRight - mLetterMarginRight - mOneLetterWidth / 2;
        float letterTextStartBaselineY = mLetterTopY + mOneLetterHeight;

        // 画出字母
        for (int i = 0; i < mLetters.length; ++i) {
            if (mIsTouchLetter && mLetterPosition == i) {
                mPaint.setColor(mLetterTouchColor);
            } else {
                mPaint.setColor(mLetterNormalColor);
            }

            float letterTextBaselineY = letterTextStartBaselineY + mOneLetterHeight * i;
            canvas.drawText(mLetters[i], letterTextStartX, letterTextBaselineY, mPaint);
        }
    }

    /**
     * 画气泡
     *
     * @param canvas
     */
    private void drawBubble(Canvas canvas) {
        if (!mIsTouchLetter) {
            return;
        }

        // 气泡 X 坐标
        int bubbleLeftX = getWidth() - (int) mLetterWidthSpace - mBubbleWidth;
        int bubbleRightX = bubbleLeftX + mBubbleWidth;
        // 气泡 Y 坐标
        int bubbleTopY = (int) mTouchY - mBubbleHeight + (int) mCorrectMarginY;
        bubbleTopY = Math.max(bubbleTopY, (int) mLetterTopY - mBubbleHeight + (int) (2 * mCorrectMarginY));
        bubbleTopY = Math.min(bubbleTopY, (int) mLetterBottomY - mBubbleHeight + (int) (2 * mCorrectMarginY));
        int bubbleBottomY = bubbleTopY + mBubbleHeight;
        // 画出气泡
        mBubble.setBounds(bubbleLeftX, bubbleTopY, bubbleRightX, bubbleBottomY);
        mBubble.draw(canvas);

        // 字体设置
        mPaint.setTextSize(mBubbleTextSize);
        mPaint.setColor(mBubbleTextColor);
        mPaint.setTypeface(Typeface.DEFAULT);
        // 文字起始点
        float bubbleTextStartX = (float) bubbleLeftX + (float) mBubbleWidth / 2;
        float bubbleTextBaselineY = (float) bubbleTopY + (float) mBubbleHeight / 2 + mBubbleTextSize / 3;
        // 画出文字
        canvas.drawText(mLetters[mLetterPosition], bubbleTextStartX, bubbleTextBaselineY, mPaint);

        mIsDrawBubble = true;
    }

    // ====================================================================================
    // 手势处理
    // ====================================================================================

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        mTouchY = event.getY();
        int touchAction = event.getAction();

        handleTouchEvent(touchX, mTouchY, touchAction);

        notifyListener(touchAction);

        if (mIsTouchLetter) {
            invalidate();
            return true;
        }

        resetCanvas();

        return super.dispatchTouchEvent(event);
    }

    /**
     * 如果画布上存在气泡，则再次更新画布，将其去除
     */
    private void resetCanvas() {
        if (mIsDrawBubble) {
            invalidate();
        }
    }

    /**
     * 通知监听
     *
     * @param touchAction
     */
    private void notifyListener(int touchAction) {
        if (!mIsTouchLetter) {
            return;
        }

        switch (touchAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (mListener != null) {
                    mListener.onHit(mLetters[mLetterPosition]);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mListener != null) {
                    mListener.onCancel();
                }
                break;
        }
    }

    /**
     * 处理触摸事件，获取所需的数据
     *
     * @param touchX
     * @param touchY
     * @param touchAction
     */
    private void handleTouchEvent(float touchX, float touchY, int touchAction) {
        mIsTouchLetter = false;

        switch (touchAction) {
            case MotionEvent.ACTION_DOWN:

                if (isTouchLetter(touchX, touchY)) {
                    mIsTouchDownLetter = true;
                    mIsTouchLetter = true;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsTouchDownLetter) {
                    mIsTouchLetter = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsTouchDownLetter = false;
                break;
        }

        if (mIsTouchLetter) {
            mLetterPosition = getLetterPosition();
        }
    }

    /**
     * 手指是否在字母区域内
     *
     * @param touchX
     * @param touchY
     * @return
     */
    private boolean isTouchLetter(float touchX, float touchY) {
        int screenWidth = getWidth();
        float letterWidth = mOneLetterWidth + mLetterPaddingLeft + mLetterPaddingRight;

        boolean isTouchLetterX = touchX > screenWidth - mLetterMarginRight - letterWidth && touchX < screenWidth - mLetterMarginRight;
        boolean isTouchLetterY = touchY > mLetterTopY && touchY < mLetterBottomY;

        return isTouchLetterX && isTouchLetterY;
    }

    /**
     * 获取选中的字母位置
     *
     * @return
     */
    private int getLetterPosition() {
        float letterTotalHeight = mLetterBottomY - mLetterTopY;
        float letterTouchY = mTouchY - mLetterTopY;

        int index = (int) (letterTouchY / letterTotalHeight * mLetters.length);
        index = Math.max(index, 0);
        return Math.min(index, mLetters.length - 1);
    }

    // ====================================================================================
    // public
    // ====================================================================================

    /**
     * 更新字母索引
     *
     * @param letters
     */
    public void updateLetters(String[] letters) {
        this.mLetters = letters;
        invalidate();
    }

    /**
     * 设置触摸监听
     *
     * @param listener
     */
    public void setOnTouchChangedListener(OnTouchChangedListener listener) {
        this.mListener = listener;
    }

    public abstract static class OnTouchChangedListener {
        public abstract void onHit(String letter);

        public void onCancel() {

        }
    }
}
