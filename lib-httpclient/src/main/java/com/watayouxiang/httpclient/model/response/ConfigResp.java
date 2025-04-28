package com.watayouxiang.httpclient.model.response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * author : TaoWang
 * date : 2020-01-22
 * desc :
 */
public class ConfigResp implements Serializable {
    /**
     * api_suf : .tio_x
     * session_cookie_name : tio_session_online
     * sitename : t-io官网
     * js_version : 6
     * api_ctx : /mytio
     * res_server : https://res.tiocloud.com
     * im_heartbeat_timeout : 120000
     */

    /**
     * 资源服务器地址
     */
    public String res_server;
    /**
     * cookie名
     */
    public String session_cookie_name;
    /**
     * 网站名字
     */
    public String sitename;
    public String js_version;
    public String api_suf;
    public String api_ctx;

    public List<Website> website;

    public Map<String, String> conf;

    public class Website{
        public Integer id;
        public String sitename;
        public String siteurl;
        public String siteicon;
    }
//    /**
//     * 验证验证码
//     */
//    public String check_code;

    /**
     * 长连接的超时时间，单位是毫秒
     */
    public long im_heartbeat_timeout;


    @Override
    public String toString() {
//        return "ConfigResp{" +
//                "res_server='" + res_server + '\'' +
//                ", session_cookie_name='" + session_cookie_name + '\'' +
//                ", sitename='" + sitename + '\'' +
//                ", js_version='" + js_version + '\'' +
//                ", api_suf='" + api_suf + '\'' +
//                ", api_ctx='" + api_ctx + '\'' +
//                ", check_code='" + check_code + '\'' +
//                ", im_heartbeat_timeout=" + im_heartbeat_timeout +
//                ", conf=" + conf.toString() +
//                '}';
        return "config:"+new Gson().toJson(this);
    }
}
