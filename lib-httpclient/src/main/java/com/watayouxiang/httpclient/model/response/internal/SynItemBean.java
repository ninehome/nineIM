package com.watayouxiang.httpclient.model.response.internal;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/28
 *     desc   :
 * </pre>
 */
public class SynItemBean {
    /**
     * createtime : 2020-08-28 14:47:27
     * devicetype : 5
     * id : 6
     * syntype : 1
     * uid : 23436
     * updatetime : 2020-08-28 14:47:27
     */

    private String createtime;
    private int devicetype;
    private int id;
    private int syntype;
    private int uid;
    private String updatetime;

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(int devicetype) {
        this.devicetype = devicetype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSyntype() {
        return syntype;
    }

    public void setSyntype(int syntype) {
        this.syntype = syntype;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
