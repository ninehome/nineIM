package com.watayouxiang.httpclient.model.response;

import com.watayouxiang.httpclient.R;
import com.watayouxiang.httpclient.utils.Constants;

import java.io.Serializable;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020-01-07
 * desc :
 */
public class UserCurrResp implements Serializable {

    /*
    {"data":{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624_sm.jpg","avatarbig":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624.jpg","createtime":"2018-11-08 09:08:29","domain":"","email":"watayouxiang@qq.com","emailbindflag":1,"emailpwd":"bc645234eee78a17bb01152901395210","fdvalidtype":1,"id":23436,"invFlag":false,"invitecode":"854135","ipInfo":{"area":"","city":"杭州市","country":"中国","operator":"联通","province":"浙江省"},"ipid":164340,"level":1,"loginname":"watayouxiang@qq.com","mg":false,"msgforbiddenflag":2,"msgremindflag":1,"nick":"wataw","openflag":2,"phone":"18768177675","phonebindflag":1,"phonepwd":"a18c012c4b5540bfe536db3cde448c37","reghref":"https://www.t-io.org","registertype":1,"remark":"王涛","roles":[2,6,7],"searchflag":1,"sex":3,"sign":"wata主账号","status":1,"thirdbindflag":2,"thirdstatus":1,"thirdtype":5,"updatetime":"2020-12-24 16:42:47","xx":2},"ok":true}

    {
        "data":{
            "avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624_sm.jpg",
            "avatarbig":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624.jpg",
            "createtime":"2018-11-08 09:08:29",
            "domain":"",
            "email":"watayouxiang@qq.com",
            "emailbindflag":1,
            "emailpwd":"bc645234eee78a17bb01152901395210",
            "fdvalidtype":1,
            "id":23436,
            "invFlag":false,
            "invitecode":"854135",
            "ipInfo":{
                "area":"",
                "city":"杭州市",
                "country":"中国",
                "operator":"联通",
                "province":"浙江省"
            },
            "ipid":164340,
            "level":1,
            "loginname":"watayouxiang@qq.com",
            "mg":false,
            "msgforbiddenflag":2,
            "msgremindflag":1,
            "nick":"wataw",
            "openflag":2,
            "phone":"18768177675",
            "phonebindflag":1,
            "phonepwd":"a18c012c4b5540bfe536db3cde448c37",
            "reghref":"https://www.t-io.org",
            "registertype":1,
            "remark":"王涛",
            "roles":[
                2,
                6,
                7
            ],
            "searchflag":1,
            "sex":3,
            "sign":"wata主账号",
            "status":1,
            "thirdbindflag":2,
            "thirdstatus":1,
            "thirdtype":5,
            "updatetime":"2020-12-24 16:42:47",
            "xx":2
        },
        "ok":true
    }
     */

    public EextData extData;
    public String avatar;
    public String avatarbig;
    public String createtime;
    public String domain;
    public int id;
    public boolean invFlag;
    public String invitecode;
    public IpInfoBean ipInfo;
    public int ipid;
    public int level;
    public String loginname;
    public boolean mg;
    public String nick;
    public String reghref;
    public int registertype;
    public String remark;
    public int status;
    public int thirdstatus;
    public String updatetime;
    public int xx;
    public List<String> roles;
    /**
     * 消息提醒开关：1：开启；2：不开启
     */
    public int msgremindflag;
    /**
     * 手机号
     */
    public String phone;
    /**
     * 手机绑定状态：1 绑定，2 未绑定
     */
    public int phonebindflag;
    /**
     * 三方绑定状态：1 绑定，2 未绑定
     */
    public int thirdbindflag;
    /**
     * 邮箱
     */
    public String email;
    /**
     * 性别：1：男；2：女；3:保密
     */
    public int sex;
    /**
     * 签名
     */
    public String sign;
    /**
     * 好友验证-开关：1：开启验证；2：不开启
     */
    public int fdvalidtype;
    /**
     * 允许他人搜索-开关：1：开启允许搜索；2：不开启
     */
    public int searchflag;

    public String getSex() {
        switch (sex) {
            case 1:
                return Constants.context.getString(R.string.man);
            case 2:
                return Constants.context.getString(R.string.woman);
            case 3:
                return Constants.context.getString(R.string.baomi);
            default:
                return "";
        }
    }

    public boolean isBindPhone() {
        return phonebindflag == 1;
    }

    public boolean isThirdbindflag() {
        return thirdbindflag == 1;
    }

    public String getRegion() {
        if (ipInfo == null) return "";
        String country = ipInfo.country;
        String province = ipInfo.province;
        String city = ipInfo.city;
        return country + " " + province + " " + city;
    }

    public static class IpInfoBean implements Serializable {
        /**
         * area :
         * city : 杭州市
         * country : 中国
         * operator : 联通
         * province : 浙江省
         */

        public String area;
        public String city;
        public String country;
        public String operator;
        public String province;
    }

    //{"uidFind":"0","cardAdd":"1","phoneFind":"0","emailFind":"0",
    // "groupAdd":"0","loginNameFind":"1","qrcodeAdd":"0"}
    public static class EextData implements Serializable {
        /**
         * area :
         * city : 杭州市
         * country : 中国
         * operator : 联通
         * province : 浙江省
         */

        public String uidFind;
        public String cardAdd;
        public String phoneFind;
        public String emailFind;
        public String groupAdd;
        public String loginNameFind;
        public String qrcodeAdd;
    }

    @Override
    public String toString() {
        return "UserCurrResp{" +
                "loginname='" + loginname + '\'' +
                ", nick='" + nick + '\'' +
                ", remark='" + remark + '\'' +
                ", id=" + id +
                ", roles=" + roles +
                '}';
    }
}
