package com.watayouxiang.httpclient.listener;

/**
 * author : TaoWang
 * date : 2020/4/10
 * desc :
 */
public interface KickOutListener {
    /**
     * 踢出登录
     */
    void onKickOut(String msg);
}
