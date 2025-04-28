package com.watayouxiang.imclient.model.body;

import java.util.ArrayList;
import java.util.List;

public class GroupChatNtf extends ArrayList {

    /**
     * c : hi~ tio-android
     * cid : 1161969981764804608
     * ct : 1
     * d : 2
     * f : {"a":"/avatar/fengjing2/2018/08/19/8.jpeg","cid":"1161969981764804608","groupid":"/product/index.html","i":23436,"ipInfo":{"area":"","city":"杭州市","country":"中国","id":651384,"ip":"124.160.42.146","operator":"联通","province":"浙江省","time":"2019-05-13 09:42:59"},"l":1,"ln":"watayouxiang@qq.com","mobileInfo":{"appversion":"1.0.0","cid":"tio-android","deviceinfo":"HUAWEI MHA-AL00","devicetype":2,"imei":"866716035107410","resolution":"1080,1808","size":"5.9"},"n":"wata","r":[2,6,7],"timeCreated":1565870206665,"timeJoinGroup":1565870210915,"x":2}
     * g : /product/index.html
     * mid : 346571
     * t : 1565870210915
     */

    public String c;
    public String cid;
    public int ct;
    public int d;
    public FBean f;
    public String g;
    public String mid;
    public String t;

    public static class FBean {
        /**
         * a : /avatar/fengjing2/2018/08/19/8.jpeg
         * cid : 1161969981764804608
         * groupid : /product/index.html
         * i : 23436
         * ipInfo : {"area":"","city":"杭州市","country":"中国","id":651384,"ip":"124.160.42.146","operator":"联通","province":"浙江省","time":"2019-05-13 09:42:59"}
         * l : 1
         * ln : watayouxiang@qq.com
         * mobileInfo : {"appversion":"1.0.0","cid":"tio-android","deviceinfo":"HUAWEI MHA-AL00","devicetype":2,"imei":"866716035107410","resolution":"1080,1808","size":"5.9"}
         * n : wata
         * r : [2,6,7]
         * timeCreated : 1565870206665
         * timeJoinGroup : 1565870210915
         * x : 2
         */

        public String a;
        public String cid;
        public String groupid;
        public int i;
        public IpInfoBean ipInfo;
        public int l;
        public String ln;
        public MobileInfoBean mobileInfo;
        public String n;
        public long timeCreated;
        public long timeJoinGroup;
        public int x;
        public List<Integer> r;
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

    public static class MobileInfoBean {
        /**
         * appversion : 1.0.0
         * cid : tio-android
         * deviceinfo : HUAWEI MHA-AL00
         * devicetype : 2
         * imei : 866716035107410
         * resolution : 1080,1808
         * size : 5.9
         */

        public String appversion;
        public String cid;
        public String deviceinfo;
        public int devicetype;
        public String imei;
        public String resolution;
        public String size;
    }
}
