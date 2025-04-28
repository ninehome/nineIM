package com.watayouxiang.imclient.model.body.webrtc;

import com.watayouxiang.imclient.model.HangUpType;

/**
 * author : TaoWang
 * date : 2020/4/29
 * desc :
 */
public class WxCallItem extends WxBaseCall {
    /**
     * 通话时长long
     */
    public String callduration;
    /**
     * 呼叫开始时间（一方请求通话请求的时间）
     */
    public String calltime;
    /**
     * 通话发起人的channelcontextid
     */
    public String fromcid;
    /**
     * 发起通话人员的设备类型（DeviceType），1：PC，2：安卓，3：IOS
     *
     * @see com.watayouxiang.imclient.model.DeviceType
     */
    public int fromdevice;
    /**
     * 发起人的ipid（ipinfo表的id）
     */
    public int fromipid;
    /**
     * 通话发起人的userid
     */
    public int fromuid;
    /**
     * 挂断类型
     * 1、正常挂断
     * 2、拒接挂断
     * 3、对方正在通话
     * 4、TCP断开时，系统自动挂断
     * 5、客户端出现异常，系统自动挂机（譬如获取摄像头失败等），这个请求是客户端发起的挂断请求
     * 6、ICE服务器异常，这个请求是客户端发起的挂断请求
     * 7、系统重启
     * 8、对方不在线
     * 9、等待响应超时
     * 10、发起方取消了通话
     * 99、还没有挂断
     */
    public int hanguptype;
    /**
     * 挂断一方的uid，如果是系统挂断，则是null
     */
    public int hangupuid;
    /**
     * 通话id
     */
    public String id;
    /**
     * 对方响应时间点
     */
    public String resptime;
    /**
     * 响应操作时长
     */
    public String respwait;
    /**
     * 呼叫状态：1、发起呼叫， 2、信令接通，3、流媒体接通，4、通话结束（拒接、占线、挂断都属于通话结束）
     */
    public int status;
    /**
     * 接通操作时长
     */
    public String streamwait;
    /**
     * 通话对方的channelcontextid
     */
    public String tocid;
    /**
     * 通话对方的设备类型（DeviceType），1：PC，2：安卓，3：IOS
     */
    public int todevice;
    /**
     * 通话对方的ipid（ipinfo表的id）
     */
    public int toipid;
    /**
     * 通话对方的userid
     */
    public int touid;
    /**
     * 通话类型：1、 音频通话，2、视频通话
     */
    public int type;
    /**
     * 通话结束时间
     */
    public String endtime;
    /**
     * 接通时间（对方同意通话时间）
     */
    public String connectedtime;

    public HangUpType getHangupType() {
        return HangUpType.valueOf(hanguptype);
    }
}
