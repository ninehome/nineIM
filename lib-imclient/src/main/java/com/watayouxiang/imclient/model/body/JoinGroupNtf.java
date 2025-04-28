package com.watayouxiang.imclient.model.body;

import java.util.List;

public class JoinGroupNtf extends BaseResp {

    /**
     * cid : 1197430042578264064
     * g : _$x-__allinone
     * ipcount : 113
     * online : 265
     * u : {"a":"/avatar/wxnv3/2018/05/15/16.jpg","cid":"1197430042578264064","groupid":"/doc/tio/41","i":29895,"ipInfo":{"area":"","city":"香港","country":"中国","id":970074,"ip":"103.119.128.212","operator":"","province":"香港","time":"2019-09-02 18:37:42"},"l":1,"ln":"xyf871212@163.com","n":"ejon","r":[2,7,8],"timeCreated":1574324543974,"timeJoinGroup":1574329769804,"userAgent":{"agentName":"Chrome","agentVersionMajor":"78","deviceClass":"Desktop","id":15430,"isMobile":2,"osName":"Windows NT","osVersion":"7","syntaxError":false,"userAgent":"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36"},"x":2}
     */

    public String cid;
    public String g;
    public int ipcount;
    public int online;
    public UBean u;

    public static class UBean {
        /**
         * a : /avatar/wxnv3/2018/05/15/16.jpg
         * cid : 1197430042578264064
         * groupid : /doc/tio/41
         * i : 29895
         * ipInfo : {"area":"","city":"香港","country":"中国","id":970074,"ip":"103.119.128.212","operator":"","province":"香港","time":"2019-09-02 18:37:42"}
         * l : 1
         * ln : xyf871212@163.com
         * n : ejon
         * r : [2,7,8]
         * timeCreated : 1574324543974
         * timeJoinGroup : 1574329769804
         * userAgent : {"agentName":"Chrome","agentVersionMajor":"78","deviceClass":"Desktop","id":15430,"isMobile":2,"osName":"Windows NT","osVersion":"7","syntaxError":false,"userAgent":"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36"}
         * x : 2
         */

        public String a;
        public String cid;
        public String groupid;
        public int i;
        public IpInfoBean ipInfo;
        public int l;
        public String ln;
        public String n;
        public long timeCreated;
        public long timeJoinGroup;
        public UserAgentBean userAgent;
        public int x;
        public List<Integer> r;
    }

    public static class IpInfoBean {
        /**
         * area :
         * city : 香港
         * country : 中国
         * id : 970074
         * ip : 103.119.128.212
         * operator :
         * province : 香港
         * time : 2019-09-02 18:37:42
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
         * id : 15430
         * isMobile : 2
         * osName : Windows NT
         * osVersion : 7
         * syntaxError : false
         * userAgent : Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36
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
