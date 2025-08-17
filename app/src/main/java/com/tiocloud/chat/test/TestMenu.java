package com.tiocloud.chat.test;

import android.content.Context;
import android.view.MenuItem;

import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.page.OptionsMenu;

/**
 * author : TaoWang
 * date : 2020-01-04
 * desc : 测试OptionsMenu
 */
public class TestMenu implements OptionsMenu {
    @Override
    public int getMenuId() {
        return R.menu.tio_test_menu;
    }

    @Override
    public void onOptionsItemSelected(Context context, MenuItem item) {
        if (item.getItemId() == R.id.test_item) {
            TestActivity.start(context);
        }
    }

}
