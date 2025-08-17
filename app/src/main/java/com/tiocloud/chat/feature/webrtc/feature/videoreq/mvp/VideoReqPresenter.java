package com.tiocloud.chat.feature.webrtc.feature.videoreq.mvp;

import android.os.CountDownTimer;

import com.tiocloud.chat.feature.webrtc.data.CallConst;
import com.tiocloud.chat.feature.webrtc.data.CallReq;
import com.watayouxiang.imclient.model.HangUpType;
import com.watayouxiang.imclient.model.body.webrtc.WxCall14EndNtf;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.webrtclib.TioWebRTC;
import com.watayouxiang.webrtclib.listener.OnSimpleRTCListener;
import com.watayouxiang.webrtclib.model.RTCViewHolder;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/6/4
 *     desc   :
 * </pre>
 */
public class VideoReqPresenter extends VideoReqContract.Presenter {

    private CountDownTimer countDownTimer;

    public VideoReqPresenter(VideoReqContract.View view) {
        super(new VideoReqModel(), view, false);
    }

    @Override
    public void initRTCViews(RTCViewHolder holder) {
        TioWebRTC.getInstance().init(holder);
        TioWebRTC.getInstance().setAudioDevice(CallConst.VIDEO___AUDIO_DEVICE);
        TioWebRTC.getInstance().registerOnRTCListener(new OnSimpleRTCListener() {
            @Override
            public void onRTCConnected() {
                getView().showCallingView();
                startCountDownTimer();
            }

            @Override
            public void onRTCClosed() {
                getView().closePage();
            }

            @Override
            public void onCallEnd(WxCall14EndNtf callEnd) {
                HangUpType hangupType = callEnd.getHangupType();
                if (hangupType != null) {
                    String showText = hangupType.getShowText(true, callEnd.callduration, callEnd.type);
                    TioToast.showShort(showText);
                }
            }

            @Override
            public void onPeerConnectionError(String error) {
                TioToast.showShort("ICE错误，断开通话");
            }

            @Override
            public void onWebSocketError(Exception e) {
                TioToast.showShort("IM报错，断开通话");
            }

            @Override
            public void onWebSocketClosed() {
                TioToast.showShort("IM关闭，断开通话");
            }
        });

        getView().showWaitingView();
    }

    private void startCountDownTimer() {
        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
                @Override
                public void onTick(long l) {
                    getView().onCountDownTimer(Long.MAX_VALUE - l);
                }

                @Override
                public void onFinish() {
                    countDownTimer.start();
                }
            };
        }
        countDownTimer.cancel();
        countDownTimer.start();
    }

    @Override
    public void reqUserInfo() {
        int toUid = getModel().getToUid(getView().getActivity());
        getModel().reqUserInfo(toUid, getView());
    }

    @Override
    public void call() {
        CallReq callReq = getModel().getCallReq(getView().getActivity());
        if (callReq != null) {
            TioWebRTC.getInstance().call(callReq.getToUid(), (byte) callReq.getType());
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        TioWebRTC.getInstance().release();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}
