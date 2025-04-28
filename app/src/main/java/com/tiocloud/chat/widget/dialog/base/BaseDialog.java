package com.tiocloud.chat.widget.dialog.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import androidx.annotation.StyleRes;

import com.tiocloud.chat.R;

/**
 * author : TaoWang
 * date : 2020/3/3
 * desc : tioChat 定制 dialog
 */
public class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context, R.style.tio_dialog);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LayoutParams lp = window.getAttributes();
        lp.alpha = 1f;
        lp.gravity = Gravity.CENTER;
        lp.height = LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }

    /**
     * 设置动画
     */
    public void setAnimation(@StyleRes int resId) {
        Window window = getWindow();
        window.setWindowAnimations(resId);
    }

    /**
     * 设置透明度
     *
     * @param alpha 0 完全透明，1 不透明
     */
    public void setAlpha(float alpha) {
        Window window = getWindow();
        LayoutParams lp = window.getAttributes();
        lp.alpha = alpha;
        window.setAttributes(lp);
    }

    /**
     * 设置重心
     *
     * @param gravity {@link Gravity}
     */
    public void setGravity(int gravity) {
        Window window = getWindow();
        LayoutParams lp = window.getAttributes();
        lp.gravity = gravity;
        window.setAttributes(lp);
    }

    /**
     * 隐藏头部导航栏状态栏
     */
    public void skipTools() {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置全屏显示
     */
    public void setFullScreen() {
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        LayoutParams lp = window.getAttributes();
        lp.width = LayoutParams.FILL_PARENT;
        lp.height = LayoutParams.FILL_PARENT;
        window.setAttributes(lp);
    }

    /**
     * 设置宽度match_parent
     */
    public void setFullScreenWidth() {
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        LayoutParams lp = window.getAttributes();
        lp.width = LayoutParams.FILL_PARENT;
        lp.height = LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    /**
     * 设置高度为match_parent
     */
    public void setFullScreenHeight() {
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        LayoutParams lp = window.getAttributes();
        lp.width = LayoutParams.WRAP_CONTENT;
        lp.height = LayoutParams.FILL_PARENT;
        window.setAttributes(lp);
    }

    public void setOnWhole() {
        getWindow().setType(LayoutParams.TYPE_SYSTEM_ALERT);
    }

    /**
     * 把背景设置成透明
     */
    public void setBackgroundTransparent() {
        this.getWindow().clearFlags(LayoutParams.FLAG_DIM_BEHIND);//去掉这句话，背景会变暗
    }

    /**
     * 资源释放
     */
    @Override
    public void dismiss() {
        super.dismiss();
    }

}
