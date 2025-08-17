package com.watayouxiang.db.temp;

import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgAudio;

import org.greenrobot.greendao.annotation.Id;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/16
 *     desc   :
 *
 *     {@link InnerMsgAudio}
 * </pre>
 */
public class InMsgAudioTable {
    @Id
    private long id;
    private String filename;
    private int seconds;
    private int uid;
    private String url;
}
