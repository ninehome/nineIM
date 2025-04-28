package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseNtf;

import java.util.Map;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/31
 *     desc   : 焦点通知
 * </pre>
 */
public class WxFocusNtf extends BaseNtf {
    /**
     * focusMap : {"37089":2,"36983":1}
     * t : 1598948358779
     */

    /**
     * key: chatlinkId
     * value:
     * 1 需要将“消息未读数”置为0
     * 2 不做处理
     */
    private Map<String, String> focusMap;
    private String t;

    public Map<String, String> getFocusMap() {
        return focusMap;
    }

    public void setFocusMap(Map<String, String> focusMap) {
        this.focusMap = focusMap;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }
}
