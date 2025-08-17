package com.watayouxiang.db.temp;

import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgVideo;

import org.greenrobot.greendao.annotation.Id;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/16
 *     desc   :
 *
 *     {@link InnerMsgVideo}
 * </pre>
 */
public class InMsgVideoTable {
    @Id
    private long id;
    private int comefrom;
    private int coverheight;
    private int coversize;
    private String coverurl;
    private int coverwidth;
    private String filename;
    private String formatedseconds;
    private double fps;
    private int framecount;
    private int height;
    private int seconds;
    private String session;
    private long size;
    private int status;
    private String title;
    private int uid;
    private String url;
    private int width;
}
