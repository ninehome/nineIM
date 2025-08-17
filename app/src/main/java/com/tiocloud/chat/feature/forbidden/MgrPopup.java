package com.tiocloud.chat.feature.forbidden;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ArrayAdapter;

import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.tiocloud.chat.R;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/07
 *     desc   : 管理 Popup
 * </pre>
 */
public class MgrPopup {

    private final QMUIPopup mLeftListPopup;
    private final Context context;
    private OnPopupListener onPopupListener;
    private View mAnchor;

    /**
     * @param forbidden 是否被禁言
     */
    public MgrPopup(Context context, int myRole, GroupUserListResp.GroupMember member, boolean forbidden) {
        this.context = context;

        String[] listItems = null;
//        if (member.grouprole == 1){
//            listItems = new String[]{forbidden ? "取消禁言" : "禁言"};
//        }else {
//            listItems = new String[]{forbidden ? "取消禁言" : "禁言", member.grouprole == 2 ? "设为管理员" : "取消管理员"};
//        }
        if (myRole == 1){
            //群主
            listItems = new String[]{forbidden ? context.getString(R.string.exit_forbiden_language)
                    : context.getString(R.string.forbiden_language),
                    context.getString(R.string.delete), member.grouprole == 2
                    ? context.getString(R.string.set_manager) : context.getString(R.string.exit_manager)};
        }else if (myRole == 3 && member.grouprole == 2){
            listItems = new String[]{forbidden ? context.getString(R.string.exit_forbiden_language) :
                    context.getString(R.string.forbiden_language),context.getString(R.string.delete)};
        }else {
            listItems = new String[0];
        }

        List<String> data = new ArrayList<>();
        Collections.addAll(data, listItems);

        mLeftListPopup = QMUIPopups.listPopup(context,
                QMUIDisplayHelper.dp2px(context, 200),
                QMUIDisplayHelper.dp2px(context, 200),
                new ArrayAdapter<>(context, R.layout.tio_simple_list_item, data),
                (adapterView, view, i, l) -> {
                    if (onPopupListener != null) {
                        if (i == 0) {
                            onPopupListener.onClickSilentItem(MgrPopup.this);
                        }else if (i == 1){
                            //删除
                            onPopupListener.onClickDeleteMember(MgrPopup.this);
                        }else if (i == 2) {
                            onPopupListener.onClickSetManager(MgrPopup.this);
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
                    if (mAnchor != null) cancelAnchorBg(mAnchor);
                });
    }

    public MgrPopup setOnPopupListener(OnPopupListener onPopupListener) {
        this.onPopupListener = onPopupListener;
        return this;
    }

    public void show(View v) {
        mAnchor = v;
        mLeftListPopup
                .offsetYIfBottom(-v.getHeight() / 2)
                .offsetYIfTop(-v.getHeight() / 2)
                .show(v);
        if (mAnchor != null) setAnchorBg(mAnchor);
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
    // 背景
    // ====================================================================================

    private Drawable originalBackground;

    private void setAnchorBg(View view) {
        originalBackground = view.getBackground();
        view.setBackground(new ColorDrawable(view.getResources().getColor(R.color.gray_e6e6e6)));
    }

    private void cancelAnchorBg(View view) {
        view.setBackground(originalBackground);
    }

    // ====================================================================================
    // inner class
    // ====================================================================================

    public interface OnPopupListener {
        void onClickSilentItem(MgrPopup popup);
        void onClickSetManager(MgrPopup popup);
        void onClickDeleteMember(MgrPopup popup);
        void onDismiss();
    }

    public abstract static class OnSimplePopupListener implements OnPopupListener {
        @Override
        public void onDismiss() {

        }
    }
}
