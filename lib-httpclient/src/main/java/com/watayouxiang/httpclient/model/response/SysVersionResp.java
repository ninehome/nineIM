package com.watayouxiang.httpclient.model.response;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/18
 *     desc   :
 * </pre>
 */
public class SysVersionResp {
    /**
     * 是否有新版本：1：是；2：否
     */
    private int updateflag;
    /**
     * 强制更新标识：1：是；2：否
     */
    private int forceflag;
    /**
     * 返回版本号
     */
    private String version;
    /**
     * 文件路径
     */
    private String url;
    /**
     * 更新内容
     */
    private String content;

    public int getUpdateflag() {
        return updateflag;
    }

    public void setUpdateflag(int updateflag) {
        this.updateflag = updateflag;
    }

    public int getForceflag() {
        return forceflag;
    }

    public void setForceflag(int forceflag) {
        this.forceflag = forceflag;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SysVersionResp{" +
                "updateflag=" + updateflag +
                ", forceflag=" + forceflag +
                ", version='" + version + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
