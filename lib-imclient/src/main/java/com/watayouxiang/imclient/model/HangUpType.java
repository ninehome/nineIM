package com.watayouxiang.imclient.model;

import java.text.DecimalFormat;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/06/16
 *     desc   :
 * </pre>
 */
public enum HangUpType {
    // 主叫 通话时长 99:09
    // 被叫 通话时长 99:09
    NORMAL(1, "正常挂断"),
    // 主叫 对方已拒绝
    // 被叫 已拒绝
    REJECT(2, "拒接挂断"),
    // 主叫 对方忙线中
    // 被叫 已在其他设备处理
    OTHER_SIDE_CALLING(3, "对方正在通话"),
    // 主叫 系统自动挂断
    // 被叫 系统自动挂断
    TCP_DROPPED(4, "TCP断开时，系统自动挂断"),
    CLIENT_ERROR(5, "客户端出现异常，系统自动挂机（譬如获取摄像头失败等），这个请求是客户端发起的挂断请求"),
    ICE_ERROR(6, "ICE服务器异常，这个请求是客户端发起的挂断请求"),
    SYSTEM_RESTART(7, "系统重启"),
    // 主叫 对方不在线
    // 被叫 对方不在线
    OFFLINE(8, "对方不在线"),
    // 主叫 对方未接听
    // 被叫 视频通话未接听
    RESP_TIMEOUT(9, "等待响应超时"),
    // 主叫 视频通话已取消
    // 被叫 对方已取消
    CANCELED(10, "发起方取消了通话"),
    // 主叫 还没有挂断
    // 被叫 还没有挂断
    NOT_HANGUP(99, "还没有挂断");

    private int value;
    private String desc;

    HangUpType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static HangUpType valueOf(int value) {
        HangUpType[] values = values();
        for (int i = 0, len = values.length; i < len; i++) {
            if (values[i].value == value) {
                return values[i];
            }
        }
        return null;
    }

    // ====================================================================================
    // 拓展方法
    // ====================================================================================

    /**
     * 获取显示文案
     *
     * @param isReq    是否是"主叫方"
     * @param duration 总通话时长
     * @param callType 通话类型：1、语音通话，2、视频通话
     */
    public String getShowText(boolean isReq, String duration, int callType) {
        switch (this) {
            case NORMAL:
                return "通话时长 " + getTime(duration);
            case REJECT:
                if (isReq) {
                    return "对方已拒绝";
                } else {
                    return "已拒绝";
                }
            case OTHER_SIDE_CALLING:
                if (isReq) {
                    return "对方忙线中";
                } else {
                    return "已在其他设备处理";
                }
            case TCP_DROPPED:
            case CLIENT_ERROR:
            case ICE_ERROR:
            case SYSTEM_RESTART:
                return "系统自动挂断";
            case OFFLINE:
                return "对方不在线";
            case RESP_TIMEOUT:
                if (isReq) {
                    return "对方未接听";
                } else {
                    return getCallType(callType) + "未接听";
                }
            case CANCELED:
                return getCallType(callType) + "已取消";
            case NOT_HANGUP:
                return "还没有挂断";
        }
        return null;
    }

    private String getCallType(int callType) {
        if (callType == 1) {
            return "语音通话";
        } else if (callType == 2) {
            return "视频通话";
        } else {
            return "未知类型通话";
        }
    }

    private String getTime(String duration) {
        try {
            long time = Long.parseLong(duration);
            time = time / 1000;
            long min = time / 60 % 60;
            long sec = time % 60;
            DecimalFormat df = new DecimalFormat("00");
            return String.format("%s:%s", df.format(min), df.format(sec));
        } catch (Exception e) {
            return null;
        }
    }
}
