package com.tiocloud.chat.feature.share.friend.model;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/21
 *     desc   :
 * </pre>
 */
public class ShareCardEntity {

    // 接收方
    public ShareCardTo to;
    // 发送方 名片id
    public ShareCardFrom from;

    public ShareCardEntity(ShareCardTo to, ShareCardFrom from) {
        this.to = to;
        this.from = from;
    }
}
