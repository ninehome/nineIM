package com.watayouxiang.androidutils.widget;

import android.app.Activity;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.watayouxiang.androidutils.R;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/29
 *     desc   :
 *
 *     注意：务必使用 QMUITheme 主题
 * </pre>
 */
public class ShareBottomSheetDialog {
    public static final int TAG_SHARE_QQ = 0;
    public static final int TAG_SHARE_WECHAT = 1;
    public static final int TAG_SHARE_WECHAT_MOMENT = 2;
    private final QMUIBottomSheet.BottomGridSheetBuilder builder;

    public ShareBottomSheetDialog(Activity activity, OnItemClickListener listener) {
        builder = new QMUIBottomSheet.BottomGridSheetBuilder(activity)
                .addItem(R.drawable.androidutils_ic_qq, "QQ", TAG_SHARE_QQ, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.androidutils_ic_weixin, "微信", TAG_SHARE_WECHAT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.androidutils_ic_wechatmoments, "朋友圈", TAG_SHARE_WECHAT_MOMENT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .setAddCancelBtn(true)
                .setRadius(SizeUtils.dp2px(19))
                .setAllowDrag(true)
                .setTitle("选择要分享的平台")
                .setSkinManager(QMUISkinManager.defaultInstance(activity))
                .setOnSheetItemClickListener((dialog, itemView) -> {
                    dialog.dismiss();
                    int tag = (int) itemView.getTag();
                    switch (tag) {
                        case TAG_SHARE_QQ:
                            listener.onClickQQ(dialog, itemView);
                            break;
                        case TAG_SHARE_WECHAT:
                            listener.onClickWX(dialog, itemView);
                            break;
                        case TAG_SHARE_WECHAT_MOMENT:
                            listener.onClickWXMoments(dialog, itemView);
                            break;
                    }
                });
    }

    public void show() {
        builder.build().show();
    }

    public interface OnItemClickListener {
        void onClickQQ(QMUIBottomSheet dialog, View itemView);

        void onClickWX(QMUIBottomSheet dialog, View itemView);

        void onClickWXMoments(QMUIBottomSheet dialog, View itemView);
    }
}
