package com.watayouxiang.httpclient.model.response;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * author : TaoWang
 * date : 2020-02-24
 * desc :
 */
public class ApplyListResp extends ArrayList<ApplyListResp.Data> {
    public static class Data implements Serializable {
        /**
         * nick : wata测试
         * uid : 32774
         * autoflag : 2
         * replytime : 2020-02-11 18:39:12
         * greet : 我是wata测试
         * id : 4
         * avatar : /user/base/avatar/20200115/7/1432571217333833301630976.png
         * status : 2
         */

        public String nick;
        public int uid;
        /**
         * 自己申请标识：1：是；2：否
         */
        public int autoflag;
        /**
         * 申请时间
         */
        public String replytime;
        public String greet;
        public int id;
        public String avatar;
        /**
         * 申请状态：1：申请通过；2：申请中
         */
        public int status;
    }
}
