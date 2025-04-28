package com.watayouxiang.imclient.model.body.wx.msg;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/8/5
 *     desc   :
 * </pre>
 */
public class InnerMsgAudio {
    /**
     * filename : 1596618762860.m4a
     * id : 270
     * seconds : 2
     * uid : 23436
     * url : /wx/upload/video/22/9010/1119563/88097616/74541310984/35/171248/1290938799093850112.m4a
     */

    public String filename;
    public int id;
    public int seconds;
    public int uid;
    public String url;

    @Override
    public String toString() {
        return "InnerMsgAudio{" +
                "filename='" + filename + '\'' +
                ", id=" + id +
                ", seconds=" + seconds +
                ", uid=" + uid +
                ", url='" + url + '\'' +
                '}';
    }
}
