package com.watayouxiang.webrtclib.listener;

import com.watayouxiang.imclient.model.body.webrtc.WxCall02Ntf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall02_2CancelNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall04ReplyNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall14EndNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall03ReplyReqNtf;

/**
 * author : TaoWang
 * date : 2020/5/22
 * desc :
 */
public class OnSimpleRTCListener implements OnRTCListener {
    @Override
    public void onCall(WxCall02Ntf call) {

    }

    @Override
    public void onCallReply(WxCall04ReplyNtf callReply) {

    }

    @Override
    public void onCallCancel(WxCall02_2CancelNtf callCancel) {

    }

    @Override
    public void onCallEnd(WxCall14EndNtf callEnd) {

    }

    @Override
    public void onCallResp(WxCall03ReplyReqNtf callResp) {

    }

    @Override
    public void onRTCClosed() {

    }

    @Override
    public void onRTCConnected() {

    }

    @Override
    public void onPeerConnectionError(String error) {

    }

    @Override
    public void onWebSocketError(Exception e) {

    }

    @Override
    public void onWebSocketClosed() {

    }
}
