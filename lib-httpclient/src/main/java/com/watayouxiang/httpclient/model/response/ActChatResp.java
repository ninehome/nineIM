package com.watayouxiang.httpclient.model.response;

import java.io.Serializable;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public class ActChatResp implements Serializable {
    /**
     * actflag : 2
     * chat : {"atnotreadcount":0,"atreadflag":1,"avatar":"/user/base/avatar/20200115/1/1432471217333790939160576.png","bizid":"23440","bizrole":2,"chatmode":1,"createtime":"2020-02-07 21:16:22","fidkey":"23440_23436","focusflag":2,"fromnick":"wata","id":"9","lastmsgid":"374057","lastmsguid":23436,"linkflag":1,"linkid":"31917","msgresume":"演示中","name":"叶孤城","notreadcount":0,"readflag":1,"sendtime":"2020-02-21 09:54:12","startmsgid":"0","sysflag":2,"topflag":2,"toreadflag":2,"uid":23436,"updatetime":"2020-02-21 09:54:12","viewflag":1}
     * state : ok
     */

    public int actflag;
    public Chat chat;
    public String state;

    public static class Chat implements Serializable {
        /**
         * atnotreadcount : 0
         * atreadflag : 1
         * avatar : /user/base/avatar/20200115/1/1432471217333790939160576.png
         * bizid : 23440
         * bizrole : 2
         * chatmode : 1
         * createtime : 2020-02-07 21:16:22
         * fidkey : 23440_23436
         * focusflag : 2
         * fromnick : wata
         * id : 9
         * lastmsgid : 374057
         * lastmsguid : 23436
         * linkflag : 1
         * linkid : 31917
         * msgresume : 演示中
         * name : 叶孤城
         * notreadcount : 0
         * readflag : 1
         * sendtime : 2020-02-21 09:54:12
         * startmsgid : 0
         * sysflag : 2
         * topflag : 2
         * toreadflag : 2
         * uid : 23436
         * updatetime : 2020-02-21 09:54:12
         * viewflag : 1
         */

        public int atnotreadcount;
        public int atreadflag;
        public String avatar;
        public String bizid;
        public int bizrole;
        public int chatmode;
        public String createtime;
        public String fidkey;
        public int focusflag;
        public String fromnick;
        /**
         * chatLinkId
         */
        public String id;
        public String lastmsgid;
        public int lastmsguid;
        public int linkflag;
        public String linkid;
        public String msgresume;
        public String name;
        public int notreadcount;
        public int readflag;
        public String sendtime;
        public String startmsgid;
        public int sysflag;
        public int topflag;
        public int toreadflag;
        public int uid;
        public String updatetime;
        public int viewflag;
    }
}
