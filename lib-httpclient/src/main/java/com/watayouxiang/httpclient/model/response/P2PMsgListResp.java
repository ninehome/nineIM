package com.watayouxiang.httpclient.model.response;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * author : TaoWang
 * date : 2020-02-11
 * desc :
 */
public class P2PMsgListResp extends ArrayList<P2PMsgListResp.List> implements Serializable {
    public static class List implements Serializable {

        /**
         * avatar : /user/base/avatar/20200115/1/1432471217333790939160576.png
         * c : 好
         * ct : 1
         * mid : 371664
         * msgtype : 1
         * nick : 叶孤城
         * readflag : 1
         * readtime : 2020-02-11 11:03:08
         * sendbysys : 2
         * sigleflag : 2
         * t : 2020-02-06 20:15:07
         * touid : 23436
         * uid : 23440
         */

        public String avatar;
        public String c;
        public int ct;
        public String mid;
        public int msgtype;
        public String nick;
        public int readflag;
        public String readtime;
        public int sendbysys;
        public int sigleflag;
        public String t;
        public int touid;
        public int uid;
    }
}
