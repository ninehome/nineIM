package com.tiocloud.chat.feature.webrtc.mvp;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.tiocloud.chat.feature.webrtc.data.CallNtf;
import com.tiocloud.chat.feature.webrtc.data.CallReq;
import com.tiocloud.chat.feature.webrtc.data.CallType;
import com.tiocloud.chat.feature.webrtc.feature.audiontf.CallAudioNtfFragment;
import com.tiocloud.chat.feature.webrtc.feature.audioreq.CallAudioReqFragment;
import com.tiocloud.chat.feature.webrtc.feature.videontf.CallVideoNtfFragment;
import com.tiocloud.chat.feature.webrtc.feature.videoreq.CallVideoReqFragment;
import com.watayouxiang.imclient.TioIMClient;
import com.tiocloud.chat.util.ActivityPermissionHelper;
import com.watayouxiang.webrtclib.TioWebRTC;
import com.watayouxiang.webrtclib.tool.PermissionTool;

import java.util.Arrays;
import java.util.List;

public class CallPresenter extends CallContract.Presenter {
    private ActivityPermissionHelper permissionHelper;

    public CallPresenter(CallContract.View view) {
        super(new CallModel(), view, false);
    }

    @Override
    public void init() {
        initPermissionHelper();
        permissionHelper.requestPermissions();
        // 取消 "App进入后台时，自动断开连接"
        TioIMClient.getInstance().setAutoDisconnectOnAppBackground(false);
    }

    @Override
    public void detachView() {
        super.detachView();
        // 恢复 "App进入后台时，自动断开连接"
        TioIMClient.getInstance().setAutoDisconnectOnAppBackground(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        permissionHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initPermissionHelper() {
        permissionHelper = new ActivityPermissionHelper(getView().getActivity(), Arrays.asList(PermissionTool.ALL_PERMISSIONS),
                new ActivityPermissionHelper.OnPermissionListener() {
                    @Override
                    public void onGranted() {
                        initPage();
                    }

                    @Override
                    public void onDenied(@NonNull List<String> deniedPermissions) {
                        CallType callType = getCallType();
                        if (callType == CallType.AUDIO_NTF || callType == CallType.VIDEO_NTF) {
                            TioWebRTC.getInstance().callReply((byte) 3, "permission denied");
                        }
                        getView().getActivity().finish();
                    }
                });
    }

    private void initPage() {
        CallType callType = getCallType();
        if (callType == CallType.AUDIO_NTF) {
            CallAudioNtfFragment fragment = new CallAudioNtfFragment();
            fragment.setContainerId(getView().getBinding().fragmentContainer.getId());
            getView().replaceFragment(fragment);
        } else if (callType == CallType.VIDEO_NTF) {
            CallVideoNtfFragment fragment = new CallVideoNtfFragment();
            fragment.setContainerId(getView().getBinding().fragmentContainer.getId());
            getView().replaceFragment(fragment);
        } else if (callType == CallType.AUDIO_REQ) {
            CallAudioReqFragment fragment = new CallAudioReqFragment();
            fragment.setContainerId(getView().getBinding().fragmentContainer.getId());
            getView().replaceFragment(fragment);
        } else if (callType == CallType.VIDEO_REQ) {
            CallVideoReqFragment fragment = new CallVideoReqFragment();
            fragment.setContainerId(getView().getBinding().fragmentContainer.getId());
            getView().replaceFragment(fragment);
        }
    }

    private CallType getCallType() {
        CallNtf callNtf = getView().getCallNtf();
        CallReq callReq = getView().getCallReq();
        if (callNtf != null) {// 来电
            if (callNtf.getType() == 1) {// 语音来电
                return CallType.AUDIO_NTF;
            } else if (callNtf.getType() == 2) {// 视频
                return CallType.VIDEO_NTF;
            }
        } else if (callReq != null) {// 去电
            if (callReq.getType() == 1) {// 语音
                return CallType.AUDIO_REQ;
            } else if (callReq.getType() == 2) {// 视频
                return CallType.VIDEO_REQ;
            }
        }
        return null;
    }
}
