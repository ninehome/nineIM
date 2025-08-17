package com.tiocloud.chat.feature.forbidden;

import android.content.Context;

import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.tiocloud.chat.R;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/05
 *     desc   :
 * </pre>
 */
public class SilentTimeSingleChoiceDialog {
    private final QMUIDialog qmuiDialog;
    private OnDialogCallback onDialogCallback;

    public SilentTimeSingleChoiceDialog(Context context) {
        String[] items = new String[]{context.getString(R.string.ten_minnute),
                context.getString(R.string.one_hour), context.getString(R.string.twenty_four_hour),
                context.getString(R.string.long_forbiden_language)};

        qmuiDialog = new QMUIDialog.CheckableDialogBuilder(context)
                .setSkinManager(QMUISkinManager.defaultInstance(context))
                .addItems(items, (dialog, which) -> {
                    if (onDialogCallback != null) {
                        onDialogCallback.onClickItem(getSecondTime(which), SilentTimeSingleChoiceDialog.this);
                    }
                })
                .setTitle(context.getString(R.string.forbiden_language_time_length))
                //.setCheckedIndex(0)
                //.addAction("取消", (dialog, index) -> dialog.dismiss())
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog);
    }

    private long getSecondTime(int which) {
        switch (which) {
            case 0:
                return 10 * 60;
            case 1:
                return 60 * 60;
            case 2:
                return 24 * 60 * 60;
            case 3:
                return Long.MAX_VALUE;
        }
        return -1;
    }

    public void dismiss() {
        qmuiDialog.dismiss();
    }

    public void show() {
        qmuiDialog.show();
    }

    public SilentTimeSingleChoiceDialog setOnDialogCallback(OnDialogCallback callback) {
        this.onDialogCallback = callback;
        return this;
    }

    public interface OnDialogCallback {
        void onClickItem(long secondTime, SilentTimeSingleChoiceDialog dialog);
    }
}
