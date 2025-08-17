package com.tiocloud.chat.feature.home.friend.task.maillist;

import android.os.AsyncTask;

import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.tiocloud.chat.feature.home.friend.adapter.model.IData;
import com.tiocloud.chat.feature.home.friend.adapter.model.item.ContactItem;
import com.tiocloud.chat.feature.home.friend.adapter.model.item.NumCountItem;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.MailListReq;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.util.List;


public class MailListTask extends AsyncTask/*<Params, Progress, Result>*/<IData, Void, MailListTaskData> {

    private final MailListTaskProxy proxy;
    private boolean isCancel;

    public MailListTask(MailListTaskProxy proxy) {
        this.proxy = proxy;
        isCancel = false;
    }

    // =========================================================================================
    // Override
    // =========================================================================================

    @Override
    protected MailListTaskData doInBackground(IData... params) {

        // 获取参数
        IData iData;
        IData param = params[0];
        if (param != null) {
            iData = param;
        } else {
            iData = new IData();
        }

        // 同步请求数据
        MailListReq req = new MailListReq("1", null);
        req.setCancelTag(this);
        req.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
        Response<BaseResp<MailListResp>> response = req.get();

        if (!response.isSuccessful()) {
            return new MailListTaskData(response.getException().getMessage());
        }
        BaseResp<MailListResp> body = response.body();
        MailListResp data = body.getData();
        if (data == null) {
            return new MailListTaskData("data null");
        }
        List<MailListResp.Friend> fd = data.fd;
        if (fd == null) {
            return new MailListTaskData("data.fd null");
        }
        // 联系人项
        for (MailListResp.Friend f : fd) {
            iData.add(new ContactItem(new MailListModel(f)));
        }
        // 个数统计项
        iData.add(new NumCountItem(fd.size()));
        iData.build();

        return new MailListTaskData(iData, fd.size());
    }

    @Override
    protected void onPostExecute(MailListTaskData result) {
        super.onPostExecute(result);
        if (proxy != null && !isCancel) {
            proxy.onTaskDone(result);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        TioHttpClient.cancel(MailListTask.this);
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }
}
