package com.watayouxiang.wallet.feature.redpacket.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.watayouxiang.wallet.R;
import com.watayouxiang.widgetlibrary.tablayout.TaoTabAdapter;
import com.watayouxiang.widgetlibrary.tablayout.TaoViewHolder;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/3
 *     desc   :
 * </pre>
 */
public class RedPacketTabAdapter extends TaoTabAdapter {
    public RedPacketTabAdapter(@NonNull RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    protected int getItemViewLayoutId() {
        return R.layout.wallet_redpacket_tab_item;
    }

    @Override
    protected void convert(@NonNull TaoViewHolder holder, int position) {
        String name = getData().get(position);
        boolean select = position == getSelectPosition();

        TextView tv_txt = holder.itemView.findViewById(R.id.tv_txt);
        View v_line = holder.itemView.findViewById(R.id.v_line);

        tv_txt.setText(String.valueOf(name));
        tv_txt.setTextColor(select ? Color.parseColor("#000000") : Color.parseColor("#666666"));
        tv_txt.setTypeface(select ? Typeface.defaultFromStyle(Typeface.BOLD) : Typeface.defaultFromStyle(Typeface.NORMAL));
//        tv_txt.setTextColor(select ? Color.parseColor("#FFFFFF") : Color.parseColor("#FFBEBE"));
//        tv_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, select ? 20 : 14);
//        tv_txt.setTypeface(Typeface.defaultFromStyle(select ? Typeface.BOLD : Typeface.NORMAL));
        v_line.setVisibility(select ? View.VISIBLE : View.INVISIBLE);
    }
}
