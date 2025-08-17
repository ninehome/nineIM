package com.watayouxiang.imclient.model.body;

public class LeaveGroupNtf extends BaseResp {

    /**
     * g : _$x-__allinone
     * online : 264
     * t : 1574329770214
     * u : {"cid":"1197451955530768384","groupid":"/stat/index.html","ipInfo":{"area":"","city":"杭州市","country":"中国","id":651384,"ip":"124.160.42.146","operator":"联通","province":"浙江省","time":"2019-05-13 09:42:59"},"n":"游客1197451955530768384","timeCreated":1574329768435,"timeJoinGroup":1574329768494,"userAgent":{"agentName":"Chrome","agentVersionMajor":"78","deviceClass":"Desktop","id":15394,"isMobile":2,"osName":"Windows NT","osVersion":"10.0","syntaxError":false,"userAgent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36"},"x":2}
     */

    public String g;
    public int online;
    public String t;
    public UBean u;

    public static class UBean {
        /**
         * cid : 1197451955530768384
         * groupid : /stat/index.html
         * ipInfo : {"area":"","city":"杭州市","country":"中国","id":651384,"ip":"124.160.42.146","operator":"联通","province":"浙江省","time":"2019-05-13 09:42:59"}
         * n : 游客1197451955530768384
         * timeCreated : 1574329768435
         * timeJoinGroup : 1574329768494
         * userAgent : {"agentName":"Chrome","agentVersionMajor":"78","deviceClass":"Desktop","id":15394,"isMobile":2,"osName":"Windows NT","osVersion":"10.0","syntaxError":false,"userAgent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36"}
         * x : 2
         */

        public String cid;
        public String groupid;
        public IpInfoBean ipInfo;
        public String n;
        public long timeCreated;
        public long timeJoinGroup;
        public UserAgentBean userAgent;
        public int x;
    }

    public static class IpInfoBean {
        /**
         * area :
         * city : 杭州市
         * country : 中国
         * id : 651384
         * ip : 124.160.42.146
         * operator : 联通
         * province : 浙江省
         * time : 2019-05-13 09:42:59
         */

        public String area;
        public String city;
        public String country;
        public int id;
        public String ip;
        public String operator;
        public String province;
        public String time;
    }

    public static class UserAgentBean {
        /**
         * agentName : Chrome
         * agentVersionMajor : 78
         * deviceClass : Desktop
         * id : 15394
         * isMobile : 2
         * osName : Windows NT
         * osVersion : 10.0
         * syntaxError : false
         * userAgent : Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36
         */

        public String agentName;
        public String agentVersionMajor;
        public String deviceClass;
        public int id;
        public int isMobile;
        public String osName;
        public String osVersion;
        public boolean syntaxError;
        public String userAgent;
    }
}
