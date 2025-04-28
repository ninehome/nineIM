package com.tiocloud.chat.feature.session.common.action;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseAction;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.imclient.TioIMClient;

import java.util.ArrayList;
import java.util.List;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc : ViewPager适配器
 */
public class ActionsViewPagerAdapter extends PagerAdapter {

    private final List<BaseAction> actions;

    public ActionsViewPagerAdapter(@NonNull List<BaseAction> actions) {
        this.actions = new ArrayList<>(actions);
    }

    @Override
    public int getCount() {
        return (int) Math.ceil((double) actions.size() / (double) ActionsGridViewAdapter.GRID_VIEW_ITEM_COUNT);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int pageIndex = position + 1;
        int end = Math.min(pageIndex * ActionsGridViewAdapter.GRID_VIEW_ITEM_COUNT, actions.size());
        List<BaseAction> subActions = actions.subList(position * ActionsGridViewAdapter.GRID_VIEW_ITEM_COUNT, end);

        // 初始化 gridView
        GridView gridView = new GridView(container.getContext());
        gridView.setSelector(R.color.transparent);
        gridView.setGravity(Gravity.CENTER);
        gridView.setNumColumns(ActionsGridViewAdapter.GRID_VIEW_COLUMNS);// 设置列数量
        gridView.setHorizontalSpacing(0);// 设置列表项水平间距
        gridView.setVerticalSpacing(0);

        // 设置适配器
        final ActionsGridViewAdapter gridViewAdapter = new ActionsGridViewAdapter(subActions);
        gridView.setAdapter(gridViewAdapter);

        gridView.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!TioIMClient.getInstance().isHandshake()) {
                    TioToast.showShort(view.getContext().getString(R.string.current_net_error));
                } else {
                    gridViewAdapter.getItem(position).onClick();
                }
            }
        });

        container.addView(gridView);
        return gridView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
