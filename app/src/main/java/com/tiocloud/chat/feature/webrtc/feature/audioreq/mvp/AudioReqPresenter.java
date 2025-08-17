package com.tiocloud.chat.feature.webrtc.feature.audioreq.mvp;

import android.os.CountDownTimer;

import com.tiocloud.chat.R;
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
public class AudioReqPresenter extends AudioReqContract.Presenter {

    private CountDownTimer countDownTimer;

    public AudioReqPresenter(AudioReqContract.View view) {
        super(new AudioReqModel(), view, false);
    }

    @Override
    public void initRTCViews(RTCViewHolder holder) {
        TioWebRTC.getInstance().init(holder);
        TioWebRTC.getInstance().setAudioDevice(CallConst.AUDIO___AUDIO_DEVICE);
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
                TioToast.showShort(getView().getActivity().getString(R.string.ice_error));
            }

            @Override
            public void onWebSocketError(Exception e) {
                TioToast.showShort(getView().getActivity().getString(R.string.im_error));
            }

            @Override
            public void onWebSocketClosed() {
                TioToast.showShort(getView().getActivity().getString(R.string.im_close));
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
