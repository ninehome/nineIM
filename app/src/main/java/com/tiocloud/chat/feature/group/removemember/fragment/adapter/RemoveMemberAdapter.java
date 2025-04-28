package com.tiocloud.chat.feature.group.removemember.fragment.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;

import java.util.LinkedList;

/**
 * author : TaoWang
 * date : 2020/2/25
 * desc :
 */
public class RemoveMemberAdapter extends BaseQuickAdapter<GroupUserListResp.GroupMember, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {
    private LinkedList<String> checkedIds = new LinkedList<>();

    public RemoveMemberAdapter() {
        super(R.layout.tio_remove_member_item);
        checkedIds.clear();
        setOnItemClickListener(this);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupUserListResp.GroupMember item) {
        TioImageView hiv_avatar = helper.getView(R.id.hiv_avatar);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_subtitle = helper.getView(R.id.tv_subtitle);
        CheckBox checkBox = helper.getView(R.id.checkBox);

        hiv_avatar.tio_roundAvatar(item.avatar);
        tv_name.setText(StringUtil.nonNull(item.nick));
        tv_subtitle.setVisibility(View.GONE);

        checkBox.setClickable(false);
        checkBox.setChecked(checkedIds.contains(String.valueOf(item.uid)));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        GroupUserListResp.GroupMember friend = getData().get(position);
        String uid = String.valueOf(friend.uid);
        if (!checkedIds.remove(uid)) {
            checkedIds.add(uid);
        }
        notifyItemChanged(position);
        if (onCheckedChangeListener != null)
            onCheckedChangeListener.onCheckedItemChange(checkedIds);
    }

    public LinkedList<String> getCheckedIds() {
        return checkedIds;
    }

    // ====================================================================================
    // OnCheckedChangeListener
    // ====================================================================================

    private OnCheckedChangeListener onCheckedChangeListener;

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        onCheckedChangeListener = listener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedItemChange(LinkedList<String> linkedList);
    }
}
