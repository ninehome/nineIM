package com.tiocloud.chat.widget.dialog.tio;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.tiocloud.chat.R;
import com.tiocloud.chat.util.ScreenUtil;

/**
 * author : TaoWang
 * date : 2020-02-20
 * desc :
 */
public class SexSelectDialog extends Dialog {

    OnBtnListener onBtnListener;

    private TextView sex1,sex2,sex3;

    public SexSelectDialog(@NonNull Context context) {
        super(context, R.style.apply_friend_dialog);
        setContentView(R.layout.sex_select_dialog);

        sex1 = findViewById(R.id.sex1);
        sex2 = findViewById(R.id.sex2);
        sex3 = findViewById(R.id.sex3);

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBtnListener != null){
                    onBtnListener.onClickNegative(findViewById(R.id.iv_back), SexSelectDialog.this);
                }
            }
        });

        sex1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBtnListener != null){
                    onBtnListener.onClickPositive(v, 1, SexSelectDialog.this);
                }
            }
        });

        sex2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBtnListener != null){
                    onBtnListener.onClickPositive(v, 2, SexSelectDialog.this);
                }
            }
        });

        sex3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBtnListener != null){
                    onBtnListener.onClickPositive(v, 3, SexSelectDialog.this);
                }
            }
        });

        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams p = window.getAttributes();
        p.height = (int) (/*ScreenUtils.getScreenHeight() * 0.45*/ScreenUtil.dp2px(335));
        p.width = ScreenUtils.getScreenWidth();
        window.setAttributes(p);
        window.setWindowAnimations(R.style.main_menu_animStyle);
        this.onBtnListener = onBtnListener;

        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (onBtnListener != null){
                    onBtnListener.onClickNegative(null, SexSelectDialog.this);
                }
            }
        });
    }

    public SexSelectDialog setOnBtnListener(SexSelectDialog.OnBtnListener onBtnListener){
        this.onBtnListener = onBtnListener;
        return this;
    }


    public interface OnBtnListener {
        void onClickPositive(View view, int sex, SexSelectDialog dialog);
        void onClickNegative(View view, SexSelectDialog dialog);
    }
}
