package com.tiocloud.chat.feature.session.group.customization;

import android.content.Context;
import android.view.MenuItem;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.group.info.GroupInfoActivity;
import com.watayouxiang.androidutils.page.OptionsMenu;

/**
 * author : TaoWang
 * date : 2020/3/19
 * desc :
 */
public class GroupMenu implements OptionsMenu {
    @Override
    public int getMenuId() {
        return R.menu.tio_session_activity_menu;
    }

    @Override
    public void onOptionsItemSelected(Context context, MenuItem item) {
        if (item.getItemId() == R.id.more_btn) {
            GroupInfoActivity.start(context, getGroupId());
        }
    }

    public String getGroupId() {
        return null;
    }
}
