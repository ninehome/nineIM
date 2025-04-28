package com.tiocloud.chat.feature.home.friend.adapter.viewholder;

import android.widget.TextView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.home.friend.adapter.BaseContactAdapter;
import com.tiocloud.chat.feature.home.friend.adapter.model.item.HeadItem;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/7/22
 *     desc   : 表的 字母组头
 * </pre>
 */
public class HeadHolder extends BaseViewHolder<HeadItem> {
    @Override
    public int getLayoutId() {
        return R.layout.tio_contacts_abc_item;
    }

    @Override
    public void convert(BaseContactAdapter contactAdapter, int position, HeadItem item) {
        TextView name = findViewById(R.id.tv_name);
        name.setText(item.getText());
    }
}
