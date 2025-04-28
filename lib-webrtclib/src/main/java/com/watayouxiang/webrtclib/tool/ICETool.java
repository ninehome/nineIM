package com.watayouxiang.webrtclib.tool;

import androidx.annotation.NonNull;

import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.TurnServerReq;
import com.watayouxiang.httpclient.model.response.TurnServerResp;
import com.watayouxiang.webrtclib.model.SignalingParameters;
import com.watayouxiang.webrtclib.util.RTCLog;

import org.webrtc.PeerConnection;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020/6/1
 * desc :
 */
public class ICETool {
    public static @NonNull
    List<PeerConnection.IceServer> getIceServers(SignalingParameters signalingParameters) {
        List<PeerConnection.IceServer> iceServers;
        if (signalingParameters != null) {
            iceServers = signalingParameters.iceServers;
        } else {
            iceServers = reqIceServers();
        }
        return iceServers;
    }

    private static @NonNull
    List<PeerConnection.IceServer> reqIceServers() {
        TurnServerReq turnServerReq = new TurnServerReq();
        Response<BaseResp<TurnServerResp>> response = turnServerReq.get();
        if (response.isSuccessful()) {
            BaseResp<TurnServerResp> body = response.body();
            if (body.isOk() && body.getData() != null) {
                LinkedList<PeerConnection.IceServer> turnServers = new LinkedList<>();
                TurnServerResp data = body.getData();
                for (TurnServerResp.TurnServer turnServer : data) {
                    String urls = turnServer.urls;
                    String username = turnServer.username;
                    String credential = turnServer.credential;
                    RTCLog.d(String.format(Locale.getDefault(), "---> turnServer: urls = %s, username = %s, credential = %s", urls, username, credential));
                    turnServers.add(new PeerConnection.IceServer(urls, username, credential));
                }
                RTCLog.d("request turn server success");
                return turnServers;
            }
        }
        throw new NullPointerException("iceServers null");
    }
}
