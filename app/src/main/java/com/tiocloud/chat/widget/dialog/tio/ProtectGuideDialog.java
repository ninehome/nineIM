package com.tiocloud.chat.widget.dialog.tio;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.tiocloud.chat.R;
import com.watayouxiang.httpclient.TioWebUrl;
import com.watayouxiang.androidutils.feature.TioBrowserActivity;
import com.tiocloud.chat.preferences.ConfigPreferences;
import com.watayouxiang.androidutils.widget.dialog.TioDialog;
import com.watayouxiang.httpclient.prefernces.HttpCache;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/13
 *     desc   :
 * </pre>
 */
public class ProtectGuideDialog extends TioDialog {
    @NonNull
    private Activity mContext;
    @NonNull
    private OnConfirmListener onConfirmListener;
    private boolean agreeProtectGuide = ConfigPreferences.getAgreeProtectGuide();

    public ProtectGuideDialog(@NonNull Activity context, @NonNull OnConfirmListener onConfirmListener) {
        this.mContext = context;
        this.onConfirmListener = onConfirmListener;
    }

    @Override
    protected int getDialogContentId() {
        return R.layout.tio_dialog_protect_guide;
    }

    @Override
    protected void initDialogContentView() {
        super.initDialogContentView();
        View tv_userProtocol = findViewById(R.id.tv_userProtocol);
        tv_userProtocol.setOnClickListener(v -> {
            String url = "https://xxsj.wangliantong.com";
            TioBrowserActivity.start(mContext, url);
        });

        View tv_privatePolicy = findViewById(R.id.tv_privatePolicy);
        tv_privatePolicy.setOnClickListener(v -> {
            String url = "https://yszc.wangliantong.com";
            TioBrowserActivity.start(mContext, url);
        });

        View tv_negativeBtn = findViewById(R.id.tv_negativeBtn);
        tv_negativeBtn.setOnClickListener(v -> {
            // 存储状态
            ConfigPreferences.saveAgreeProtectGuide(false);
            //退出整个APP
            AppUtils.exitApp();
        });

        View tv_positiveBtn = findViewById(R.id.tv_positiveBtn);
        tv_positiveBtn.setOnClickListener(v -> {
            // 存储状态
            ConfigPreferences.saveAgreeProtectGuide(true);
            // 关闭弹窗
            dismiss();
            // 回调
            onConfirmListener.onConfirm();
        });
    }

    /**
     * 检测是否同意
     */
    public void checkConfirm() {
        // 没有同意才显示
        if (!agreeProtectGuide) {
            super.show_unCancel(mContext);
        } else {
            onConfirmListener.onConfirm();
        }
    }

    public interface OnConfirmListener {
        /**
         * 同意回调
         */
        void onConfirm();
    }
}
