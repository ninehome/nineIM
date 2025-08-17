package com.tiocloud.chat.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.tiocloud.chat.R;

public class BCRefreshHeader extends RelativeLayout implements RefreshHeader {

    private ImageView mIvHead;
    private ImageView mIvHand;
    AnimationDrawable animaition;
    int minimumHeight = 0;

    public BCRefreshHeader(Context context) {
        super(context);
        this.initView(context, null, 0);
    }

    public BCRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context, attrs, 0);
    }

    public BCRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        //下拉刷新头部的大小
        setMinimumHeight(dp2px(context, 50));
        minimumHeight=dp2px(context, 50);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        View headerView = View.inflate(context, R.layout.refresh_header, null);
        mIvHead = headerView.findViewById(R.id.iv_head);
        mIvHand = headerView.findViewById(R.id.iv_hand);
        mIvHead.setBackgroundResource(R.drawable.icon_add_friend);
        animaition = (AnimationDrawable) mIvHead.getBackground();
        mIvHead.setVisibility(GONE);
        addView(headerView, params);
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) { // 尺寸定义完成
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
//这里的操作是下拉，回退时，动画的变化，
        mIvHead.setVisibility(GONE);
        mIvHand.setVisibility(VISIBLE);

        float tmp = (float) (minimumHeight / 49);
        if (offset >= tmp * 0 && offset <= tmp * 1) {
            mIvHand.setBackground(getResources().getDrawable(R.mipmap.icon_address));
        } else if (offset >= tmp * 1 && offset <= tmp * 2) {
            mIvHand.setBackground(getResources().getDrawable(R.mipmap.icon_arrowr_gray));
        } else if (offset >= tmp * 2 && offset <= tmp * 3) {
            mIvHand.setBackground(getResources().getDrawable(R.mipmap.icon_choose02));
        } else if (offset >= tmp * 3 && offset <= tmp * 4) {
            mIvHand.setBackground(getResources().getDrawable(R.mipmap.icon_groupchat));
        } else if (offset >= tmp * 4 && offset <= tmp * 5) {
            mIvHand.setBackground(getResources().getDrawable(R.mipmap.icon_my_account));
        } else if (offset >= tmp * 5 && offset <= tmp * 6) {
            mIvHand.setBackground(getResources().getDrawable(R.mipmap.icon_my_setting));
        }  else{
            mIvHand.setBackground(getResources().getDrawable(R.mipmap.ic_delete_easy_photos));
        }


        if (animaition == null) return;
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int extendHeight) {
        animaition.setOneShot(false);   //设置是否只播放一次，和上面xml配置效果一致
        animaition.start();
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        animaition.stop();
        return 100; // 动画结束,延迟多少毫秒之后再收回
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void setPrimaryColors(int... colors) {
        setBackgroundColor(getResources().getColor(R.color.theme_color));
    }

    @NonNull
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) { // 状态改变事件
        switch (newState) {
            case None: // 无状态

                break;
            case PullDownToRefresh: // 可以下拉状态
//                animaition.setOneShot(true);
//                animaition.start();
                break;

            case PullDownCanceled://下拉取消状态

                break;
            case Refreshing: // 刷新中状态
                mIvHead.setVisibility(VISIBLE);
                mIvHand.setVisibility(GONE);
                animaition.start();
                animaition.setOneShot(false);
                break;
            case ReleaseToRefresh:  // 释放就开始刷新状态

                break;

        }
    }


    /**
     * dp转px
     */
    private int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}