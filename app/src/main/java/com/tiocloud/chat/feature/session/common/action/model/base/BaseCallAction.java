package com.tiocloud.chat.feature.session.common.action.model.base;

import com.tiocloud.chat.feature.session.p2p.P2PSessionActivity;
import com.watayouxiang.androidutils.widget.TioToast;

import java.lang.ref.WeakReference;

/**
 * author : TaoWang
 * date : 2020/5/26
 * desc :
 */
public abstract class BaseCallAction extends BaseAction {

    private transient WeakReference<P2PSessionActivity> activityWeakReference;

    public BaseCallAction(int iconResId, int titleId) {
        super(iconResId, titleId);
    }

    public int getToUid() {
        try {
            P2PSessionActivity p2pActivity = activityWeakReference.get();
            String uid = p2pActivity.getUid();
            return Integer.parseInt(uid);
        } catch (Exception e) {
            return 0;
        }
    }

    public void setP2PActivity(P2PSessionActivity p2pActivity) {
        activityWeakReference = new WeakReference<>(p2pActivity);
    }

    public void showToast(String msg) {
        if (activityWeakReference != null && activityWeakReference.get() != null) {
            TioToast.showShort(msg);
        }
    }
}
