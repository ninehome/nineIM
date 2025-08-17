package com.tiocloud.chat.feature.session.common.action;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseAction;

import java.util.ArrayList;
import java.util.List;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc : 多功能面板
 */
public class ActionsPanel extends LinearLayout {

    private ViewPager viewPager;
    private ViewGroup indicator;

    public ActionsPanel(Context context) {
        this(context, null);
    }

    public ActionsPanel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionsPanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.message_activity_actions_layout, this);
        findViews();
        setVisibility(View.GONE);
    }

    public void init(List<BaseAction> actions) {
        if (actions == null) actions = new ArrayList<>();

        initEvents(actions);
        initViews(actions);
    }

    private void initViews(List<BaseAction> actions) {
        boolean showIndicator = actions.size() > ActionsGridViewAdapter.GRID_VIEW_ITEM_COUNT;
        indicator.setVisibility(showIndicator ? View.VISIBLE : View.GONE);

        // 根据数量，设置viewPager高度
        final int viewPagerHeight = actions.size() > ActionsGridViewAdapter.GRID_VIEW_COLUMNS
                ? viewPager.getResources().getDimensionPixelOffset(R.dimen.function_panel_height)
                : viewPager.getResources().getDimensionPixelOffset(R.dimen.function_panel_height) / 2;
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
                layoutParams.height = viewPagerHeight;
                viewPager.setLayoutParams(layoutParams);
            }
        });
    }

    private void initEvents(List<BaseAction> actions) {
        final ActionsViewPagerAdapter adapter = new ActionsViewPagerAdapter(actions);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setIndicator(indicator, adapter.getCount(), position);
            }
        });
        viewPager.setAdapter(adapter);
        setIndicator(indicator, adapter.getCount(), 0);
    }

    private void findViews() {
        viewPager = findViewById(R.id.viewPager);
        indicator = findViewById(R.id.actions_page_indicator);
    }

    /**
     * 设置页码
     */
    private void setIndicator(ViewGroup indicator, int total, int current) {
        if (total <= 1) {
            indicator.removeAllViews();
        } else {
            indicator.removeAllViews();
            for (int i = 0; i < total; i++) {
                ImageView imgCur = new ImageView(indicator.getContext());
                // 判断当前页码来更新
                if (i == current) {
                    imgCur.setBackgroundResource(R.drawable.moon_page_selected);
                } else {
                    imgCur.setBackgroundResource(R.drawable.moon_page_unselected);
                }

                indicator.addView(imgCur);
            }
        }
    }
}
