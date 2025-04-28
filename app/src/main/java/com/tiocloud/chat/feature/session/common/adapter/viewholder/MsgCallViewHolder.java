package com.tiocloud.chat.feature.session.common.adapter.viewholder;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.adapter.MsgAdapter;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.base.MsgBaseViewHolder;
import com.watayouxiang.imclient.model.HangUpType;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgCall;
import com.tiocloud.chat.util.StringUtil;

/**
 * author : TaoWang
 * date : 2020/5/26
 * desc :
 */
public class MsgCallViewHolder extends MsgBaseViewHolder {
    private TextView bodyTextView;
    private InnerMsgCall callInfo;

    public MsgCallViewHolder(MsgAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int contentResId() {
        return R.layout.tio_msg_item_call;
    }

    @Override
    protected void inflateContent() {
        bodyTextView = findViewById(R.id.tv_message);
    }

    @Override
    protected void bindContent(BaseViewHolder holder) {
        callInfo = (InnerMsgCall) getMessage().getContentObj();
        if (callInfo == null) return;
        setTvDrawable(callInfo);
        setTvText(callInfo);
    }

    private void setTvText(InnerMsgCall call) {
        if (call == null) return;
        String showText = null;
        HangUpType hangupType = call.getHangupType();
        if (hangupType != null) {
            boolean isReq = getMessage().isSendMsg();
            int callType = 0;
            if (call.calltype == 11) {
                callType = 1;
            } else if (call.calltype == 10) {
                callType = 2;
            }
            showText = hangupType.getShowText(isReq, call.duration, callType);
        }
        bodyTextView.setText(StringUtil.nonNull(showText));
    }

    private void setTvDrawable(InnerMsgCall call) {
        if (call == null) return;
        boolean isRight = getMessage().isSendMsg();
        Drawable drawable = null;
        if (call.calltype == 10) {
            // 视频
            if (isRight) {
                drawable = ResourceUtils.getDrawable(R.drawable.icon_im_video01);
            } else {
                drawable = ResourceUtils.getDrawable(R.drawable.icon_im_video02);
            }
        } else if (call.calltype == 11) {
            // 音频
            if (isRight) {
                drawable = ResourceUtils.getDrawable(R.drawable.icon_im_call);
            } else {
                drawable = ResourceUtils.getDrawable(R.drawable.icon_im_call);
            }
        }
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        if (drawable != null) {
            if (getMessage().isSendMsg()) {
                bodyTextView.setCompoundDrawables(null, null, drawable, null);
            } else {
                bodyTextView.setCompoundDrawables(drawable, null, null, null);
            }
            bodyTextView.setCompoundDrawablePadding(SizeUtils.dp2px(4));
        }
    }

    @Override
    protected boolean isShowContentBg() {
        return true;
    }

    // ====================================================================================
    // 点击拨打电话
    // ====================================================================================

//    @Override
//    protected View.OnClickListener onContentClick() {
//        return v -> {
//            Integer callUid = getCallUid();
//            Integer callType = getCallType();
//            if (callType != null && callUid != null) {
//                CallReq callReq = new CallReq(callUid, callType);
//                CallActivity.start(UIKit.getContext(), callReq);
//            }
//        };
//    }
//
//    private Integer getCallType() {
//        Integer type = null;
//        if (callInfo != null) {
//            int calltype = callInfo.calltype;
//            if (calltype == 10) {// 视频
//                type = 2;
//            } else if (calltype == 11) {// 音频
//                type = 1;
//            }
//        }
//        return type;
//    }
//
//    private Integer getCallUid() {
//        Integer uid = null;
//        Context context = getContext();
//        if (context instanceof P2PSessionActivity) {
//            P2PSessionActivity activity = (P2PSessionActivity) context;
//            String _uid = activity.getUid();
//            try {
//                if (_uid != null) {
//                    uid = Integer.parseInt(_uid);
//                }
//            } catch (Exception ignored) {
//            }
//        }
//        return uid;
//    }
}
