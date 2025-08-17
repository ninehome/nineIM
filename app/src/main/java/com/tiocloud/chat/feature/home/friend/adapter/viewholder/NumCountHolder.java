package com.tiocloud.chat.feature.home.friend.adapter.viewholder;

import android.widget.TextView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.home.friend.adapter.BaseContactAdapter;
import com.tiocloud.chat.feature.home.friend.adapter.model.item.NumCountItem;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/7/10
 *     desc   : 个数统计 项
 * </pre>
 */
public class NumCountHolder extends BaseViewHolder<NumCountItem>{
    @Override
    public int getLayoutId() {
        return R.layout.item_home_friend_list_numcount;
    }

    @Override
    public void convert(BaseContactAdapter adapter, int position, NumCountItem item) {
        TextView tv_centerTxt = findViewById(R.id.tv_centerTxt);
        tv_centerTxt.setText(item.getNumCountDesc());
    }
}
