package com.tiocloud.chat.feature.search.curr.main.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiocloud.chat.R;
import com.watayouxiang.widgetlibrary.tablayout.TaoTabAdapter;
import com.watayouxiang.widgetlibrary.tablayout.TaoViewHolder;

/**
 * author : TaoWang
 * date : 2020-02-13
 * desc :
 */
public class SearchTabAdapter extends TaoTabAdapter {
    public SearchTabAdapter(@NonNull RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    protected int getItemViewLayoutId() {
        return R.layout.tio_search_tab_item_view;
    }

    @Override
    protected void convert(@NonNull TaoViewHolder holder, int position) {
        String name = getData().get(position);
        boolean select = position == getSelectPosition();

        TextView tv_txt = holder.itemView.findViewById(R.id.tv_txt);
        View v_line = holder.itemView.findViewById(R.id.v_line);

        tv_txt.setText(String.valueOf(name));
        tv_txt.setTextColor(select ? Color.parseColor("#FF000000") : Color.parseColor("#FF666666"));
//        tv_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, select ? 20 : 14);
//        tv_txt.setTypeface(Typeface.defaultFromStyle(select ? Typeface.BOLD : Typeface.NORMAL));
        v_line.setVisibility(select ? View.VISIBLE : View.INVISIBLE);
    }
}
