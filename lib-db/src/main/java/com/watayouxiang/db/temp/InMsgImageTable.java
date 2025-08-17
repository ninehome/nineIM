package com.watayouxiang.db.temp;

import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgImage;

import org.greenrobot.greendao.annotation.Id;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/16
 *     desc   :
 *     {@link InnerMsgImage}
 * </pre>
 */
public class InMsgImageTable {
    @Id
    private long id;
    private int comefrom;
    private int coverheight;
    private int coversize;
    /**
     * 缩略图
     */
    private String coverurl;
    private int coverwidth;
    private String filename;
    private int height;
    private String session;
    private long size;
    private int status;
    private String title;
    private int uid;
    /**
     * 原始图
     */
    private String url;
    private int width;
}
