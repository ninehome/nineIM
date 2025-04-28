package com.tiocloud.chat.feature.group.member;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;

/**
 * author : TaoWang
 * date : 2020/2/27
 * desc :
 */
class ListAdapter extends BaseQuickAdapter<GroupUserListResp.GroupMember, BaseViewHolder> {
    public ListAdapter() {
        super(R.layout.tio_group_member_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupUserListResp.GroupMember item) {
        TioImageView hiv_avatar = helper.getView(R.id.hiv_avatar);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_groupOwnerTag = helper.getView(R.id.tv_groupOwnerTag);
        View v_divider = helper.getView(R.id.v_divider);
        View v_divider1 = helper.getView(R.id.v_divider1);

        hiv_avatar.tio_roundAvatar(item.avatar);
        tv_name.setText(StringUtil.nonNull(item.nick));
//        tv_groupOwnerTag.setVisibility(item.grouprole == 1 ? View.VISIBLE : View.GONE);
        if (item.grouprole == 1){
            tv_groupOwnerTag.setVisibility(View.VISIBLE);
            tv_groupOwnerTag.setText(R.string.group_owner);
        }else if (item.grouprole == 3){
            tv_groupOwnerTag.setVisibility(View.VISIBLE);
            tv_groupOwnerTag.setText(R.string.group_manager);
        }else {
            tv_groupOwnerTag.setVisibility(View.GONE);
        }

        if(getData().size()-1 == helper.getAdapterPosition()){
            v_divider1.setVisibility(View.GONE);
            v_divider.setVisibility(View.GONE);
        }else {
            v_divider1.setVisibility(View.GONE);
            v_divider.setVisibility(View.VISIBLE);
        }
    }
}
