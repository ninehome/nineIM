package com.tiocloud.chat.feature.main.fragment;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.home.group.GroupFragment;
import com.tiocloud.chat.feature.main.base.MainTabFragment;
import com.tiocloud.chat.widget.titlebar.HomeTitleBar;
import com.watayouxiang.androidutils.page.TioActivity;

/**
 * author : TaoWang
 * date : 2020-01-10
 * desc : 群聊
 * <p>
 * tabData: {@link com.tiocloud.chat.feature.main.model.MainTab#GROUP}
 */
public class MainGroupFragment extends MainTabFragment {

    private HomeTitleBar homeTitleBar;
    private GroupFragment fragment;

    @Override
    protected void onInit() {
        homeTitleBar = findViewById(R.id.homeTitleBar);

        setStatusBarCustom(findViewById(R.id.fl_statusBar));
        homeTitleBar.setTitle(getString(getTabData().titleId));

        fragment = new GroupFragment();
        fragment.setContainerId(R.id.group_fragment_container);
        TioActivity tioActivity = (TioActivity) getActivity();
        if (tioActivity != null) {
            tioActivity.replaceFragment(fragment);
        }
    }

    @Override
    public void onPageShow(int count, boolean isInit) {
        super.onPageShow(count, isInit);
        setStatusBarLightMode(true);
    }

    public void setAppendTitle(int groupNum) {
        if (homeTitleBar != null) {
            homeTitleBar.setAppendTitle(String.valueOf(groupNum));
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
