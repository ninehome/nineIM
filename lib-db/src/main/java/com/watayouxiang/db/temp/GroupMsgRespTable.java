package com.watayouxiang.db.temp;

import org.greenrobot.greendao.annotation.Id;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/16
 *     desc   :
 *     {@link com.watayouxiang.imclient.model.body.wx.WxGroupMsgResp}
 * </pre>
 */
public class GroupMsgRespTable {
    @Id
    private long chatlinkid;
    private boolean lastPage;
    private List<GroupMsgTable> data;
}
