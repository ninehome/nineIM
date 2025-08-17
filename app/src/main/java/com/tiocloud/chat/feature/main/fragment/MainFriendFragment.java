package com.tiocloud.chat.feature.main.fragment;

import android.view.View;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.home.friend.FriendFragment;
import com.tiocloud.chat.feature.main.base.MainTabFragment;
import com.tiocloud.chat.feature.search.curr.SearchActivity;
import com.tiocloud.chat.widget.titlebar.HomeTitleBar;
import com.watayouxiang.androidutils.page.TioActivity;

/**
 * author : TaoWang
 * date : 2020-01-10
 * desc : 联系人
 * <p>
 * tabData: {@link com.tiocloud.chat.feature.main.model.MainTab#FRIEND}
 */
public class MainFriendFragment extends MainTabFragment {

    private HomeTitleBar homeTitleBar;
    private FriendFragment fragment;

    @Override
    protected void onInit() {
        homeTitleBar = findViewById(R.id.homeTitleBar);

        setStatusBarCustom(findViewById(R.id.fl_statusBar));
        homeTitleBar.setTitle(getString(getTabData().titleId));

        fragment = new FriendFragment();
        fragment.setContainerId(R.id.friend_fragment_container);
        TioActivity tioActivity = (TioActivity) getActivity();
        if (tioActivity != null) {
            tioActivity.replaceFragment(fragment);
        }
        findViewById(R.id.ll_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(v.getContext());
            }
        });
    }

    @Override
    public void onPageShow(int count, boolean isInit) {
        super.onPageShow(count, isInit);
        setStatusBarLightMode(true);
    }

    public void setAppendTitle(int friendNum) {
        if (homeTitleBar != null) {
            homeTitleBar.setAppendTitle(String.valueOf(friendNum));
        }
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        if (fragment != null) {
            fragment.onRefresh();
        }
    }
}
