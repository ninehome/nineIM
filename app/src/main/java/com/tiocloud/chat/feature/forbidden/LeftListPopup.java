package com.tiocloud.chat.feature.forbidden;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.tiocloud.chat.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/05
 *     desc   : 左侧 列表类型的 Popup
 * </pre>
 */
public class LeftListPopup {

    private final QMUIPopup mLeftListPopup;
    private final Context context;
    private OnPopupListener onPopupListener;

    /**
     * @param forbidden 是否被禁言
     */
    public LeftListPopup(Context context, boolean forbidden) {
        this.context = context;

        String[] listItems = new String[]{
                "@TA",
                forbidden ? "取消禁言" : "禁言","踢出群聊"
        };
        List<String> data = new ArrayList<>();
        Collections.addAll(data, listItems);

        mLeftListPopup = QMUIPopups.listPopup(context,
                QMUIDisplayHelper.dp2px(context, 100),
                QMUIDisplayHelper.dp2px(context, 200),
                new ArrayAdapter<>(context, R.layout.tio_simple_list_item, data),
                (adapterView, view, i, l) -> {
                    if (onPopupListener != null) {
                        if (i == 0) {
                            onPopupListener.onClickAitItem(LeftListPopup.this);
                        } else if (i == 1) {
                            onPopupListener.onClickSilentItem(LeftListPopup.this);
                        }else if (i == 2){
                            onPopupListener.onClickKickItem(LeftListPopup.this);
                        }
                    }
                })
                // 配置
                .radius(QMUIDisplayHelper.dp2px(context, 4))
                .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                .shadow(true)
                .arrow(false)
                .skinManager(QMUISkinManager.defaultInstance(context))
                .onDismiss(() -> {
                    if (onPopupListener != null) {
                        onPopupListener.onDismiss();
                    }
                })
                .offsetX(QMUIDisplayHelper.dp2px(context, 50))
                .offsetYIfBottom(QMUIDisplayHelper.dp2px(context, -20))
                .offsetYIfTop(QMUIDisplayHelper.dp2px(context, -20));
    }

    public LeftListPopup setOnPopupListener(OnPopupListener onPopupListener) {
        this.onPopupListener = onPopupListener;
        return this;
    }

    public void show(View v) {
        mLeftListPopup.show(v);
    }

    public void dismiss() {
        if (mLeftListPopup != null) {
            mLeftListPopup.dismiss();
        }
    }

    private Context getContext() {
        return context;
    }

    // ====================================================================================
    // inner class
    // ====================================================================================

    public interface OnPopupListener {
        void onClickAitItem(LeftListPopup popup);

        void onClickSilentItem(LeftListPopup popup);

        void onClickKickItem(LeftListPopup popup);

        void onDismiss();
    }

    public abstract static class OnSimplePopupListener implements OnPopupListener {
        @Override
        public void onDismiss() {

        }
    }
}
