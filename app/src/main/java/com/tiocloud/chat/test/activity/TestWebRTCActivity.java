package com.tiocloud.chat.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.webrtc.data.RTCViewHolderImpl;
import com.watayouxiang.demoshell.DemoActivity;
import com.watayouxiang.demoshell.ListData;
import com.watayouxiang.imclient.model.body.webrtc.WxCall02Ntf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall04ReplyNtf;
import com.tiocloud.chat.util.ActivityPermissionHelper;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.webrtclib.TioWebRTC;
import com.watayouxiang.webrtclib.listener.OnSimpleRTCListener;
import com.watayouxiang.webrtclib.tool.PermissionTool;
import com.watayouxiang.webrtclib.view.PercentFrameLayout;

import org.webrtc.SurfaceViewRenderer;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020/4/29
 * desc :
 */
public class TestWebRTCActivity extends DemoActivity {

    private ActivityPermissionHelper permissionHelper = new ActivityPermissionHelper(this,
            Arrays.asList(PermissionTool.ALL_PERMISSIONS),
            new ActivityPermissionHelper.OnPermissionListener() {
                @Override
                public void onGranted() {
                    initWebRTC();
                }

                @Override
                public void onDenied(@NonNull List<String> deniedPermissions) {
                    TestWebRTCActivity.this.finish();
                }
            });
    private RTCViewHolderImpl rtcViewHolder;

    @Override
    protected int getHolderViewId() {
        return R.layout.tio_test_webrtc_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        permissionHelper.requestPermissions();
    }

    private void initWebRTC() {
        SurfaceViewRenderer local_video_view = findViewById(R.id.local_video_view);
        SurfaceViewRenderer remote_video_view = findViewById(R.id.remote_video_view);
        PercentFrameLayout local_video_layout = findViewById(R.id.local_video_layout);
        PercentFrameLayout remote_video_layout = findViewById(R.id.remote_video_layout);

        rtcViewHolder = new RTCViewHolderImpl(local_video_view, remote_video_view);
        TioWebRTC.getInstance().init(rtcViewHolder);
        TioWebRTC.getInstance().registerOnRTCListener(new OnSimpleRTCListener() {
            @Override
            public void onCall(WxCall02Ntf call) {
                TioToast.show(TestWebRTCActivity.this,
                        String.format(Locale.getDefault(), "onCall: uid = %d, type = %d",
                                call.fromuid, call.type));
            }

            @Override
            public void onCallReply(WxCall04ReplyNtf callReply) {
                String tip = callReply.result == 1 ? "同意通话" : callReply.reason;
                TioToast.show(TestWebRTCActivity.this, tip);
            }
        });
    }

    @Override
    protected ListData getListData() {
        return new ListData()
                .addClick("init（初始化）", v -> TioWebRTC.getInstance().init(rtcViewHolder))
                .addClick("call（去电）", v -> {
                    Integer uid = getUid();
                    if (uid != null) {
                        TioWebRTC.getInstance().call(uid, (byte) 2);
                    }
                })
                .addClick("callCancel（取消去电）", v -> TioWebRTC.getInstance().callCancel())
                .addClick("callReply-agree（来电回复-同意）", v -> TioWebRTC.getInstance().callReply((byte) 1, null))
                .addClick("callReply-disagree（来电回复-拒绝）", v -> TioWebRTC.getInstance().callReply((byte) 2, "busy now, call me later."))
                .addClick("hangUp（挂断）", v -> TioWebRTC.getInstance().hangUp())
                .addClick("toggleAudioDevice（切换音频）", v -> TioWebRTC.getInstance().toggleAudioDevice())
                .addClick("switchCamera（切换摄像头）", v -> TioWebRTC.getInstance().switchCamera(null))
                .addClick("release（资源释放）", v -> TioWebRTC.getInstance().release())
                ;
    }

    private Integer getUid() {
        try {
            String _uid = ((EditText) findViewById(R.id.et_input)).getText().toString();
            return Integer.parseInt(_uid);
        } catch (Exception ignored) {
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        permissionHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TioWebRTC.getInstance().release();
    }
}
