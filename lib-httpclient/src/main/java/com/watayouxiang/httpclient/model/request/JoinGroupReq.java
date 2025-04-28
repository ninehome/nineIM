package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/3/4
 * desc : 加入群聊
 *
 * @see String
 */
public class JoinGroupReq extends BaseReq<String> {
    /**
     * 用户列表，逗号分隔
     */
    private final String uids;
    private final String groupid;
    // 邀请人uid-通过群名片加入时，请填写群名片对象中的shareFromUid,其他无须传
    private String applyuid;

    public JoinGroupReq(String uids, String groupid) {
        this.uids = uids;
        this.groupid = groupid;
    }

    public JoinGroupReq(String uids, String groupid, String applyuid) {
        this(uids, groupid);
        this.applyuid = applyuid;
    }

    @Override
    public String path() {
        return "/mytio/chat/joinGroup.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("uids", uids)
                .append("groupid", groupid)
                .append("applyuid", applyuid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
