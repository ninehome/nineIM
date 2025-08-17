package com.watayouxiang.webrtclib.client;

import android.widget.Toast;

import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.client.IMCallback;
import com.watayouxiang.imclient.model.body.webrtc.WxCall01Req;
import com.watayouxiang.imclient.model.body.webrtc.WxCall02Ntf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall02_1CancelReq;
import com.watayouxiang.imclient.model.body.webrtc.WxCall02_2CancelNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall03ReplyReq;
import com.watayouxiang.imclient.model.body.webrtc.WxCall03ReplyReqNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall04ReplyNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall05OfferSdpReq;
import com.watayouxiang.imclient.model.body.webrtc.WxCall06OfferSdpNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall07AnswerSdpReq;
import com.watayouxiang.imclient.model.body.webrtc.WxCall08AnswerSdpNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall09OfferIceReq;
import com.watayouxiang.imclient.model.body.webrtc.WxCall10OfferIceNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall11AnswerIceReq;
import com.watayouxiang.imclient.model.body.webrtc.WxCall12AnswerIceNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall13EndReq;
import com.watayouxiang.imclient.model.body.webrtc.WxCall14EndNtf;
import com.watayouxiang.imclient.packet.TioCommand;
import com.watayouxiang.imclient.packet.TioPacket;
import com.watayouxiang.imclient.packet.TioPacketBuilder;
import com.watayouxiang.webrtclib.interf.WebSocketChannelEvents;
import com.watayouxiang.webrtclib.interf.WebSocketChannelOp;
import com.watayouxiang.webrtclib.util.RTCLog;

import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020/4/30
 * desc : WebSocket信令客户端
 */
public class WebSocketChannelClient implements WebSocketChannelOp {

    private WebSocketChannelEvents events;
    private ClientIMCallback imCallback;
    private TioIMClient tioClient;

    public WebSocketChannelClient(WebSocketChannelEvents events) {
        this.events = events;
        imCallback = new ClientIMCallback();
        tioClient = TioIMClient.getInstance();
        tioClient.registerCallback(imCallback);
    }

    // ====================================================================================
    // WebSocketChannelOp
    // ====================================================================================

    @Override
    public void callReq(int toUid, byte type) {
        RTCLog.d(String.format(Locale.getDefault(), "---> callReq: toUid = %d, type = %d", toUid, type));

        WxCall01Req wxCall01Req = new WxCall01Req(toUid, type);
        TioPacket packet = TioPacketBuilder.getPacket(wxCall01Req, TioCommand.WX_CALL_01_REQ);
        TioIMClient.getInstance().sendPacket(packet);
    }

    @Override
    public void callCancelReq() {
        RTCLog.d("---> callCancelReq");

        WxCall02_1CancelReq wxCall02_1CancelReq = new WxCall02_1CancelReq();
        TioPacket packet = TioPacketBuilder.getPacket(wxCall02_1CancelReq, TioCommand.WX_CALL_021_CANCEL_REQ);
        TioIMClient.getInstance().sendPacket(packet);
    }

    @Override
    public void callReplyReq(byte result, String reason, String id) {
        RTCLog.d(String.format(Locale.getDefault(), "---> callReplyReq: callId = %s, result = %d, reason = %s", id, result, reason));

        WxCall03ReplyReq wxCall03ReplyReq = new WxCall03ReplyReq(result, reason, id);
        TioPacket packet = TioPacketBuilder.getPacket(wxCall03ReplyReq, TioCommand.WX_CALL_03_REPLY_REQ);
        TioIMClient.getInstance().sendPacket(packet);
    }

    @Override
    public void callEndReq(String id) {
        RTCLog.d(String.format(Locale.getDefault(), "---> callEndReq: callId = %s", id));

        WxCall13EndReq wxCall13EndReq = new WxCall13EndReq(id);
        TioPacket packet = TioPacketBuilder.getPacket(wxCall13EndReq, TioCommand.WX_CALL_13_END_REQ);
        TioIMClient.getInstance().sendPacket(packet);
    }

    @Override
    public void offerSdpReq(SessionDescription sdp, String id) {
        RTCLog.d(String.format(Locale.getDefault(), "---> offerSdpReq: callId = %s", id));

        HashMap<String, Object> map = new HashMap<>();
        map.put("sdp", sdp.description);
        map.put("type", sdp.type.canonicalForm());

        WxCall05OfferSdpReq wxCall05OfferSdpReq = new WxCall05OfferSdpReq(id, map);
        TioPacket packet = TioPacketBuilder.getPacket(wxCall05OfferSdpReq, TioCommand.WX_CALL_05_OFFER_SDP_REQ);
        TioIMClient.getInstance().sendPacket(packet);
    }

    @Override
    public void answerSdpReq(SessionDescription sdp, String id) {
        RTCLog.d(String.format(Locale.getDefault(), "---> answerSdpReq: callId = %s", id));

        HashMap<String, Object> map = new HashMap<>();
        map.put("sdp", sdp.description);
        map.put("type", sdp.type.canonicalForm());

        WxCall07AnswerSdpReq wxCall07AnswerSdpReq = new WxCall07AnswerSdpReq(id, map);
        TioPacket packet = TioPacketBuilder.getPacket(wxCall07AnswerSdpReq, TioCommand.WX_CALL_07_ANSWER_SDP_REQ);
        TioIMClient.getInstance().sendPacket(packet);
    }

    @Override
    public void offerIceReq(IceCandidate iceCandidate, String id) {
        RTCLog.d(String.format(Locale.getDefault(), "---> offerIceReq: callId = %s", id));

        HashMap<String, Object> map = new HashMap<>();
        map.put("sdpMLineIndex", iceCandidate.sdpMLineIndex);
        map.put("candidate", iceCandidate.sdp);
        map.put("sdpMid", iceCandidate.sdpMid);

        WxCall09OfferIceReq wxCall09OfferIceReq = new WxCall09OfferIceReq(id, map);
        TioPacket packet = TioPacketBuilder.getPacket(wxCall09OfferIceReq, TioCommand.WX_CALL_09_OFFER_ICE_REQ);
        TioIMClient.getInstance().sendPacket(packet);
    }

    @Override
    public void answerIceReq(IceCandidate iceCandidate, String id) {
        RTCLog.d(String.format(Locale.getDefault(), "---> answerIceReq: callId = %s", id));

        HashMap<String, Object> map = new HashMap<>();
        map.put("sdpMLineIndex", iceCandidate.sdpMLineIndex);
        map.put("candidate", iceCandidate.sdp);
        map.put("sdpMid", iceCandidate.sdpMid);

        WxCall11AnswerIceReq wxCall11AnswerIceReq = new WxCall11AnswerIceReq(id, map);
        TioPacket packet = TioPacketBuilder.getPacket(wxCall11AnswerIceReq, TioCommand.WX_CALL_11_ANSWER_ICE_REQ);
        TioIMClient.getInstance().sendPacket(packet);
    }

    @Override
    public void release() {
        if (tioClient != null) {
            tioClient.unregisterCallback(imCallback);
            tioClient = null;
        }
        imCallback = null;
    }

    // ====================================================================================
    // IMCallback<TioPacket>
    // ====================================================================================

    private class ClientIMCallback implements IMCallback<TioPacket> {
        @Override
        public void onConnecting() {

        }

        @Override
        public void onConnected() {

        }

        @Override
        public void onDisconnected() {
            events.onWebSocketChannelClosed();
        }

        @Override
        public void onError(Exception e) {
            events.onWebSocketChannelError(e);
        }

        @Override
        public void onSendBegin(TioPacket packet) {

        }

        @Override
        public void onSendCancel(TioPacket packet) {

        }

        @Override
        public void onSendEnd(TioPacket packet) {

        }

        @Override
        public void onReceiveBegin(ByteBuffer buffer) {

        }

        @Override
        public void onReceiveCancel() {

        }

        @Override
        public void onReceiveEnd(TioPacket packet) {
            short command = packet.getCommand();
            Object body = packet.getBody();
            if (command == TioCommand.WX_CALL_02_NTF) {
                WxCall02Ntf call = (WxCall02Ntf) body;
                RTCLog.d(String.format(Locale.getDefault(),
                        "<--- onCall: callId = %s, type = %d, fromuid = %d, touid = %d",
                        call.id, call.type, call.fromuid, call.touid));
                events.onCall(call);
            } else if (command == TioCommand.WX_CALL_022_CANCEL_NTF) {
                WxCall02_2CancelNtf callCancel = (WxCall02_2CancelNtf) body;
                RTCLog.d(String.format(Locale.getDefault(),
                        "<--- onCallCancel: callId = %s, type = %d, fromuid = %d, touid = %d",
                        callCancel.id, callCancel.type, callCancel.fromuid, callCancel.touid));
                events.onCallCancel(callCancel);
            } else if (command == TioCommand.WX_CALL_04_REPLY_NTF) {
                WxCall04ReplyNtf callReply = (WxCall04ReplyNtf) body;
                RTCLog.d(String.format(Locale.getDefault(),
                        "<--- onCallReply: callId = %s, type = %d, fromuid = %d, touid = %d; result = %d, reason = %s",
                        callReply.id, callReply.type, callReply.fromuid, callReply.touid, callReply.result, callReply.reason));
                events.onCallReply(callReply);
            } else if (command == TioCommand.WX_CALL_06_OFFER_SDP_NTF) {
                WxCall06OfferSdpNtf offerSdp = (WxCall06OfferSdpNtf) body;
                RTCLog.d("<--- onOfferSdp");
                events.onOfferSdp(offerSdp);
            } else if (command == TioCommand.WX_CALL_08_ANSWER_SDP_NTF) {
                WxCall08AnswerSdpNtf answerSdp = (WxCall08AnswerSdpNtf) body;
                RTCLog.d("<--- onAnswerSdp");
                events.onAnswerSdp(answerSdp);
            } else if (command == TioCommand.WX_CALL_10_OFFER_ICE_NTF) {
                WxCall10OfferIceNtf offerIceCandidate = (WxCall10OfferIceNtf) body;
                RTCLog.d("<--- onOfferIceCandidate");
                events.onOfferIceCandidate(offerIceCandidate);
            } else if (command == TioCommand.WX_CALL_12_ANSWER_ICE_NTF) {
                WxCall12AnswerIceNtf answerIceCandidate = (WxCall12AnswerIceNtf) body;
                RTCLog.d("<--- onAnswerIceCandidate");
                events.onAnswerIceCandidate(answerIceCandidate);
            } else if (command == TioCommand.WX_CALL_14_END_NTF) {
                WxCall14EndNtf callEnd = (WxCall14EndNtf) body;
                RTCLog.d(String.format(Locale.getDefault(),
                        "<--- onCallEnd: callId = %s, type = %d, fromuid = %d, touid = %d; hanguptype = %d",
                        callEnd.id, callEnd.type, callEnd.fromuid, callEnd.touid, callEnd.hanguptype));
                events.onCallEnd(callEnd);
            } else if (command == TioCommand.WX_CALL_03_REPLY_REQ_NTF) {
                WxCall03ReplyReqNtf callReplyReqNtf = (WxCall03ReplyReqNtf) body;
                RTCLog.d("<--- onCallReplyReqNtf");
                events.onCallReplyReqNtf(callReplyReqNtf);
            }
        }
    }
}
