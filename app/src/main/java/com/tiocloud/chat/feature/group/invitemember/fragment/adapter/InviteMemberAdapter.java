package com.tiocloud.chat.feature.group.invitemember.fragment.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.watayouxiang.httpclient.model.response.ApplyGroupFdListResp;
import com.tiocloud.chat.util.KeywordUtil;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;

import java.util.LinkedList;

/**
 * author : TaoWang
 * date : 2020/2/25
 * desc :
 */
public class InviteMemberAdapter extends BaseQuickAdapter<ApplyGroupFdListResp.Friend, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {
    private LinkedList<String> checkedIds = new LinkedList<>();
    private String keyWord;

    public InviteMemberAdapter() {
        super(R.layout.tio_invite_member_item);
        checkedIds.clear();
        setOnItemClickListener(this);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyGroupFdListResp.Friend item) {
        TioImageView hiv_avatar = helper.getView(R.id.hiv_avatar);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_subtitle = helper.getView(R.id.tv_subtitle);
        CheckBox checkBox = helper.getView(R.id.checkBox);

        hiv_avatar.tio_roundAvatar(item.avatar);

        // 如果有备注名则优先显示
        String name = item.remarkname;
        if (TextUtils.isEmpty(name)) {
            name = item.nick;
        }
        tv_name.setText(KeywordUtil.matcherSearchTitle(
                Color.parseColor("#FF4C94E8"),
                StringUtil.nonNull(name),
                keyWord));

        tv_subtitle.setVisibility(View.GONE);

        checkBox.setClickable(false);
        checkBox.setChecked(checkedIds.contains(String.valueOf(item.uid)));
    }

    public void setNewData(ApplyGroupFdListResp friends, String keyWord) {
        this.keyWord = keyWord;
        setNewData(friends);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ApplyGroupFdListResp.Friend friend = getData().get(position);
        String uid = String.valueOf(friend.uid);
        if (!checkedIds.remove(uid)) {
            checkedIds.add(uid);
        }
        notifyItemChanged(position);
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedItemChange(checkedIds);
        }
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
