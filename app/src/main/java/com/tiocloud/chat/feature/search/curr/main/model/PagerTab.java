package com.tiocloud.chat.feature.search.curr.main.model;

import com.tiocloud.chat.feature.search.curr.all.AllResultFragment;
import com.tiocloud.chat.feature.search.curr.main.base.BaseResultFragment;
import com.tiocloud.chat.feature.search.curr.friend.FriendResultFragment;
import com.tiocloud.chat.feature.search.curr.group.GroupResultFragment;

/**
 * author : TaoWang
 * date : 2020-02-13
 * desc :
 */
public class PagerTab {

    public final BaseResultFragment page;
    public final String title;

    private PagerTab(BaseResultFragment page, String title) {
        this.page = page;
        this.title = title;
    }

    public static PagerTab[] get() {
        PagerTab all = new PagerTab(new AllResultFragment(), "全部");
        PagerTab friend = new PagerTab(new FriendResultFragment(), "好友");
        PagerTab group = new PagerTab(new GroupResultFragment(), "群组");
        return new PagerTab[]{all, friend, group};
    }
}
