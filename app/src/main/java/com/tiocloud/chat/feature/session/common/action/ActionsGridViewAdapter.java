package com.tiocloud.chat.feature.session.common.action;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseAction;

import java.util.List;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc : GridView适配器
 */
public class ActionsGridViewAdapter extends BaseAdapter {

    public static final int GRID_VIEW_ITEM_COUNT = 8;
    public static final int GRID_VIEW_COLUMNS = 4;

    private final List<BaseAction> baseActions;

    public ActionsGridViewAdapter(List<BaseAction> baseActions) {
        this.baseActions = baseActions;
    }

    @Override
    public int getCount() {
        return baseActions.size();
    }

    @Override
    public BaseAction getItem(int position) {
        return baseActions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.actions_item_layout, null);
        }

        final View finalConvertView = convertView;
        parent.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = finalConvertView.getLayoutParams();
                layoutParams.height = parent.getContext().getResources().getDimensionPixelOffset(R.dimen.function_panel_height)
                        / (GRID_VIEW_ITEM_COUNT / GRID_VIEW_COLUMNS);
                finalConvertView.setLayoutParams(layoutParams);
            }
        });

        BaseAction action = baseActions.get(position);
        ((ImageView) convertView.findViewById(R.id.imageView)).setImageResource(action.iconResId);
        ((TextView) convertView.findViewById(R.id.textView)).setText(parent.getContext().getString(action.titleId));
        return convertView;
    }
}

