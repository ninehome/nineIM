package com.watayouxiang.httpclient.model.response;

import android.content.Intent;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-14
 * desc :
 */
public class LableListResp implements Serializable {

    public List<Lable> groupList;

    public static class Lable implements Serializable {
        /**
         * nick : wata
         * chatindex : W
         * uid : 23436
         * id : 1158
         * avatar : /user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg
         * remarkname : wata测试
         */
        public Integer id;
        public Integer uid;
        public String groupname;
        public String createtime;

        public List<MailListResp.Friend> friendidlist;

        public List<MailListResp.Friend> getFriendidlist() {
            return friendidlist;
        }

        public void setFriendidlist(List<MailListResp.Friend> friendidlist) {
            this.friendidlist = friendidlist;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUid() {
            return uid;
        }

        public void setUid(Integer uid) {
            this.uid = uid;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }


    }


}
