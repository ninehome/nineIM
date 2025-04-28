package com.watayouxiang.androidutils.widget.dialog.oper;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.watayouxiang.androidutils.R;

/**
 * author : TaoWang
 * date : 2020-03-02
 * desc : 操作弹窗
 */
public class EasyOperDialog extends TioOperDialog {
    private final CharSequence titleTxt;
    private final String positiveBtnTxt;
    private final String negativeBtnTxt;
    private final OnBtnListener onBtnListener;
    private final int titleGravity;
    private final boolean posBtnShowRedBg;

    private EasyOperDialog(CharSequence titleTxt,
                           String positiveBtnTxt,
                           String negativeBtnTxt,
                           OnBtnListener onBtnListener,
                           int titleGravity, boolean posBtnShowRedBg) {
        this.titleTxt = titleTxt;
        this.positiveBtnTxt = positiveBtnTxt;
        this.negativeBtnTxt = negativeBtnTxt;
        this.onBtnListener = onBtnListener;
        this.titleGravity = titleGravity;
        this.posBtnShowRedBg = posBtnShowRedBg;
    }


    @Override
    protected void initPositiveBtn(final TextView tv_positiveBtn) {
        if (posBtnShowRedBg){
            tv_positiveBtn.setTextColor(Color.parseColor("#FFFF4D4D"));
            tv_positiveBtn.setBackgroundResource(R.drawable.tio_dialog_positive_btn_bg_red);
        }
        tv_positiveBtn.setText(positiveBtnTxt);
        tv_positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBtnListener != null)
                    onBtnListener.onClickPositive(tv_positiveBtn, EasyOperDialog.this);
            }
        });

    }

    @Override
    protected void initNegativeBtn(final TextView tv_negativeBtn) {
        tv_negativeBtn.setText(negativeBtnTxt);
        tv_negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBtnListener != null)
                    onBtnListener.onClickNegative(tv_negativeBtn, EasyOperDialog.this);
            }
        });
    }

    @Override
    protected void initTitleView(TextView tv_title) {
        tv_title.setGravity(titleGravity);
        tv_title.setText(titleTxt);
    }

    public interface OnBtnListener {
        void onClickPositive(View view, EasyOperDialog dialog);

        void onClickNegative(View view, EasyOperDialog dialog);
    }

    public static class Builder {
        private final CharSequence titleTxt;
        private String positiveBtnTxt = "确定";
        private String negativeBtnTxt = "取消";
        private OnBtnListener onBtnListener;
        private boolean posBtnShowRedBg = false;
        private int titleGravity = Gravity.CENTER;


        public Builder(CharSequence titleTxt) {
            this.titleTxt = titleTxt;
        }

        public Builder setPositiveBtnTxt(String positiveBtnTxt) {
            this.positiveBtnTxt = positiveBtnTxt;
            return this;
        }

        public Builder setNegativeBtnTxt(String negativeBtnTxt) {
            this.negativeBtnTxt = negativeBtnTxt;
            return this;
        }

        public Builder setOnBtnListener(OnBtnListener onBtnListener) {
            this.onBtnListener = onBtnListener;
            return this;
        }


        public Builder setPosBtnRedRoundBg(){
            this.posBtnShowRedBg = true;
            return this;
        }

        public Builder setTitleGravity(int titleGravity) {
            this.titleGravity = titleGravity;
            return this;
        }

        public EasyOperDialog build() {
            return new EasyOperDialog(
                    titleTxt,
                    positiveBtnTxt,
                    negativeBtnTxt,
                    onBtnListener,
                    titleGravity, posBtnShowRedBg);
        }
    }
}
