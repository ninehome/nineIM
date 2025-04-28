package com.tiocloud.chat.feature.webrtc.feature.videontf.mvp;

import android.os.CountDownTimer;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.webrtc.data.CallConst;
import com.watayouxiang.imclient.model.HangUpType;
import com.watayouxiang.imclient.model.body.webrtc.WxCall03ReplyReqNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall14EndNtf;
import com.tiocloud.chat.util.TioBellVibrate;
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
public class VideoNtfPresenter extends VideoNtfContract.Presenter {

    private CountDownTimer countDownTimer;

    public VideoNtfPresenter(VideoNtfContract.View view) {
        super(new VideoNtfModel(), view, false);
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
                if (getView().getCallActivity().isOpenMsgRemind()) {
                    TioBellVibrate.getInstance().stop();
                }
            }

            @Override
            public void onRTCClosed() {
                getView().closePage();
                if (getView().getCallActivity().isOpenMsgRemind()) {
                    TioBellVibrate.getInstance().start(TioBellVibrate.Bell.CALL_END);
                }
            }

            @Override
            public void onCallEnd(WxCall14EndNtf callEnd) {
                HangUpType hangupType = callEnd.getHangupType();
                if (hangupType != null) {
                    String showText = hangupType.getShowText(false, callEnd.callduration, callEnd.type);
                    TioToast.showShort(showText);
                }
            }

            @Override
            public void onCallResp(WxCall03ReplyReqNtf callResp) {
                // 同意通话 && 接收端是安卓
                if (callResp.result == 1 && callResp.todevice == 2) {
                    // ui需要做的处理：接听按钮隐藏、提示修改为 "接通中..."
                    getView().changeWaitingView();
                }
            }

            @Override
            public void onPeerConnectionError(String error) {
                TioToast.showShort(getView().getCallActivity().getString(R.string.ice_error));
            }

            @Override
            public void onWebSocketError(Exception e) {
                TioToast.showShort(getView().getCallActivity().getString(R.string.im_error));
            }

            @Override
            public void onWebSocketClosed() {
                TioToast.showShort(getView().getCallActivity().getString(R.string.im_close));
            }
        });

        getView().showWaitingView();

        // 开始震动
        if (getView().getCallActivity().isOpenMsgRemind()) {
            TioBellVibrate.getInstance().start(TioBellVibrate.Bell.CALL_NTF);
        }
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
        int fromUid = getModel().getFromUid(getView().getCallActivity());
        getModel().reqUserInfo(fromUid, getView());
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
