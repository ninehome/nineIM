package com.watayouxiang.httpclient.model.response;

import com.watayouxiang.httpclient.model.response.internal.ChatListBean;
import com.watayouxiang.httpclient.model.response.internal.SynItemBean;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/28
 *     desc   :
 * </pre>
 */
public class UploadCommonFileResp {
    private String url;

    private String filename;

    private long size;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
