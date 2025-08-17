package com.watayouxiang.imclient.model.body;

public class JoinGroupReq extends BaseReq {
    /**
     * 群组id
     */
    public String g;
    /**
     * 第几次进入群组，不传、0、1表示第一次，其它值表示第几次
     */
    public Integer c;
}
