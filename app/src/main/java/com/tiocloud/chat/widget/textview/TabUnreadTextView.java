package com.tiocloud.chat.widget.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.SizeUtils;
import com.tiocloud.chat.R;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/14
 *     desc   :
 * </pre>
 */
public class TabUnreadTextView extends AppCompatTextView {
    public TabUnreadTextView(Context context) {
        this(context, null);
    }

    public TabUnreadTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabUnreadTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackground(getResources().getDrawable(R.drawable.bg_unread_msg));
        setGravity(Gravity.CENTER);
        setIncludeFontPadding(false);
        setPadding(SizeUtils.dp2px(4), SizeUtils.dp2px(0), SizeUtils.dp2px(4), SizeUtils.dp2px(0));
        setSingleLine(true);
        setTextColor(getResources().getColor(R.color.white));
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        setMinWidth(SizeUtils.dp2px(16));
        setMinHeight(SizeUtils.dp2px(16));
    }

    public void setUnread(int unread) {
        if (unread > 0) {
            setVisibility(View.VISIBLE);
            if (unread >= 100) {
                setText("⋯");
            } else {
                setText(String.valueOf(unread));
            }
        } else {
            setVisibility(View.GONE);
        }
    }
}
