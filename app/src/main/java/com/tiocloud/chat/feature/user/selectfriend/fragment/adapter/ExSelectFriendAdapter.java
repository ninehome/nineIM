package com.tiocloud.chat.feature.user.selectfriend.fragment.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiocloud.chat.feature.user.selectfriend.fragment.adapter.model.MultiModel;
import com.tiocloud.chat.feature.user.selectfriend.fragment.adapter.model.SectionModel;
import com.tiocloud.chat.widget.ContactsCatalogView;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020/3/17
 * desc :
 */
public class ExSelectFriendAdapter extends SelectFriendAdapter {

    // 联系人目录
    private ContactsCatalogView mCatalogView;
    /**
     * key：section 值
     * value：section 在列表中的位置
     */
    private LinkedHashMap<String, Integer> mGroupMap = new LinkedHashMap<>();
    // 列表数据远
    private List<MultiModel> mMultiModels = new ArrayList<>();

    // ====================================================================================
    // data
    // ====================================================================================

    /**
     * 安装 联系人目录
     *
     * @param catalogView
     * @param recyclerView
     */
    public void installCatalogView(ContactsCatalogView catalogView, final RecyclerView recyclerView) {
        this.mCatalogView = catalogView;
        catalogView.setOnTouchChangedListener(new ContactsCatalogView.OnTouchChangedListener() {
            @Override
            public void onHit(String letter) {
                scroll2Top(letter, recyclerView);
            }
        });
    }

    // 定位到顶部
    private void scroll2Top(String letter, RecyclerView recyclerView) {
        Integer position = mGroupMap.get(letter);
        if (position == null) return;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            linearLayoutManager.scrollToPositionWithOffset(position, 0);
        }
    }

    /**
     * 设置数据
     *
     * @param friends
     * @param keyWord
     */
    public void updateData(List<MailListResp.Friend> friends, String keyWord) {
        // 设置搜索关键词
        setKeyWord(keyWord);
        // 解析数据
        parseMultiModels(friends);
        // 更新 "联系人目录" 数据
        if (mCatalogView != null) {
            mCatalogView.updateLetters(mGroupMap.keySet().toArray(new String[0]));
        }
        // 设置列表数据
        setNewData(mMultiModels);
    }

    private void parseMultiModels(List<MailListResp.Friend> friends) {
        if (friends == null) friends = new ArrayList<>(0);

        mGroupMap.clear();
        mMultiModels.clear();

        String currChatIndex = null;
        int friendSize = friends.size();
        for (int i = 0; i < friendSize; i++) {
            MailListResp.Friend friend = friends.get(i);

            // 添加组头 model
            if (currChatIndex == null || !currChatIndex.equals(friend.chatindex)) {
                currChatIndex = friend.chatindex;
                MultiModel multiModel = new MultiModel(new SectionModel(currChatIndex));
                mMultiModels.add(multiModel);

                // 建立组位置索引表
                mGroupMap.put(currChatIndex, mMultiModels.indexOf(multiModel));
            }

            // 添加联系人 model
            mMultiModels.add(new MultiModel(friend));
        }
    }
}
