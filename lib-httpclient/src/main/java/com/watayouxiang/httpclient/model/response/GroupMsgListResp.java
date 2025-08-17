package com.watayouxiang.httpclient.model.response;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * author : TaoWang
 * date : 2020-02-18
 * desc :
 */
public class GroupMsgListResp extends ArrayList<GroupMsgListResp.List> implements Serializable {
    
    public static class List implements Serializable {
        /**
         * avatar : /user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg
         * c : 123456
         * ct : 1
         * d : 2
         * f : 23436
         * g : 128
         * mid : 346183
         * nick : wata
         * sendbysys : 2
         * sigleflag : 2
         * sigleuid : -1
         * t : 2020-02-18 15:00:11
         * whereflag : 2
         * whereuid :
         */

        public String avatar;
        public String c;
        public int ct;
        public int d;
        public int f;
        public String g;
        public String mid;
        public String nick;
        public int sendbysys;
        public int sigleflag;
        public int sigleuid;
        public String t;
        public int whereflag;
        public String whereuid;
    }
}
