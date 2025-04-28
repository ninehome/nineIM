package com.watayouxiang.httpclient.model.response;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class CreateGroupResp {
    /**
     * avatar : /wx/group/avatar/22/9010/1119563/88097616/74541310984/72/114424/1232511707876827136_sm.jpg
     * id : 139
     * joinnum : 3
     * name : test
     * uid : 23436
     */

    public String avatar;
    /**
     * 群组id
     */
    public String id;
    public int joinnum;
    public String name;
    public int uid;

    public String getGroupId() {
        return id;
    }
}
