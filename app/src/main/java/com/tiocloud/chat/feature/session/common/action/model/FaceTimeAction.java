package com.tiocloud.chat.feature.session.common.action.model;

import com.blankj.utilcode.util.Utils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseCallAction;
import com.tiocloud.chat.feature.webrtc.CallActivity;
import com.tiocloud.chat.feature.webrtc.data.CallReq;

/**
 * author : TaoWang
 * date : 2020/3/5
 * desc :
 */
public class FaceTimeAction extends BaseCallAction {
    public FaceTimeAction() {
        super(R.drawable.icon_im_shipin, R.string.video_call);
    }

    @Override
    public void onClick() {
        int toUid = getToUid();
        if (toUid != 0) {
            CallReq callReq = new CallReq(toUid, 2);
            CallActivity.start(Utils.getApp(), callReq);
        } else {
            showToast("uid获取失败");
        }
    }
}
