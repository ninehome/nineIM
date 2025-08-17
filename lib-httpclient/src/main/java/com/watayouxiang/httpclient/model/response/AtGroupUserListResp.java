package com.watayouxiang.httpclient.model.response;

import java.util.ArrayList;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/24
 *     desc   :
 * </pre>
 */
public class AtGroupUserListResp extends ArrayList<AtGroupUserListResp.List> {
    public static class List {
        public List(String remarkname, String srcnick, int uid) {
            this.srcnick = srcnick;
            this.remarkname = remarkname;
            this.uid = uid;
        }
        /**
         * nick : Liwata
         * uid : 25405
         * groupid : 1
         * id : 46241
         * avatar : /avatar/tio/20200708/4.jpg
         * grouprole : 2
         * srcnick : Liwata
         */

        /**
         * 昵称
         */
        public String srcnick;
        /**
         * 备注名
         */
        public String remarkname;
        /**
         * 群昵称
         */
        public String nick;
        public int uid;
        public String groupid;
        public String id;
        public String avatar;
        public int grouprole;
    }
}
