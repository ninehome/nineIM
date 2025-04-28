package com.tiocloud.chat.feature.group.info.fragment.adapter.model;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.group.invitemember.InviteMemberActivity;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.imclient.utils.ConstantUtils;

/**
 * author : TaoWang
 * date : 2020/3/16
 * desc : 添加成员
 */
public class AddButton extends ButtonModel {
    public AddButton() {
        super(ConstantUtils.context.getString(R.string.tianjia), R.drawable.btn_add_mem);
    }

    @Override
    public void onItemClick(TioActivity activity, String groupId) {
        InviteMemberActivity.start(activity, groupId);
    }
}
