package com.watayouxiang.httpclient.model.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-03
 * desc :
 */
public class WxRecentListResp extends ArrayList<WxRecentListResp.Recent> {

    public static class Recent implements Serializable {
        /**
         * nick : t-io更优质的朋友圈
         * uid : 23885
         * ct : 1
         * groupid : 28
         * notreadcount : 0
         * sendbysys : 2
         * text : 123
         * time : 2020-02-01 00:40:21
         * id : 346045
         * avatar : /wx/group/avatar/50/8931/1119484/88097537/74541310905/72/183103/wxgroup_avatar_sm.jpg
         * readflag : 1
         * readflag2 : 2
         * touid : 23374
         * roles : [7,99]
         * remarkname : 官方人员
         */

        public String nick;
        public int uid;
        public int ct;
        public String groupid;
        public String notreadcount;
        public int sendbysys;
        public String text;
        public String time;
        public String id;
        public String avatar;
        public int readflag;
        public int readflag2;
        public int touid;
        public String remarkname;
        public List<Integer> roles;
    }
}
