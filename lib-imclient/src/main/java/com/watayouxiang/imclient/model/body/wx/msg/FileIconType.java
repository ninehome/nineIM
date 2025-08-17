package com.watayouxiang.imclient.model.body.wx.msg;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/18
 *     desc   : 文件icon类型
 * </pre>
 */
public interface FileIconType {
    /**
     * pdf
     */
    byte PDF = 1;

    /**
     * txt
     */
    byte TXT = 2;

    /**
     * doc
     */
    byte DOC = 3;

    /**
     * xls
     */
    byte XLS = 4;

    /**
     * ppt
     */
    byte PPT = 5;

    /**
     * apk
     */
    byte APK = 6;

    /**
     * 图片
     */
    byte IMG = 7;

    /**
     * 压缩包
     */
    byte ZIP = 8;

    /**
     * 视频
     */
    byte VIDEO = 9;

    /**
     * 音频
     */
    byte AUDIO = 10;

    /**
     * 其它
     */
    byte OTHER = 11;
}
