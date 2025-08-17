package com.tiocloud.chat.feature.webrtc.feature.audioreq.mvp;

import androidx.fragment.app.FragmentActivity;

import com.tiocloud.chat.feature.webrtc.CallActivity;
import com.tiocloud.chat.feature.webrtc.data.CallReq;
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
public class AudioReqModel extends AudioReqContract.Model {
    @Override
    public void reqUserInfo(int uid, AudioReqContract.View view) {
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
    public CallReq getCallReq(FragmentActivity activity) {
        if (activity instanceof CallActivity) {
            CallActivity callActivity = (CallActivity) activity;
            return callActivity.getCallReq();
        }
        return null;
    }

    @Override
    public int getToUid(FragmentActivity activity) {
        CallReq callReq = getCallReq(activity);
        if (callReq != null) {
            return callReq.getToUid();
        }
        return -1;
    }
}
