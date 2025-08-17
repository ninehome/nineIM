package com.tiocloud.chat.feature.group.info.fragment.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.group.info.fragment.adapter.model.AddButton;
import com.tiocloud.chat.feature.group.info.fragment.adapter.model.RemoveButton;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020/2/27
 * desc :
 */
public class MemberListAdapter extends BaseMultiItemQuickAdapter<MemberItem, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MemberListAdapter(List<MemberItem> data, RecyclerView recyclerView) {
        super(data);
        addItemType(MemberItem.USER, R.layout.tio_member_list_item);
        addItemType(MemberItem.BUTTON, R.layout.tio_member_list_item_btn);

        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 5, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(this);
    }

    /**
     * @param groupOwner       群主吗
     * @param groupUserList    群成员列表
     * @param showInviteMember 显示邀请按钮吗
     */
    public void setNewData(boolean groupOwner, GroupUserListResp groupUserList, boolean showInviteMember) {
        List<MemberItem> items = new ArrayList<>();

        // 数据处理
        List<GroupUserListResp.GroupMember> list = groupUserList.list;
        int maxSize = 15;
        if (groupOwner) --maxSize;
        if (showInviteMember) --maxSize;
        maxSize = Math.min(maxSize, list.size());
        List<GroupUserListResp.GroupMember> members = list.subList(0, maxSize);

        // 数据整合
        for (GroupUserListResp.GroupMember user : members) {
            items.add(new MemberItem(user));
        }
        if (groupOwner || showInviteMember) {
            items.add(new MemberItem(new AddButton()));
        }
        if (groupOwner) {
            items.add(new MemberItem(new RemoveButton()));
        }

        // 显示
        setNewData(items);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberItem item) {
        switch (helper.getItemViewType()) {
            case MemberItem.USER:
                TioImageView hiv_avatar = helper.getView(R.id.hiv_avatar);
                TextView tv_name = helper.getView(R.id.tv_name);
                hiv_avatar.tio_roundAvatar(item.user.avatar);
                tv_name.setText(StringUtil.nonNull(item.user.nick));
                break;
            case MemberItem.BUTTON:
                ImageView iv_icon = helper.getView(R.id.iv_icon);
                TextView tv_btn = helper.getView(R.id.tv_btn);
                iv_icon.setImageResource(item.button.iconRes);
                tv_btn.setText(item.button.name);
                break;
        }
    }
}
