package com.tiocloud.chat.feature.share.group.feature.result;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.search.curr.friend.FriendListAdapter;
import com.tiocloud.chat.feature.search.curr.group.GroupListAdapter;
import com.tiocloud.chat.feature.share.group.feature.result.model.ItemType;
import com.tiocloud.chat.feature.share.group.feature.result.model.MultiItem;
import com.tiocloud.chat.feature.share.group.feature.result.model.SectionMultipleItem;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/7/21
 *     desc   :
 * </pre>
 */
public class ResultAdapter extends BaseSectionMultiItemQuickAdapter<SectionMultipleItem, BaseViewHolder>
        implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private String keyWord;
    private MailListResp data;
    private int friendShowCount;
    private int groupShowCount;

    public ResultAdapter(RecyclerView recyclerView) {
        super(R.layout.item_share_group_search_result_head, null);

        addItemType(ItemType.FRIEND.value, R.layout.item_share_group_search_result_friend);
        addItemType(ItemType.GROUP.value, R.layout.item_share_group_search_result_group);

        setOnItemClickListener(this);
        setOnItemChildClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        bindToRecyclerView(recyclerView);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionMultipleItem item) {
        View ll_more = helper.getView(R.id.ll_more);
        TextView tv_headTxt = helper.getView(R.id.tv_headTxt);
        TextView tv_more = helper.getView(R.id.tv_more);

        if (item.initCount >= item.totalCount) {
            // 初始化显示个数 >= 总个数 -> 隐藏
            ll_more.setVisibility(View.INVISIBLE);
        } else {
            // 初始化显示个数 < 总个数 -> 显示
            ll_more.setVisibility(View.VISIBLE);
            tv_headTxt.setText(item.header);
            helper.addOnClickListener(R.id.ll_more);

            if (item.showCount > item.initCount) {
                // 显示数 > 初始化显示个数 -> 收起
                tv_more.setText(mContext.getString(R.string.shouqi));
            } else {
                // 显示数 < 初始化显示个数 -> 查看全部
                tv_more.setText(mContext.getString(R.string.query_all));
            }
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionMultipleItem item) {
        int itemType = item.getItemType();
        if (itemType == ItemType.FRIEND.value) {
            MailListResp.Friend friend = item.t.friend;
            FriendListAdapter.setItemData(mContext,helper, friend, keyWord);
        } else if (itemType == ItemType.GROUP.value) {
            MailListResp.Group group = item.t.group;
            GroupListAdapter.setItemData(helper, group, keyWord);
        }
    }

    // ====================================================================================
    // data
    // ====================================================================================

    public void setNewData(@Nullable MailListResp data, String keyWord) {
        this.setNewData(data, keyWord, 4, 4);
    }

    private void setNewData(@Nullable MailListResp data, String keyWord, int friendShowCount, int groupShowCount) {
        this.keyWord = keyWord;
        this.data = data;
        List<SectionMultipleItem> items = data2Items(data, friendShowCount, groupShowCount);
        super.setNewData(items);
    }

    private List<SectionMultipleItem> data2Items(@Nullable MailListResp mailListResp, int friendShowCount, int groupShowCount) {
        this.friendShowCount = friendShowCount;
        this.groupShowCount = groupShowCount;

        if (mailListResp == null) return null;

        List<SectionMultipleItem> items = new ArrayList<>();

        // 好友
        List<MailListResp.Friend> friends = mailListResp.fd;
        int friendSize = friends.size();
        if (friendSize > 0) {
            items.add(new SectionMultipleItem(ItemType.FRIEND, friendSize, friendShowCount, 4));
            for (int i = 0; i < Math.min(friendSize, friendShowCount); i++) {
                items.add(new SectionMultipleItem(new MultiItem(friends.get(i)), ItemType.FRIEND));
            }
        }
        // 群组
        List<MailListResp.Group> groups = mailListResp.group;
        int groupSize = groups.size();
        if (groupSize > 0) {
            items.add(new SectionMultipleItem(ItemType.GROUP, groupSize, groupShowCount, 4));
            for (int i = 0; i < Math.min(groupSize, groupShowCount); i++) {
                items.add(new SectionMultipleItem(new MultiItem(groups.get(i)), ItemType.GROUP));
            }
        }

        return items;
    }

    // ====================================================================================
    // click
    // ====================================================================================

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        SectionMultipleItem item = getData().get(position);
        int itemType = item.getItemType();
        if (itemType == ItemType.FRIEND.value) {
            MailListResp.Friend friend = item.t.friend;
            onClickFriendItem(friend);
        } else if (itemType == ItemType.GROUP.value) {
            MailListResp.Group group = item.t.group;
            onClickGroupItem(group);
        }
    }

    public void onClickGroupItem(MailListResp.Group group) {

    }

    public void onClickFriendItem(MailListResp.Friend friend) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        SectionMultipleItem item = getData().get(position);
        if (view.getId() == R.id.ll_more) {
            if (item.headType == ItemType.FRIEND) {
                onClickMoreFriendBtn(item);
            } else if (item.headType == ItemType.GROUP) {
                onClickMoreGroupBtn(item);
            }
        }
    }

    private void onClickMoreGroupBtn(SectionMultipleItem item) {
        if (item.showCount > item.initCount) {
            // 显示数 > 初始化显示个数 -> 收起
            setNewData(data, keyWord, friendShowCount, item.initCount);
        } else {
            // 显示数 < 初始化显示个数 -> 查看全部
            setNewData(data, keyWord, friendShowCount, item.totalCount);
        }
    }

    private void onClickMoreFriendBtn(SectionMultipleItem item) {
        if (item.showCount > item.initCount) {
            // 显示数 > 初始化显示个数 -> 收起
            setNewData(data, keyWord, item.initCount, groupShowCount);
        } else {
            // 显示数 < 初始化显示个数 -> 查看全部
            setNewData(data, keyWord, item.totalCount, groupShowCount);
        }
    }
}
