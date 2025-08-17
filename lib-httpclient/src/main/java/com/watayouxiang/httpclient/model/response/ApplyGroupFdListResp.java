package com.watayouxiang.httpclient.model.response;

import java.util.ArrayList;

/**
 * author : TaoWang
 * date : 2020/3/4
 * desc :
 */
public class ApplyGroupFdListResp extends ArrayList<ApplyGroupFdListResp.Friend> {
    public static class Friend {
        /**
         * nick : wata
         * uid : 23436
         * groupnick :
         * groupid : 182
         * id : 990
         * avatar : /user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg
         * grouprole : 1
         */

        public String nick;
        public int uid;
        public String groupnick;
        public String groupid;
        public String id;
        public String avatar;
        public String remarkname;
        public int grouprole;
    }
}
