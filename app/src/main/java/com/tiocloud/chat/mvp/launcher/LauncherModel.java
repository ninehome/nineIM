package com.tiocloud.chat.mvp.launcher;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.lzy.okgo.cache.CacheMode;
import com.tiocloud.account.TioAccount;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.main.fragment.Nav1Fragment;
import com.tiocloud.chat.preferences.ConfigPreferences;
import com.tiocloud.jpush.utils.LogUtils;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.androidutils.yanxun.ConstantUtils;
import com.watayouxiang.db.dao.CacheTableCrud;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.httpclient.callback.TioCallbackImpl;
import com.watayouxiang.httpclient.model.request.ConfigReq;
import com.watayouxiang.httpclient.model.response.ConfigResp;
import com.watayouxiang.httpclient.prefernces.HttpPreferences;

/**
 * author : TaoWang
 * date : 2020-02-12
 * desc :
 */
public class LauncherModel extends LauncherContract.Model {
    @Override
    public void requestConfig(final DataProxy<Void> proxy) {
        ConfigReq configReq = new ConfigReq();
        configReq.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
        configReq.setCancelTag(this);
        configReq.get(new TioCallbackImpl<ConfigResp>() {
            @Override
            public void onTioSuccess(ConfigResp configResp) {
                TioLogger.d("req config success: " + configResp.toString());
                // 存储资源服务器地址
                HttpPreferences.saveResUrl(configResp.res_server);
                // 存储cookieName
                HttpPreferences.saveSessionCookieName(configResp.session_cookie_name);
                // 存储心跳超时时长
                ConfigPreferences.saveImHeartbeatTimeout(configResp.im_heartbeat_timeout);

                ConstantUtils.checkCode = configResp.conf.get("validateType") != null && configResp.conf.get("validateType").equals("1");
                ConstantUtils.checkEmail = configResp.conf.get("validateType") != null && configResp.conf.get("validateType").equals("2");
//                ConstantUtils.websites = configResp.website;
                Nav1Fragment.findItems = configResp.website;
                TioAccount.inviteEnable = "1".equals(configResp.conf.get("inviteEnable"));
                LogUtils.i("zlb==config==>>"+new Gson().toJson(configResp.conf));
                TioConfig.OpenCloseConfig.confMap = configResp.conf;
                TioAccount.sitename = TioConfig.OpenCloseConfig.getSitename();
                // 回调
                proxy.onSuccess(null);

                CacheTableCrud.insertOrUpdate("currentConfig", configResp);
            }

            @Override
            public void onTioError(String msg) {
                TioLogger.d("req config error: " + msg);
                ConfigResp configResp = CacheTableCrud.getValue("currentConfig", ConfigResp.class);
                if (configResp == null){
                    if (msg != null) {
                        proxy.onFailure(msg);
                    }
                }else {
                    HttpPreferences.saveResUrl(configResp.res_server);
                    // 存储cookieName
                    HttpPreferences.saveSessionCookieName(configResp.session_cookie_name);
                    // 存储心跳超时时长
                    ConfigPreferences.saveImHeartbeatTimeout(configResp.im_heartbeat_timeout);

                    ConstantUtils.checkCode = configResp.conf.get("validateType") != null && configResp.conf.get("validateType").equals("1");
                    ConstantUtils.checkEmail = configResp.conf.get("validateType") != null && configResp.conf.get("validateType").equals("2");
//                ConstantUtils.websites = configResp.website;
                    Nav1Fragment.findItems = configResp.website;
                    TioAccount.inviteEnable = "1".equals(configResp.conf.get("inviteEnable"));
                    LogUtils.i("zlb==config==>>"+new Gson().toJson(configResp.conf));
                    TioConfig.OpenCloseConfig.confMap = configResp.conf;
                    TioAccount.sitename = TioConfig.OpenCloseConfig.getSitename();
                    // 回调
                    proxy.onSuccess(null);
                }
            }
        });
    }

    @Override
    public boolean isLogin() {
        return CurrUserTableCrud.curr_isLogin();
    }
}
