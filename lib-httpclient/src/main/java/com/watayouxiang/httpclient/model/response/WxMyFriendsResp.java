package com.watayouxiang.httpclient.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020-01-16
 * desc :
 */
public class WxMyFriendsResp implements Serializable {

    /**
     * firstPage : true
     * lastPage : true
     * list : [{"nick":"jueqingsizhe66","uid":23692,"remarkname":"[jueqingsizhe66]","avatar":"/user/avatar/64/9266/1119819/88097872/74541311240/29/201402/xinran.jpg_sm.jpg"},{"nick":"talent","uid":23357,"remarkname":"[talent]","avatar":"/user/avatar/50/8931/1119484/88097537/74541310905/91/170020/t-io-前白背蓝-正方.png_sm.jpg"},{"nick":"官方机器人","uid":23374,"remarkname":"官方人员","avatar":"/user/avatar/67/8948/1119501/88097554/74541310922/17/091736/1216167308691644416_sm.jpg"}]
     * pageNumber : 1
     * pageSize : 50
     * totalPage : 1
     * totalRow : 3
     */

    public boolean firstPage;
    public boolean lastPage;
    public int pageNumber;
    public int pageSize;
    public int totalPage;
    public int totalRow;
    public List<ListBean> list;

    public static class ListBean implements Serializable {
        /**
         * nick : jueqingsizhe66
         * uid : 23692
         * remarkname : [jueqingsizhe66]
         * avatar : /user/avatar/64/9266/1119819/88097872/74541311240/29/201402/xinran.jpg_sm.jpg
         */

        public String nick;
        public int uid;
        public String remarkname;
        public String avatar;

        @Override
        public String toString() {
            return "ListBean{" +
                    "nick='" + nick + '\'' +
                    ", uid=" + uid +
                    ", remarkname='" + remarkname + '\'' +
                    ", avatar='" + avatar + '\'' +
                    '}';
        }
    }
}
