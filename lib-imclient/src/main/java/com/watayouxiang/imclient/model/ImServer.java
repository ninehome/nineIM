package com.watayouxiang.imclient.model;

public class ImServer {
    /**
     * 服务器地址
     */
    public Address data;
    /**
     * 请求是否成功
     */
    public boolean ok;

    public static class Address {
        /**
         * ip
         */
        public String ip;
        /**
         * 端口号
         */
        public int port;
    }
}
