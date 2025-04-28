package com.tiocloud.chat.feature.search.curr.all;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.search.curr.SearchActivity;
import com.tiocloud.chat.feature.search.curr.msg.MsgSearchResultActivity;
import com.tiocloud.chat.feature.user.detail.UserDetailActivity;
import com.tiocloud.chat.feature.search.curr.all.adapter.AllListAdapter;
import com.tiocloud.chat.feature.search.curr.all.adapter.model.ItemType;
import com.tiocloud.chat.feature.search.curr.all.adapter.model.MultiItem;
import com.tiocloud.chat.feature.search.curr.all.adapter.model.SectionMultipleItem;
import com.tiocloud.chat.feature.search.curr.main.SearchFragment;
import com.tiocloud.chat.feature.search.curr.main.base.BaseResultFragment;
import com.tiocloud.chat.feature.session.group.GroupSessionActivity;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.db.dao.GroupMsgTableCrud;
import com.watayouxiang.db.table.GroupMsgTable;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.watayouxiang.androidutils.mvp.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author : TaoWang
 * date : 2020-02-13
 * desc :
 */
public class AllResultFragment extends BaseResultFragment<List<SectionMultipleItem>> {

    // ====================================================================================
    // data
    // ====================================================================================

    private List<SectionMultipleItem> data2Items(List<MailListResp.Friend> friends, List<MailListResp.Group> groups, List<Map<String, Object>> mapList) {
        if (friends == null) friends = new ArrayList<>();
        if (groups == null) groups = new ArrayList<>();

        int showCount = 3;// 最多显示个数
        List<SectionMultipleItem> items = new ArrayList<>();

        // 好友
        int friendSize = friends.size();
        if (friendSize > 0) {
            items.add(new SectionMultipleItem(getString(R.string.friend), 1, friendSize > showCount));
            for (int i = 0; i < (Math.min(friendSize, showCount)); i++){
                if (friendSize <= showCount && i == (Math.min(friendSize, showCount)) - 1){
                    items.add(new SectionMultipleItem(new MultiItem(friends.get(i)), ItemType.FRIEND_BOTTOM));
                }else {
                    items.add(new SectionMultipleItem(new MultiItem(friends.get(i)), ItemType.FRIEND));
                }
            }
            if (friendSize > showCount){
                items.add(new SectionMultipleItem(getString(R.string.more_friend), 1, friendSize > showCount, ItemType.BOTTOM));
            }
        }

        // 群组
        int groupSize = groups.size();
        if (groupSize > 0) {
            items.add(new SectionMultipleItem(getString(R.string.group_chat), 2, groupSize > showCount));
            for (int i = 0; i < (Math.min(groupSize, showCount)); i++){
                if (groupSize <= showCount && i == (Math.min(groupSize, showCount)) - 1){
                    items.add(new SectionMultipleItem(new MultiItem(groups.get(i)), ItemType.GROUP_BOTTOM));
                }else {
                    items.add(new SectionMultipleItem(new MultiItem(groups.get(i)), ItemType.GROUP));
                }
            }
            if (groupSize > showCount){
                items.add(new SectionMultipleItem(getString(R.string.more_group_chat), 2, groupSize > showCount, ItemType.BOTTOM));
            }
        }

        int msgSize = mapList.size();
        if (msgSize > 0) {
            items.add(new SectionMultipleItem(getString(R.string.chat_history), 3, msgSize > showCount));
            for (int i = 0; i < (Math.min(msgSize, showCount)); i++){
                if (msgSize <= showCount && i == (Math.min(msgSize, showCount)) - 1){
                    items.add(new SectionMultipleItem(new MultiItem(mapList.get(i)), ItemType.MSG_BOTTOM));
                }else {
                    items.add(new SectionMultipleItem(new MultiItem(mapList.get(i)), ItemType.MSG));
                }
            }
            if (msgSize > showCount){
                items.add(new SectionMultipleItem(getString(R.string.more_chat_history), 3, msgSize > showCount, ItemType.BOTTOM));
            }
        }

        return items;
    }

    @Override
    protected void loadData(String keyWord) {
        getModel().requestMailList(null, keyWord, new BaseModel.DataProxy<MailListResp>() {
            @Override
            public void onSuccess(MailListResp resp) {
                List<MailListResp.Friend> friends = resp.fd;
                List<MailListResp.Group> groups = resp.group;
                List<Map<String, Object>> mapList = queryMsg(keyWord);
                if (friends.size() + groups.size() + mapList.size() < 1){
                    notifyEmpty();
                }else {
                    notifySuccess(data2Items(friends, groups, mapList));
                }
            }

            @Override
            public void onFailure(String msg) {
                List<Map<String, Object>> mapList = queryMsg(keyWord);
                if (mapList.size() > 0){
                    notifySuccess(data2Items(new ArrayList<>(), new ArrayList<>(), mapList));
                }else {
                    notifyError();
                }
            }
        });
    }

    private List<Map<String, Object>> queryMsg(String keyWord) {
        return GroupMsgTableCrud.queryChatMsgGroup(keyWord);
    }

    // ====================================================================================
    // ui
    // ====================================================================================

    @Override
    protected int successLayoutId() {
        return R.layout.tio_search_all_result_fragment;
    }

    @Override
    protected void initSuccessLayout(List<SectionMultipleItem> data, String keyWord) {
        // list
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        // adapter
        new AllListAdapter(data, recyclerView, keyWord) {
            @Override
            protected void onClickMoreBtn(int pageIndex) {
                super.onClickMoreBtn(pageIndex);
//                SearchFragment searchFragment = (SearchFragment) getParentFragment();
//                if (searchFragment != null) {
//                    searchFragment.setCurrentItem(pageIndex);
//                }
                FragmentActivity activity = getActivity();
                if (activity != null){
//                    ((SearchActivity)activity).hideAllFragment();
//                    ((SearchActivity)activity).showAllFriendFragment();
                    ((SearchActivity)activity).repalceFragment(pageIndex, keyWord);
                }
            }

            @Override
            protected void onClickFriendItem(MailListResp.Friend friend) {
                super.onClickFriendItem(friend);
                UserDetailActivity.start(getActivity(), String.valueOf(friend.uid));
            }

            @Override
            protected void onClickGroupItem(MailListResp.Group group) {
                super.onClickGroupItem(group);
                GroupSessionActivity.active(getActivity(), group.groupid);
            }

            @Override
            protected void onClickMsgItem(Map<String, Object> map) {
                super.onClickMsgItem(map);
                MsgSearchResultActivity.start(getActivity(), keyWord, (int)(map.get("chatMode")), (String)(map.get("chatLinkId")),
                        StringUtil.nonNull(map.get("name")), StringUtil.nonNull(map.get("avatar")));
            }
        };
    }

//    public void search(String keyWord){
//        BaseResultFragment fragment = this;
//        fragment.updateKeyWord(keyWord);
//        // 加载当前所处页面
//        fragment.load();
//    }
}
