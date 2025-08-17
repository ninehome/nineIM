package com.tiocloud.chat.feature.webrtc.feature.videoreq;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.BarUtils;
import com.tiocloud.chat.databinding.TioCallVideoReqFragmentBinding;
import com.tiocloud.chat.feature.webrtc.data.RTCViewHolderImpl;
import com.tiocloud.chat.feature.webrtc.feature.videoreq.mvp.VideoReqContract;
import com.tiocloud.chat.feature.webrtc.feature.videoreq.mvp.VideoReqPresenter;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.androidutils.page.TioFragment;
import com.tiocloud.chat.util.TimeUtil;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.webrtclib.TioWebRTC;

import org.webrtc.CameraVideoCapturer;

/**
 * author : TaoWang
 * date : 2020/5/26
 * desc :
 */
public class CallVideoReqFragment extends TioFragment implements VideoReqContract.View {
    private TioCallVideoReqFragmentBinding binding;
    private VideoReqPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TioCallVideoReqFragmentBinding.inflate(inflater, container, false);
        presenter = new VideoReqPresenter(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        BarUtils.addMarginTopEqualStatusBarHeight(binding.ivCallAvatar);
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
        binding.getRoot().setOnClickListener(view -> binding.rlCalling.setVisibility(binding.rlCalling.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE));
        binding.tvCallingHangup.setOnClickListener(view -> TioWebRTC.getInstance().hangUp());
        binding.tvCallingSwitchCamera.setOnClickListener(view -> TioWebRTC.getInstance().switchCamera(new CameraVideoCapturer.CameraSwitchHandler() {
            @Override
            public void onCameraSwitchDone(boolean isFontCamera) {
                binding.tvCallingSwitchCamera.setSelected(!isFontCamera);
            }

            @Override
            public void onCameraSwitchError(String s) {
                TioToast.showShort(s);
            }
        }));
        binding.localVideoView.setOnClickListener(v -> {
            if (TioWebRTC.getInstance().isSwitchVideoSink()) {
                TioWebRTC.getInstance().switchVideoSink(false);
            } else {
                TioWebRTC.getInstance().switchVideoSink(true);
            }
        });
    }

    @Override
    public void onUserInfoResp(UserInfoResp userInfoResp) {
        binding.ivCallAvatar.tio_roundAvatar(userInfoResp.avatar);
        String name = userInfoResp.remarkname;
        if (TextUtils.isEmpty(name)) {
            name = String.valueOf(userInfoResp.nick);
        }
        binding.tvCallNick.setText(name);
    }

    @Override
    public void onCountDownTimer(long l) {
        binding.tvCallingTimer.setText(TimeUtil.formatMS(l));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}
