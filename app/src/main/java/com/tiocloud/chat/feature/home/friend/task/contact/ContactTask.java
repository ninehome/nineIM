package com.tiocloud.chat.feature.home.friend.task.contact;

import android.os.AsyncTask;

import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.tiocloud.chat.feature.home.friend.adapter.model.item.ContactItem;
import com.tiocloud.chat.feature.home.friend.adapter.model.IData;
import com.tiocloud.chat.feature.home.friend.adapter.model.IItem;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.model.request.WxMyFriendsReq;
import com.watayouxiang.httpclient.model.response.WxMyFriendsResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020-01-21
 * desc : 获取数据列表的任务
 */
public class ContactTask extends AsyncTask/*<Params, Progress, Result>*/<Void, Object, Void> {

    private final ContactTaskProxy proxy;

    public ContactTask(ContactTaskProxy proxy) {
        this.proxy = proxy;
    }

    // =========================================================================================
    // Override
    // =========================================================================================

    @Override
    protected Void doInBackground(Void... voids) {

        IData data = new IData();

        List<? extends IItem> prepares = proxy.onTaskPrepare();
        if (prepares != null) {
            data.addAll(prepares);
            data.build();
            publish(data, null);
        }

        runTask(data, 1);

        return null;
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);

        IData data = (IData) values[0];
        Throwable throwable = (Throwable) values[1];

        proxy.onTaskUpdate(data, throwable);
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        proxy.onTaskDone(this);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        TioHttpClient.cancel(ContactTask.this);
    }

    // =========================================================================================
    // 处理、发布数据
    // =========================================================================================

    private void runTask(IData data, int pageNumber) {
        try {
            // 请求远程好友列表
            WxMyFriendsResp resp = requestFriends(pageNumber);

            // 处理好友列表
            for (WxMyFriendsResp.ListBean friend : resp.list) {
                data.add(new ContactItem(new ContactModel(friend)));
            }
            data.build();
            publish(data, null);

            // 处理下一页数据
            if (!resp.lastPage) {
                runTask(data, ++pageNumber);
            }
        } catch (Throwable throwable) {
            publish(null, throwable);
        }
    }

    /**
     * 获取好友列表(同步请求)
     *
     * @return
     */
    private WxMyFriendsResp requestFriends(int pageNumber) throws Throwable {
        WxMyFriendsReq req = new WxMyFriendsReq(pageNumber + "");
        req.setCancelTag(ContactTask.this);
        req.setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST);
        Response<BaseResp<WxMyFriendsResp>> response = TioHttpClient.get(req);

        BaseResp<WxMyFriendsResp> resp = response.body();
        if (resp == null) {
            throw response.getException();
        }
        WxMyFriendsResp data = resp.getData();
        if (data == null) {
            throw new NullPointerException("TioRespData null: " + resp.getMsg());
        }
        return data;
    }

    /**
     * 更新到主线程
     *
     * @param data      数据
     * @param throwable 异常
     */
    private void publish(IData data, Throwable throwable) {
        if (data != null) {
            // 由于 data 会在子线程中持续被修改，所以深拷贝一份
            publishProgress(data.clone(), throwable);
        } else {
            publishProgress(null, throwable);
        }
    }
}
