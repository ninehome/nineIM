package com.tiocloud.chat.feature.webrtc.feature.videontf;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.BarUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.databinding.TioCallVideoNtfFragmentBinding;
import com.tiocloud.chat.feature.webrtc.CallActivity;
import com.tiocloud.chat.feature.webrtc.data.RTCViewHolderImpl;
import com.tiocloud.chat.feature.webrtc.feature.videontf.mvp.VideoNtfContract;
import com.tiocloud.chat.feature.webrtc.feature.videontf.mvp.VideoNtfPresenter;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.androidutils.page.TioFragment;
import com.tiocloud.chat.util.TimeUtil;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.webrtclib.TioWebRTC;

import org.webrtc.CameraVideoCapturer;

/**
 * author : TaoWang
 * date : 2020/5/22
 * desc :
 */
public class CallVideoNtfFragment extends TioFragment implements VideoNtfContract.View {
    private TioCallVideoNtfFragmentBinding binding;
    private VideoNtfPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TioCallVideoNtfFragmentBinding.inflate(inflater, container, false);
        presenter = new VideoNtfPresenter(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.reqUserInfo();
        BarUtils.addMarginTopEqualStatusBarHeight(binding.ivReplyAvatar);
        BarUtils.addMarginTopEqualStatusBarHeight(binding.localVideoView);

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
    public void onCountDownTimer(long l) {
        binding.tvCallingTimer.setText(TimeUtil.formatMS(l));
    }

    @Override
    public void onUserInfoResp(UserInfoResp userInfoResp) {
        binding.ivReplyAvatar.tio_roundAvatar(userInfoResp.avatar);
        String name = userInfoResp.remarkname;
        if (TextUtils.isEmpty(name)) {
            name = String.valueOf(userInfoResp.nick);
        }
        binding.tvReplyNick.setText(name);
    }

    @Override
    public void changeWaitingView() {
        binding.tvReplyAgree.setVisibility(View.GONE);
        binding.tvTip.setText(getString(R.string.jietongzhong));
    }

    @Override
    public CallActivity getCallActivity() {
        return (CallActivity) getActivity();
    }
}
