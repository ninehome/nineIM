package com.tiocloud.chat.feature.share.group.feature.recent;

import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.watayouxiang.httpclient.model.response.ChatListResp;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/20
 *     desc   :
 * </pre>
 */
class RecentAdapter extends BaseQuickAdapter<ChatListResp.List, BaseViewHolder> {
    public RecentAdapter(RecyclerView recyclerView) {
        super(R.layout.item_share_group_recent);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        bindToRecyclerView(recyclerView);

        RecentListHeader header = new RecentListHeader(recyclerView.getContext());
        addHeaderView(header);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatListResp.List item) {
        TioImageView tiv_avatar = helper.getView(R.id.tiv_avatar);
        tiv_avatar.tio_roundAvatar(item.avatar);

        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(StringUtil.nonNull(item.name));
    }
}
