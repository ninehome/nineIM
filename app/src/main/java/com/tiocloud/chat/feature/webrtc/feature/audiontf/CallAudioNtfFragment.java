package com.tiocloud.chat.feature.webrtc.feature.audiontf;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.BarUtils;
import com.tiocloud.chat.databinding.TioCallAudioNtfFragmentBinding;
import com.tiocloud.chat.feature.webrtc.CallActivity;
import com.tiocloud.chat.feature.webrtc.data.RTCViewHolderImpl;
import com.tiocloud.chat.feature.webrtc.feature.audiontf.mvp.AudioNtfContract;
import com.tiocloud.chat.feature.webrtc.feature.audiontf.mvp.AudioNtfPresenter;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.androidutils.page.TioFragment;
import com.tiocloud.chat.util.TimeUtil;
import com.watayouxiang.webrtclib.TioWebRTC;
import com.watayouxiang.webrtclib.model.AudioDevice;

/**
 * author : TaoWang
 * date : 2020/5/22
 * desc :
 */
public class CallAudioNtfFragment extends TioFragment implements AudioNtfContract.View {
    private TioCallAudioNtfFragmentBinding binding;
    private AudioNtfPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TioCallAudioNtfFragmentBinding.inflate(inflater, container, false);
        presenter = new AudioNtfPresenter(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BarUtils.addMarginTopEqualStatusBarHeight(binding.ivReplyAvatar);
        BarUtils.addMarginTopEqualStatusBarHeight(binding.ivCallingAvatar);
        BarUtils.addMarginTopEqualStatusBarHeight(binding.localVideoView);

        presenter.reqUserInfo();
        RTCViewHolderImpl rtcViewHolder = new RTCViewHolderImpl(
                binding.localVideoView,
                binding.remoteVideoView);
        presenter.initRTCViews(rtcViewHolder);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
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
        binding.rlReply.setVisibility(View.VISIBLE);
        binding.tvReplyAgree.setVisibility(View.VISIBLE);
        binding.tvReplyAgree.setOnClickListener(view -> TioWebRTC.getInstance().callReply((byte) 1, null));
        binding.tvReplyDisagree.setOnClickListener(view -> TioWebRTC.getInstance().callReply((byte) 2, "busy now, call me later."));
    }

    @Override
    public void showCallingView() {
        binding.rlReply.setVisibility(View.GONE);
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
    public CallActivity getCallActivity() {
        return (CallActivity) getActivity();
    }

    @Override
    public void onUserInfoResp(UserInfoResp userInfoResp) {
        String name = userInfoResp.remarkname;
        if (TextUtils.isEmpty(name)) {
            name = String.valueOf(userInfoResp.nick);
        }
        binding.ivReplyAvatar.tio_roundAvatar(userInfoResp.avatar);
        binding.tvReplyNick.setText(name);
        binding.ivCallingAvatar.tio_roundAvatar(userInfoResp.avatar);
        binding.tvCallingNick.setText(name);
    }

    @Override
    public void onCountDownTimer(long l) {
        binding.tvCallingTimer.setText(TimeUtil.formatMS(l));
    }

    @Override
    public void changeWaitingView() {
        binding.tvReplyAgree.setVisibility(View.GONE);
        binding.tvTip.setText("接通中...");
    }
}
