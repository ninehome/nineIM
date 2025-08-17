package com.watayouxiang.imclient.model.body.wx;

/**
 * author : TaoWang
 * date : 2020/3/12
 * desc :
 */
public class WxChatItemInfoResp {
    /**
     * chatlinkid : 1250
     * data : {"atnotreadcount":0,"atreadflag":1,"avatar":"/user/base/avatar/20200115/7/1433081217333877887082496.png","bizid":"22626","bizrole":2,"chatmode":1,"createtime":"2020-03-11 17:47:05","fidkey":"23436_22626","focusflag":2,"fromnick":"","id":"1250","joinnum":2,"linkflag":1,"linkid":"33088","msgresume":"","name":"备注一下20","notreadcount":0,"readflag":1,"sysflag":2,"topflag":2,"toreadflag":1,"uid":23436,"updatetime":"2020-03-12 09:09:47","viewflag":1}
     */

    public String chatlinkid;
    public DataBean data;

    public static class DataBean {
        /**
         * atnotreadcount : 0
         * atreadflag : 1
         * avatar : /user/base/avatar/20200115/7/1433081217333877887082496.png
         * bizid : 22626
         * bizrole : 2
         * chatmode : 1
         * createtime : 2020-03-11 17:47:05
         * fidkey : 23436_22626
         * focusflag : 2
         * fromnick :
         * id : 1250
         * joinnum : 2
         * linkflag : 1
         * linkid : 33088
         * msgresume :
         * name : 备注一下20
         * notreadcount : 0
         * readflag : 1
         * sysflag : 2
         * topflag : 2
         * toreadflag : 1
         * uid : 23436
         * updatetime : 2020-03-12 09:09:47
         * viewflag : 1
         */

        public int atnotreadcount;
        public int atreadflag;
        public String avatar;
        /**
         * 好友uid / groupId
         */
        public String bizid;
        public int bizrole;
        public int chatmode;
        public String createtime;
        public String fidkey;
        public int focusflag;
        public String fromnick;
        public String id;
        public int joinnum;
        /**
         * 聊天会话是否有效：1：有效；2：无效
         */
        public int linkflag;
        /**
         * 好友关系id / 群的用户id
         */
        public String linkid;
        public String msgresume;
        public String name;
        public int notreadcount;
        public int readflag;
        public int sysflag;
        public int topflag;
        public int toreadflag;
        public int uid;
        public String updatetime;
        public int viewflag;
    }
}
