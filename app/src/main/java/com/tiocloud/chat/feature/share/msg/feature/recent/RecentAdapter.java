package com.tiocloud.chat.feature.share.msg.feature.recent;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.group.removemember.fragment.adapter.RemoveMemberAdapter;
import com.watayouxiang.httpclient.model.response.ChatListResp;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;

import java.util.LinkedList;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/20
 *     desc   :
 * </pre>
 */
class RecentAdapter extends BaseQuickAdapter<ChatListResp.List, BaseViewHolder>{
    private LinkedList<String> checkedIds = new LinkedList<>();
    public RecentAdapter(RecyclerView recyclerView) {
        super(R.layout.item_share_msg_recent);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        bindToRecyclerView(recyclerView);

        RecentListHeader header = new RecentListHeader(recyclerView.getContext());
        addHeaderView(header);

        checkedIds.clear();
    }


    @Override
    protected void convert(BaseViewHolder helper, ChatListResp.List item) {
        TioImageView tiv_avatar = helper.getView(R.id.tiv_avatar);

//        CheckBox checkBox = helper.getView(R.id.checkBox);
//        checkBox.setClickable(false);
//        checkBox.setChecked(checkedIds.contains(String.valueOf(item.uid)));
        tiv_avatar.tio_roundAvatar(item.avatar);

        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(StringUtil.nonNull(item.name));
    }

    public LinkedList<String> getCheckedIds() {
        return checkedIds;
    }


}
