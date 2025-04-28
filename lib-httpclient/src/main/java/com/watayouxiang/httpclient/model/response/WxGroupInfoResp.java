package com.watayouxiang.httpclient.model.response;

import java.io.Serializable;

/**
 * author : TaoWang
 * date : 2020-02-06
 * desc :
 */
public class WxGroupInfoResp implements Serializable {

    /**
     * membercount : 71
     * uid : 23357
     * createtime : 2019-10-01 18:31:03
     * joinmode : 3
     * msgactflag : 2
     * intro : t-io更优质的朋友圈
     * name : t-io更优质的朋友圈
     * id : 28
     * avatar : /wx/group/avatar/50/8931/1119484/88097537/74541310905/72/183103/wxgroup_avatar_sm.jpg
     * updatetime : 2019-12-31 15:15:13
     * notice : 客户端正在研发
     * status : 1
     */

    public String membercount;
    public int uid;
    public String createtime;
    public int joinmode;
    public int msgactflag;
    public String intro;
    public String name;
    public String id;
    public String avatar;
    public String updatetime;
    public String notice;
    public int status;
}
