package com.watayouxiang.httpclient.model.response;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * author : TaoWang
 * date : 2020-01-23
 * desc :
 */
public class WxGroupListResp extends ArrayList<WxGroupListResp.Group> {

    public static class Group implements Serializable {
        /**
         * uid : 23357
         * groupavatar : /wx/group/avatar/50/8931/1119484/88097537/74541310905/72/183103/wxgroup_avatar_sm.jpg
         * createtime : 2019-10-01 18:31:03
         * groupid : 28
         * groupname : t-io更优质的朋友圈
         */

        public int uid;
        public String groupavatar;
        public String createtime;
        public String groupid;
        public String groupname;
    }

}
