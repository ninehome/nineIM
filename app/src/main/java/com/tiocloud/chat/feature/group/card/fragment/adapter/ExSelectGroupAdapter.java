package com.tiocloud.chat.feature.group.card.fragment.adapter;

import com.tiocloud.chat.feature.group.card.fragment.adapter.model.MultiModel;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.util.ArrayList;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020/3/17
 * desc :
 */
public class ExSelectGroupAdapter extends SelectGroupAdapter {

    // 列表数据远
    private List<MultiModel> mMultiModels = new ArrayList<>();

    // ====================================================================================
    // data
    // ====================================================================================

    /**
     * 设置数据
     *
     * @param friends
     * @param keyWord
     */
    public void updateData(List<MailListResp.Group> friends, String keyWord) {
        // 设置搜索关键词
        setKeyWord(keyWord);
        // 解析数据
        parseMultiModels(friends);
        // 设置列表数据
        setNewData(mMultiModels);
    }

    private void parseMultiModels(List<MailListResp.Group> friends) {
        if (friends == null) friends = new ArrayList<>(0);

        mMultiModels.clear();

        int friendSize = friends.size();
        for (int i = 0; i < friendSize; i++) {
            MailListResp.Group group = friends.get(i);

            // 添加群聊 model
            mMultiModels.add(new MultiModel(group));
        }
    }
}
