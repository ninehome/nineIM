package com.tiocloud.chat.feature.webrtc.data;

import com.watayouxiang.webrtclib.model.RTCViewHolder;

import org.webrtc.SurfaceViewRenderer;

/**
 * author : TaoWang
 * date : 2020/4/30
 * desc :
 */
public class RTCViewHolderImpl implements RTCViewHolder {

    private final SurfaceViewRenderer localVideoView;
    private final SurfaceViewRenderer remoteVideoView;

    public RTCViewHolderImpl(SurfaceViewRenderer localVideoView, SurfaceViewRenderer remoteVideoView) {
        this.localVideoView = localVideoView;
        this.remoteVideoView = remoteVideoView;
    }

    @Override
    public SurfaceViewRenderer getLocalVideoView() {
        return localVideoView;
    }

    @Override
    public SurfaceViewRenderer getRemoteVideoView() {
        return remoteVideoView;
    }
}
