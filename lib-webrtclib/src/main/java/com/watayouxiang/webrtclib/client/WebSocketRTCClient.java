package com.watayouxiang.webrtclib.client;

import android.os.Handler;
import android.os.HandlerThread;

import com.watayouxiang.imclient.model.body.webrtc.WxCall02Ntf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall02_2CancelNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall03ReplyReqNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall04ReplyNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall06OfferSdpNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall08AnswerSdpNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall10OfferIceNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall12AnswerIceNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall14EndNtf;
import com.watayouxiang.webrtclib.interf.WebSocketChannelEvents;
import com.watayouxiang.webrtclib.interf.WebSocketRTCEvents;
import com.watayouxiang.webrtclib.interf.WebSocketRTCOp;

import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

/**
 * author : TaoWang
 * date : 2020/4/29
 * desc : WebSocketRTC客户端
 */
public class WebSocketRTCClient implements WebSocketRTCOp, WebSocketChannelEvents {

    private WebSocketRTCEvents events;
    private Handler handler;
    private WebSocketChannelClient wsClient;

    private boolean initiator = false;// true 聊天发起人，false 聊天响应人
    private String offerChatId;// offer聊天id
    private String answerChatId;// answer聊天id

    public WebSocketRTCClient(WebSocketRTCEvents events) {
        this.events = events;
        HandlerThread handlerThread = new HandlerThread(getClass().getSimpleName());
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        wsClient = new WebSocketChannelClient(this);
    }

    // ====================================================================================
    // WebSocketRTCClientOp
    // ====================================================================================

    @Override
    public void callReq(int toUid, byte type) {
        handler.post(() -> wsClient.callReq(toUid, type));
    }

    @Override
    public void callCancelReq() {
        handler.post(() -> wsClient.callCancelReq());
    }

    @Override
    public void callReplyReq(byte result, String reason) {
        handler.post(() -> {
            if (!initiator && answerChatId != null) {
                wsClient.callReplyReq(result, reason, answerChatId);
            }
        });
    }

    @Override
    public void callEndReq() {
        handler.post(() -> {
            if (initiator) {
                if (offerChatId != null) {
                    wsClient.callEndReq(offerChatId);
                }
            } else {
                if (answerChatId != null) {
                    wsClient.callEndReq(answerChatId);
                }
            }
        });
    }

    @Override
    public void offerSdpReq(SessionDescription sdp) {
        handler.post(() -> {
            if (initiator && offerChatId != null) {
                wsClient.offerSdpReq(sdp, offerChatId);
            }
        });
    }

    @Override
    public void answerSdpReq(SessionDescription sdp) {
        handler.post(() -> {
            if (!initiator && answerChatId != null) {
                wsClient.answerSdpReq(sdp, answerChatId);
            }
        });
    }

    @Override
    public void offerIceReq(IceCandidate iceCandidate) {
        handler.post(() -> {
            if (initiator && offerChatId != null) {
                wsClient.offerIceReq(iceCandidate, offerChatId);
            }
        });
    }

    @Override
    public void answerIceReq(IceCandidate iceCandidate) {
        handler.post(() -> {
            if (!initiator && answerChatId != null) {
                wsClient.answerIceReq(iceCandidate, answerChatId);
            }
        });
    }

    @Override
    public void release() {
        handler.post(() -> {
            answerChatId = null;
            offerChatId = null;
            initiator = false;
            if (wsClient != null) {
                wsClient.release();
                wsClient = null;
            }
            if (handler != null) {
                handler.getLooper().quit();
                handler = null;
            }
        });
    }

    // ====================================================================================
    // WebSocketChannelEvents
    // ====================================================================================

    @Override
    public void onCall(WxCall02Ntf call) {
        handler.post(() -> {
            initiator = false;
            answerChatId = call.id;
            events.onCall(call);
        });
    }

    @Override
    public void onCallReply(WxCall04ReplyNtf callReply) {
        handler.post(() -> {
            if (callReply.result == 1) {
                initiator = true;
                offerChatId = callReply.id;
            }
            events.onCallReply(callReply);
        });
    }

    @Override
    public void onCallCancel(WxCall02_2CancelNtf callCancel) {
        handler.post(() -> events.onCallCancel(callCancel));
    }

    @Override
    public void onCallEnd(WxCall14EndNtf callEnd) {
        handler.post(() -> events.onCallEnd(callEnd));
    }

    @Override
    public void onCallReplyReqNtf(WxCall03ReplyReqNtf callReplyReqNtf) {
        handler.post(() -> events.onCallReplyReqNtf(callReplyReqNtf));
    }

    @Override
    public void onOfferSdp(WxCall06OfferSdpNtf offerSdp) {
        handler.post(() -> {
            try {
                // 创建sdp
                String _type = (String) offerSdp.sdp.get("type");
                String _sdp = (String) offerSdp.sdp.get("sdp");
                if (_sdp == null) throw new NullPointerException("SDP _sdp null");

                SessionDescription sdp = new SessionDescription(SessionDescription.Type.fromCanonicalForm(_type), _sdp);
                events.onRemoteSdp(sdp, false);
            } catch (Exception e) {
                events.onWebSocketError(e);
            }
        });
    }

    @Override
    public void onAnswerSdp(WxCall08AnswerSdpNtf answerSdp) {
        handler.post(() -> {
            try {
                // 创建sdp
                String _type = (String) answerSdp.sdp.get("type");
                String _sdp = (String) answerSdp.sdp.get("sdp");
                if (_sdp == null) throw new NullPointerException("SDP _sdp null");

                SessionDescription sdp = new SessionDescription(SessionDescription.Type.fromCanonicalForm(_type), _sdp);
                events.onRemoteSdp(sdp, true);
            } catch (Exception e) {
                events.onWebSocketError(e);
            }
        });
    }

    @Override
    public void onOfferIceCandidate(WxCall10OfferIceNtf offerIce) {
        handler.post(() -> {
            try {
                // 创建IceCandidate
                int _sdpMLineIndex = (int) (double) offerIce.candidate.get("sdpMLineIndex");
                String _candidate = (String) offerIce.candidate.get("candidate");
                String _sdpMid = (String) offerIce.candidate.get("sdpMid");
                if (_candidate == null) throw new NullPointerException("ICE _candidate null");

                IceCandidate candidate = new IceCandidate(_sdpMid, _sdpMLineIndex, _candidate);
                events.onRemoteIceCandidate(candidate, false);
            } catch (Exception e) {
                events.onWebSocketError(e);
            }
        });
    }

    @Override
    public void onAnswerIceCandidate(WxCall12AnswerIceNtf answerIce) {
        handler.post(() -> {
            try {
                // 创建IceCandidate
                int _sdpMLineIndex = (int) (double) answerIce.candidate.get("sdpMLineIndex");
                String _candidate = (String) answerIce.candidate.get("candidate");
                String _sdpMid = (String) answerIce.candidate.get("sdpMid");
                if (_candidate == null) throw new NullPointerException("ICE _candidate null");

                IceCandidate candidate = new IceCandidate(_sdpMid, _sdpMLineIndex, _candidate);
                events.onRemoteIceCandidate(candidate, true);
            } catch (Exception e) {
                events.onWebSocketError(e);
            }
        });
    }

    @Override
    public void onWebSocketChannelError(Exception e) {
        handler.post(() -> events.onWebSocketError(e));
    }

    @Override
    public void onWebSocketChannelClosed() {
        handler.post(() -> events.onWebSocketClosed());
    }
}
