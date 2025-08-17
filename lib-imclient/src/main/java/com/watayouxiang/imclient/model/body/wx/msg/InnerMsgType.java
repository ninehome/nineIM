package com.watayouxiang.imclient.model.body.wx.msg;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/06/05
 *     desc   :
 * </pre>
 */
public enum InnerMsgType {
    TEXT(1, "文字", String.class),
    BLOG(2, "博客", null),
    FILE(3, "文件", InnerMsgFile.class),
    AUDIO(4, "音频", InnerMsgAudio.class),
    VIDEO(5, "视频", InnerMsgVideo.class),
    IMAGE(6, "图片", InnerMsgImage.class),
    CARD(9, "明片", InnerMsgCard.class),
    CALL_VIDEO(10, "视频电话", InnerMsgCall.class),
    CALL_AUDIO(11, "音频电话", InnerMsgCall.class),
    RED_PAPER(12, "红包", InnerMsgRed.class),
    GROUP_APPLY(13, "群申请", InnerMsgGroupApply.class),
    LOCATION(14, "位置信息", InnerMsgLocation.class),
    FACE_EMOTION(15, "表情", InnerMsgFaceEmotion.class),
    TRANS_AMOUNT(16, "转账", InnerMsgTransAmount.class),
    TRANS_Message(17, "消息记录", InnerMsgHistory.class),
    ;
    private int code;
    private String desc;
    private Object reference;

    InnerMsgType(int code, String desc, Object reference) {
        this.code = code;
        this.desc = desc;
        this.reference = reference;
    }

    public static InnerMsgType valueOf(int code) {
        InnerMsgType[] values = values();
        for (int i = 0, len = values.length; i < len; i++) {
            if (values[i].code == code) {
                return values[i];
            }
        }
        return null;
    }
}
