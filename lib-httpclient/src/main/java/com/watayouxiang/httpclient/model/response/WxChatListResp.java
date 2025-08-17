package com.watayouxiang.httpclient.model.response;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * author : TaoWang
 * date : 2020-02-06
 * desc :
 */
public class WxChatListResp extends ArrayList<WxChatListResp.Chat> {
    public static class Chat implements Serializable {
        /**
         * ct : 1
         * c : 你是tio本尊吗
         * t : 2019-12-27 17:33:29
         * d : 1
         * f : 32319
         * g : 28
         * mid : 345891
         * sendbysys : 2
         */

        public int ct;
        public String c;
        public String t;
        public int d;
        public int f;
        public String g;
        public String mid;
        public int sendbysys;
    }
}
