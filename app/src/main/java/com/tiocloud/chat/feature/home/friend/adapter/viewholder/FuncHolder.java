package com.tiocloud.chat.feature.home.friend.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.home.friend.adapter.BaseContactAdapter;
import com.tiocloud.chat.feature.home.friend.adapter.model.item.FuncItem;
import com.tiocloud.chat.widget.textview.ListUnreadTextView;
import com.tiocloud.chat.util.StringUtil;

/**
 * author : TaoWang
 * date : 2020-01-22
 * desc : 好友请求
 */
public class FuncHolder extends BaseViewHolder<FuncItem> {
    @Override
    public int getLayoutId() {
        return R.layout.tio_func_contacts_item;
    }

    @Override
    public void convert(BaseContactAdapter adapter, int position, FuncItem item) {
        ImageView img = findViewById(R.id.iv_img);
        TextView name = findViewById(R.id.tv_name);
        ListUnreadTextView tip = findViewById(R.id.tv_tip);

        // name
        name.setText(StringUtil.nonNull(item.name));
        // img
        img.setImageResource(item.imgResId);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        // tip
        tip.setUnread(item.applyCount);
        View divider = findViewById(R.id.v_divider);
        divider.setVisibility(View.VISIBLE);
    }
}
