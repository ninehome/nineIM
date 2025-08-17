package com.tiocloud.chat.yanxun.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class MarqueeHorizontalTextView extends AppCompatTextView {
    private float textLength = 0f;
    private float drawTextX = 0f;// 文本的横坐标
    public boolean isStarting = false;// 是否开始滚动
    private Paint paint = null;
    private String text = "";
    private long waitTime = 1000; //开始时等待的时间
    private int scrollTile = 2; //文字的滚动速度
    private int baseline;

    public MarqueeHorizontalTextView(Context context) {
        super(context);
        initView(context);
    }

    public MarqueeHorizontalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MarqueeHorizontalTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        setMaxWidth(context.getResources().getDisplayMetrics().widthPixels / 2); //因为需求需要所以设置了最大宽度，如果不需要此功能可以删除掉

        paint = getPaint();
        paint.setColor(getTextColors().getColorForState(getDrawableState(), 0));
        text = getText().toString();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        textLength = paint.measureText(text);
        isStarting = true;
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        paint.setColor(color);
        start();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        this.text = text.toString();
        this.textLength = getPaint().measureText(text.toString());
        drawTextX = 0;
        start();
    }



    public void start() {
        isStarting = true;
        invalidate();
    }

    public void stop() {
        isStarting = false;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        final Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        baseline = (canvas.getHeight() - fontMetrics.bottom - fontMetrics.top) / 2;
        if (textLength <= canvas.getWidth()) {
            canvas.drawText(text, (canvas.getWidth()-textLength)/2, baseline, paint);
            return;
        }
        canvas.drawText(text, -drawTextX, baseline, paint);

        if (!isStarting) {
            return;
        }
        if (drawTextX == 0) {
            postDelayed(() -> {
                drawTextX = 1;
                isStarting = true;
                invalidate();
            }, waitTime);
            isStarting = false;
            return;
        }
        drawTextX += scrollTile;
        //判断是否滚动结束
        if (drawTextX > textLength) {
            drawTextX = -canvas.getWidth();

        }
        invalidate();
    }
}
