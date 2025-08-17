package com.tiocloud.chat.feature.group.transfergroup.fragment.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;
import com.tiocloud.chat.util.KeywordUtil;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;

import java.util.ListIterator;

/**
 * author : TaoWang
 * date : 2020/2/25
 * desc :
 */
public class TransferGroupAdapter extends BaseQuickAdapter<GroupUserListResp.GroupMember, BaseViewHolder> {
    private String keyWord;

    public TransferGroupAdapter() {
        super(R.layout.tio_transfer_group_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupUserListResp.GroupMember item) {
        TioImageView hiv_avatar = helper.getView(R.id.hiv_avatar);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_subtitle = helper.getView(R.id.tv_subtitle);

        hiv_avatar.tio_roundAvatar(item.avatar);
        tv_name.setText(KeywordUtil.matcherSearchTitle(
                Color.parseColor("#FF4C94E8"),
                StringUtil.nonNull(item.nick),
                keyWord));
        tv_subtitle.setVisibility(View.GONE);
    }

    // ====================================================================================
    // handle data
    // ====================================================================================

    public void setNewData(GroupUserListResp friends, String keyWord) {
        this.keyWord = keyWord;
        removeMyselfItem(friends);
        filterItems(keyWord, friends);
        setNewData(friends.list);
    }

    public void addData(GroupUserListResp friends, String keyWord) {
        this.keyWord = keyWord;
        removeMyselfItem(friends);
        filterItems(keyWord, friends);
        addData(friends.list);
    }

    private void filterItems(String keyWord, GroupUserListResp friends) {
        ListIterator<GroupUserListResp.GroupMember> it = friends.list.listIterator();
        while (it.hasNext()) {
            GroupUserListResp.GroupMember user = it.next();
            if (user.nick != null && keyWord != null) {
                if (!user.nick.toLowerCase().contains(keyWord.toLowerCase())) {
                    it.remove();
                }
            }
        }
    }

    private void removeMyselfItem(GroupUserListResp friends) {
        int currUid = 0;
        try {
            currUid = Integer.parseInt(String.valueOf(TioDBPreferences.getCurrUid()));
        } catch (Exception ignored) {
        }

        ListIterator<GroupUserListResp.GroupMember> it = friends.list.listIterator();
        while (it.hasNext()) {
            if (it.next().uid == currUid) {
                it.remove();
                break;
            }
        }
    }
}
