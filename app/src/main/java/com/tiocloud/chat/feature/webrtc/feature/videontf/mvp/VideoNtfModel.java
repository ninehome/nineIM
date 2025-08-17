package com.tiocloud.chat.feature.webrtc.feature.videontf.mvp;

import androidx.fragment.app.FragmentActivity;

import com.tiocloud.chat.feature.webrtc.CallActivity;
import com.tiocloud.chat.feature.webrtc.data.CallNtf;
import com.watayouxiang.httpclient.callback.TioCallbackImpl;
import com.watayouxiang.httpclient.model.request.UserInfoReq;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.androidutils.util.TioLogger;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/6/4
 *     desc   :
 * </pre>
 */
public class VideoNtfModel extends VideoNtfContract.Model {
    @Override
    public void reqUserInfo(int uid, VideoNtfContract.View view) {
        if (uid == -1) {
            TioLogger.d("get uid failed!");
            return;
        }
        UserInfoReq userInfoReq = new UserInfoReq(String.valueOf(uid));
        userInfoReq.setCancelTag(this);
        userInfoReq.get(new TioCallbackImpl<UserInfoResp>() {
            @Override
            public void onTioSuccess(UserInfoResp userInfoResp) {
                view.onUserInfoResp(userInfoResp);
            }

            @Override
            public void onTioError(String msg) {
                TioLogger.d("http UserInfoResp error: " + msg);
            }
        });
    }

    @Override
    public int getFromUid(FragmentActivity activity) {
        if (activity instanceof CallActivity) {
            CallActivity callActivity = (CallActivity) activity;
            CallNtf callNtf = callActivity.getCallNtf();
            return callNtf.getFromUid();
        }
        return -1;
    }
}
