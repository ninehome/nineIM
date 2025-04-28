package com.watayouxiang.imclient.model.body;

import com.watayouxiang.imclient.model.DeviceType;

public class HandshakeReq extends BaseReq {
    /**
     * 客户端通过http登录后，服务器返回给客户端的token值，没登录则为空串
     */
    public String token;
    /**
     * 签名
     */
    public String sign;
    /**
     * {@link DeviceType}
     */
    public Byte devicetype;
    /**
     * 设备信息
     */
    public MobileInfo mobileInfo;
    /**
     * 极光推送绑定信息
     * <p>
     * 对应极光推送的 registerId
     */
    public String jpushinfo;

    public static class MobileInfo {
        /**
         * 2：安卓，3：IOS
         */
        public Byte devicetype;
        /**
         * 手机型号, 形如：huawei p6
         */
        public String deviceinfo;
        /**
         * IMEI
         */
        public String imei;
        /**
         * App版本
         */
        public String appversion;
        /**
         * 渠道
         */
        public String cid;
        /**
         * 分辨率
         */
        public String resolution;
        /**
         * 手机屏幕，多少英寸
         */
        public String size;
        /**
         * 运营商
         */
        public String operator;

        @Override
        public String toString() {
            return "MobileInfo{" +
                    "devicetype=" + devicetype +
                    ", deviceinfo='" + deviceinfo + '\'' +
                    ", imei='" + imei + '\'' +
                    ", appversion='" + appversion + '\'' +
                    ", cid='" + cid + '\'' +
                    ", resolution='" + resolution + '\'' +
                    ", size='" + size + '\'' +
                    ", operator='" + operator + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HandshakeReq{" +
                "token='" + token + '\'' +
                ", sign='" + sign + '\'' +
                ", devicetype=" + devicetype +
                ", mobileInfo=" + mobileInfo +
                '}';
    }
}
