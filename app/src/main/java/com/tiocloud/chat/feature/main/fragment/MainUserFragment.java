package com.tiocloud.chat.feature.main.fragment;

import com.jaeger.library.StatusBarUtil;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.home.user.UserFragment;
import com.tiocloud.chat.feature.main.base.MainTabFragment;
import com.watayouxiang.androidutils.page.TioActivity;

/**
 * author : TaoWang
 * date : 2020-01-10
 * desc : 我的
 * <p>
 * tabData: {@link com.tiocloud.chat.feature.main.model.MainTab#USER}
 */
public class MainUserFragment extends MainTabFragment {

    private UserFragment fragment;

    @Override
    protected void onInit() {
//        setStatusBarCustom(findViewById(R.id.fl_statusBar));
        fragment = new UserFragment();
        fragment.setContainerId(R.id.user_fragment_container);
        TioActivity tioActivity = (TioActivity) getActivity();
        if (tioActivity != null) {
            tioActivity.replaceFragment(fragment);
        }
    }

    @Override
    public void onPageShow(int count, boolean isInit) {
        super.onPageShow(count, isInit);
        setStatusBarLightMode(false);
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        if (fragment != null) {
            fragment.onRefresh();
        }
    }
}
