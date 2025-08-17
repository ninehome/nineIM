package com.watayouxiang.httpclient.model.response;

import java.io.Serializable;

/**
 * author : TaoWang
 * date : 2020-01-06
 * desc :
 */
public class TransDetailResp implements Serializable {
    public long id;
    public String createtime;
    public String updatetime;
    public long fromUid;
    public String fromNick;
    public long toUid;
    public String toNick;
    public int amount;
    public int status;
    public String serial;
    public String recievertime;
    public String backtime;
    public String remark;
}
