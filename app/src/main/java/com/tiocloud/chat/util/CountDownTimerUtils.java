package com.tiocloud.chat.util;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.tiocloud.chat.R;

public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;
    private Context context;

    public CountDownTimerUtils(Context context,TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
        this.context = context;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText("已发送("+millisUntilFinished / 1000 + "s)");  //设置倒计时时间
        mTextView.setTextColor(context.getResources().getColor(R.color.gray_9c9c9c)); //设置按钮为灰色，这时是不能点击的
//        SpannableString spannableString = new SpannableString(mTextView.getText().toString());  //获取按钮上的文字
//        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
//        spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
//        mTextView.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取验证码");
        mTextView.setClickable(true);//重新获得点击
        mTextView.setTextColor(context.getResources().getColor(R.color.color_06D89A));  //还原背景色
    }
}
