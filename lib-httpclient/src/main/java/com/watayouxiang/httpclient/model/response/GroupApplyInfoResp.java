package com.watayouxiang.httpclient.model.response;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-20
 * desc :
 */
public class GroupApplyInfoResp {
    public GroupApply apply;

    public List<GroupApplyItem> items;

    public static class GroupApplyItem{
        public Integer id;
        public Integer aid;
        public Integer groupid;
        public Integer uid;
        public Integer status;
        public String createtime;
        public String updatetime;
        public String nick;
        public String avatar;
        public String phone;
        public String email;
    }

    public static class GroupApply{
        public Integer id;
        public Integer groupid;
        public Integer operuid;
        public String applymsg;
        public Integer status;
        public String createtime;
        public String updatetime;

        public String groupnick;
        public String srcnick;
        public String groupavator;
        public String grouprole;
    }
}
