package com.tiocloud.chat.util;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.AppUtils;
import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.appupdate.UpdateManager;
import com.watayouxiang.appupdate.entity.AppUpdate;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.SysVersionReq;
import com.watayouxiang.httpclient.model.response.SysVersionResp;
import com.watayouxiang.httpclient.prefernces.HttpCache;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/18
 *     desc   :
 * </pre>
 */
public class AppUpdateTool {
    @NonNull
    private final FragmentActivity context;

    public AppUpdateTool(@NonNull FragmentActivity context) {
        this.context = context;
    }

    public void checkUpdate() {
        SysVersionReq sysVersionReq = new SysVersionReq(AppUtils.getAppVersionName());
        sysVersionReq.setCancelTag(this);
        sysVersionReq.get(new TioCallback<SysVersionResp>() {
            @Override
            public void onTioSuccess(SysVersionResp sysVersionResp) {
                onCheckUpdateSuccess(sysVersionResp);
            }

            @Override
            public void onTioError(String msg) {
                onCheckUpdateError(msg);
            }
        });
    }

    public void onCheckUpdateSuccess(SysVersionResp sysVersionResp) {
        if (sysVersionResp.getUpdateflag() == 1) {
            startUpdate(sysVersionResp);
        }
    }

    public void onCheckUpdateError(String msg) {
        TioToast.showShort(msg);
    }

    private void startUpdate(SysVersionResp resp) {
        AppUpdate appUpdate = new AppUpdate.Builder(HttpCache.getResUrl(resp.getUrl()))
                .updateResourceId(R.layout.tio_dialog_update)
                .forceUpdate(resp.getForceflag() == 1)
                .updateTitle(String.format(Locale.getDefault(), "发现新版本v%s", resp.getVersion()))
                .updateInfo(resp.getContent())
                .build();
        new UpdateManager(context, appUpdate) {
            @Override
            public void onUpdateToast(@NonNull String msg) {
                super.onUpdateToast(msg);
                TioToast.showShort(msg);
            }
        }.startUpdate();
    }

    public void release() {
        TioHttpClient.cancel(this);
    }
}
