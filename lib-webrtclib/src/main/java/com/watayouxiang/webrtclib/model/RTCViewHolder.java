package com.watayouxiang.webrtclib.model;

import org.webrtc.SurfaceViewRenderer;

/**
 * author : TaoWang
 * date : 2020/5/22
 * desc :
 */
public interface RTCViewHolder {
    SurfaceViewRenderer getLocalVideoView();

    SurfaceViewRenderer getRemoteVideoView();
}
