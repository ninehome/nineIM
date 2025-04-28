package com.watayouxiang.imclient.model.body;

import com.watayouxiang.imclient.model.JoinGroupResult;

public class JoinGroupResp extends BaseResp {
    /**
     * groupId
     */
    public String g;
    /**
     * {@link JoinGroupResult}
     */
    public Byte result;
}
