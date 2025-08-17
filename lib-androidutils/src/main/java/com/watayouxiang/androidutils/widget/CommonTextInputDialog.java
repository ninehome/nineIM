package com.watayouxiang.androidutils.widget;

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
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.watayouxiang.androidutils.R;

/**
 * author : TaoWang
 * date : 2020-02-20
 * desc :
 */
public class CommonTextInputDialog extends Dialog {

    OnBtnListener onBtnListener;

    private EditText editText;

    public CommonTextInputDialog(@NonNull Context context) {
        super(context, R.style.apply_friend_dialog);
        setContentView(R.layout.dialog_apply_friend);

        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams p = window.getAttributes();
        p.height = (int) (ScreenUtils.getScreenHeight() * 0.6);
        p.width = ScreenUtils.getScreenWidth();
        window.setAttributes(p);
        window.setWindowAnimations(R.style.main_menu_animStyle);
        this.onBtnListener = onBtnListener;
        editText = findViewById(R.id.edit);
        findViewById(R.id.iv_back).setOnClickListener(v -> {
            if (onBtnListener != null){
                onBtnListener.onClickNegative(v, this);
            }
        });
        findViewById(R.id.btn_send).setOnClickListener(v -> {
            if (onBtnListener != null){
                onBtnListener.onClickPositive(v, ((EditText)findViewById(R.id.edit)).getText().toString(), this);
            }
        });
//        setCancelable(false);
//        setCanceledOnTouchOutside(true);
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (onBtnListener != null){
                    onBtnListener.onClickNegative(findViewById(R.id.iv_back), CommonTextInputDialog.this);
                }
            }
        });

        findViewById(R.id.iv_clear).setOnClickListener(v -> ((EditText)findViewById(R.id.edit)).setText(""));
    }

    public CommonTextInputDialog setPositiveText(String text){
        ((QMUIRoundButton)findViewById(R.id.btn_send)).setText(text);
        return this;
    }

    public CommonTextInputDialog showClearButton(boolean show){
        findViewById(R.id.iv_clear).setVisibility(show?View.VISIBLE:View.GONE);
        return this;
    }

    public CommonTextInputDialog setTopTitle(String title){
        ((TextView)findViewById(R.id.tv1)).setText(title);
        return this;
    }

    public CommonTextInputDialog hintSubTitle(){
        findViewById(R.id.tv2).setVisibility(View.GONE);
        return this;
    }

    public CommonTextInputDialog setSubTitle(String subTitle){
        ((TextView)findViewById(R.id.tv2)).setText(subTitle);
        return this;
    }

    public CommonTextInputDialog setEditHeight(int height){
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) editText.getLayoutParams();
        layoutParams.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getContext().getResources().getDisplayMetrics()));
        editText.setLayoutParams(layoutParams);
        return this;
    }

    public CommonTextInputDialog setMaxLimit(int max){
        findViewById(R.id.text_limit).setVisibility(View.VISIBLE);
        editText.setMaxEms(max);
        ((EditText)findViewById(R.id.edit)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ((TextView)findViewById(R.id.text_limit)).setText(editText.getText().length()+"/"+max);
            }
        });
        return this;
    }

    public CommonTextInputDialog setOnBtnListener(OnBtnListener onBtnListener){
        this.onBtnListener = onBtnListener;
        return this;
    }

    public CommonTextInputDialog setEdittext(String etText){
        editText.setText(etText);
        return this;
    }

    public CommonTextInputDialog setHintText(String hintText){
        editText.setHint(hintText);
        return this;
    }


    public interface OnBtnListener {
        void onClickPositive(View view, String submitTxt, CommonTextInputDialog dialog);

        void onClickNegative(View view, CommonTextInputDialog dialog);
    }
}
