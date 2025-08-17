package com.watayouxiang.httpclient.model.response;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020/2/27
 * desc :
 */
public class GroupUserListResp {
    public boolean firstPage;
    public boolean lastPage;
    public List<GroupMember> list;
    public int pageNumber;
    public int pageSize;
    public int totalPage;
    public int totalRow;

    public static class GroupMember {
        /**
         * nick : wata
         * uid : 23436
         * groupnick :
         * groupid : 138
         * id : 721
         * avatar : /user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg
         * grouprole : 1
         */

        public String nick;
        public int uid;
        public String groupnick;
        public String groupid;
        public String id;
        public String avatar;
        /**
         * 群成员角色：1：群主；2：成员 3、群管
         */
        public int grouprole;

        public boolean isGroupOwner() {
            return grouprole == 1;
        }
    }
}
