package com.tiocloud.chat.widget.fragmentDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tiocloud.chat.R;

/**
 * author : TaoWang
 * date : 2020/3/3
 * desc :
 */
public abstract class BaseBottomSheetDialogFragment extends FixBottomSheetDialogFragment {

    public Activity mActivity;
    private Integer mHeight;
    private BottomSheetDialog bottomSheetDialog;

    // ====================================================================================
    // lifecycle
    // ====================================================================================

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dismiss();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        bottomSheetDialog = new BottomSheetDialog(mActivity);
        // 设置布局
        View view = LayoutInflater.from(mActivity).inflate(getLayoutResId(), null);
        bottomSheetDialog.setContentView(view);
        // 设置高度
        if (mHeight != null) {
            view.getLayoutParams().height = mHeight;
        }
        // 设置背景为透明
        Window window = bottomSheetDialog.getWindow();
        if (window != null) {
            window.findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        // 初始化界面
        initView(bottomSheetDialog);
        return bottomSheetDialog;
    }

    @Override
    public void onDestroy() {
        mActivity = null;
        mHeight = null;
        bottomSheetDialog = null;
        super.onDestroy();
    }

    // ====================================================================================
    // abstract
    // ====================================================================================

    protected abstract int getLayoutResId();

    protected abstract void initView(BottomSheetDialog dialog);

    // ====================================================================================
    // enhance
    // ====================================================================================

    public void show(FragmentManager manager, Integer height) {
        this.mHeight = height;
        super.show(manager, "hello");
    }

    public void show(FragmentManager manager) {
        super.show(manager, "hello");
    }

    public void setScrollDownClose(BottomSheetDialog dialog) {
        dialog.setCancelable(false);// 设置下滑关闭
        dialog.setCanceledOnTouchOutside(true);// 外部点击取消
    }

    public void setBGTransparent(BottomSheetDialog dialog) {
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.dimAmount = 0.0f;//对话框外部的背景设为透明
            window.setAttributes(lp);
        }
    }

    protected final <T extends View> T findViewById(@IdRes int id) {
        if (bottomSheetDialog == null)
            throw new NullPointerException("rootView is null");
        return bottomSheetDialog.findViewById(id);
    }
}
