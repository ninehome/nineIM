package com.watayouxiang.httpclient.model.response;

import java.util.ArrayList;

/**
 * author : TaoWang
 * date : 2020/5/20
 * desc :
 */
public class TurnServerResp extends ArrayList<TurnServerResp.TurnServer> {

    public static class TurnServer {
        /**
         * credential : 8654269566
         * urls : turn:114.116.46.19:3478
         * username : tan
         */
        
        public String credential;
        public String urls;
        public String username;
    }
}
