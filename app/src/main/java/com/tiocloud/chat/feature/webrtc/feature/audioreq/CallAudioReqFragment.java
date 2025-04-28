package com.tiocloud.chat.feature.webrtc.feature.audioreq;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.BarUtils;
import com.tiocloud.chat.databinding.TioCallAudioReqFragmentBinding;
import com.tiocloud.chat.feature.webrtc.data.RTCViewHolderImpl;
import com.tiocloud.chat.feature.webrtc.feature.audioreq.mvp.AudioReqContract;
import com.tiocloud.chat.feature.webrtc.feature.audioreq.mvp.AudioReqPresenter;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.androidutils.page.TioFragment;
import com.tiocloud.chat.util.TimeUtil;
import com.watayouxiang.webrtclib.TioWebRTC;
import com.watayouxiang.webrtclib.model.AudioDevice;

/**
 * author : TaoWang
 * date : 2020/5/26
 * desc :
 */
public class CallAudioReqFragment extends TioFragment implements AudioReqContract.View {
    private TioCallAudioReqFragmentBinding binding;
    private AudioReqPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TioCallAudioReqFragmentBinding.inflate(inflater, container, false);
        presenter = new AudioReqPresenter(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        BarUtils.addMarginTopEqualStatusBarHeight(binding.ivCallAvatar);
//        BarUtils.addMarginTopEqualStatusBarHeight(binding.ivCallingAvatar);
//        BarUtils.addMarginTopEqualStatusBarHeight(binding.localVideoView);

        presenter.reqUserInfo();
        RTCViewHolderImpl rtcViewHolder = new RTCViewHolderImpl(
                binding.localVideoView,
                binding.remoteVideoView);
        presenter.initRTCViews(rtcViewHolder);
        presenter.call();
    }

    @Override
    public void closePage() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    @Override
    public void showWaitingView() {
        binding.rlCalling.setVisibility(View.GONE);
        binding.rlCall.setVisibility(View.VISIBLE);
        binding.tvCallCancel.setOnClickListener(view -> TioWebRTC.getInstance().callCancel());
    }

    @Override
    public void showCallingView() {
        binding.rlCall.setVisibility(View.GONE);
        binding.rlCalling.setVisibility(View.VISIBLE);
        binding.tvCallingHangup.setOnClickListener(view -> TioWebRTC.getInstance().hangUp());
        binding.tvCallingToggleAudio.setOnClickListener(view -> {
            AudioDevice audioDevice = TioWebRTC.getInstance().getAudioDevice();
            if (audioDevice == AudioDevice.SPEAKER) {
                // 听筒模式
                TioWebRTC.getInstance().setAudioDevice(AudioDevice.RECEIVER);
                binding.tvCallingToggleAudio.setSelected(false);
            } else if (audioDevice == AudioDevice.RECEIVER) {
                // 扬声器模式
                TioWebRTC.getInstance().setAudioDevice(AudioDevice.SPEAKER);
                binding.tvCallingToggleAudio.setSelected(true);
            }
        });
        binding.tvCallingToggleMic.setOnClickListener(view -> {
            if (TioWebRTC.getInstance().isLocalAudioEnable()) {
                // 禁止麦克风
                TioWebRTC.getInstance().setLocalAudioEnable(false);
                binding.tvCallingToggleMic.setSelected(true);
            } else {
                // 开启麦克风
                TioWebRTC.getInstance().setLocalAudioEnable(true);
                binding.tvCallingToggleMic.setSelected(false);
            }
        });
    }

    @Override
    public void onUserInfoResp(UserInfoResp userInfoResp) {
        String name = userInfoResp.remarkname;
        if (TextUtils.isEmpty(name)) {
            name = String.valueOf(userInfoResp.nick);
        }
        binding.ivCallAvatar.tio_roundAvatar(userInfoResp.avatar);
        binding.tvCallNick.setText(name);
        binding.ivCallingAvatar.tio_roundAvatar(userInfoResp.avatar);
        binding.tvCallingNick.setText(name);
    }

    @Override
    public void onCountDownTimer(long totalTime) {
        binding.tvCallingTimer.setText(TimeUtil.formatMS(totalTime));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}
