package com.tiocloud.chat.feature.main.model;

import android.content.Context;
import android.view.MenuItem;
import android.widget.Toast;

import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.widget.dialog.progress.SingletonProgressDialog;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.request.UserCurrReq;
import com.watayouxiang.httpclient.model.response.UserCurrResp;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.androidutils.page.OptionsMenu;
import com.watayouxiang.androidutils.page.TioActivity;

/**
 * author : TaoWang
 * date : 2020-01-07
 * desc : 主页菜单
 */
public class MainMenu implements OptionsMenu {
    @Override
    public int getMenuId() {
        return R.menu.tio_main_activity_menu;
    }

    @Override
    public void onOptionsItemSelected(Context context, MenuItem item) {
        if (item.getItemId() == R.id.create_group) {

        } else if (item.getItemId() == R.id.add_friend) {

        } else if (item.getItemId() == R.id.invite_friend) {

        }
    }

    private void userCurr(final TioActivity activity) {
        SingletonProgressDialog.show_unCancel(activity, activity.getString(R.string.user_curr_info));
        UserCurrReq req = new UserCurrReq();
        req.setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST);
        TioHttpClient.get(activity, new UserCurrReq(), new TaoCallback<BaseResp<UserCurrResp>>() {
            @Override
            public void onCacheSuccess(Response<BaseResp<UserCurrResp>> response) {
                super.onCacheSuccess(response);
                BaseResp<UserCurrResp> body = response.body();
                Toast.makeText(activity, "缓存数据：" + body.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(Response<BaseResp<UserCurrResp>> response) {
                BaseResp<UserCurrResp> body = response.body();
                Toast.makeText(activity, body.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                SingletonProgressDialog.dismiss();
            }
        });
    }
}
