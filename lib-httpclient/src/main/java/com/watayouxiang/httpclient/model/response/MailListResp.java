package com.watayouxiang.httpclient.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-14
 * desc :
 */
public class MailListResp implements Serializable {

    public List<Friend> fd;

    public static class Friend implements Serializable {
        /**
         * nick : wata
         * chatindex : W
         * uid : 23436
         * id : 1158
         * avatar : /user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg
         * remarkname : wata测试
         */

        public String nick;
        public String chatindex;
        public int uid;
        public String id;
        public String avatar;
        public String remarkname;

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getRemarkname() {
            return remarkname;
        }

        public void setRemarkname(String remarkname) {
            this.remarkname = remarkname;
        }
    }

    public List<Group> group;

    public static class Group implements Serializable {
        /**
         * uid : 23436
         * joinnum : 1
         * groupid : 128
         * name : wata、叶孤城、wata
         * avatar : /wx/group/avatar/22/9010/1119563/88097616/74541310984/13/135447/1229645414790209536_sm.jpg
         */

        public int uid;// 群主uio
        public int joinnum;
        public String groupid;
        public String name;
        public String avatar;
    }
}
