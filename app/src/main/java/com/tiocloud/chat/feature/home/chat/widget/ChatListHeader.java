package com.tiocloud.chat.feature.home.chat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.tiocloud.chat.R;

/**
 * author : TaoWang
 * date : 2020/3/20
 * desc :
 */
public class ChatListHeader extends RelativeLayout {
    public ChatListHeader(Context context) {
        this(context, null);
    }

    public ChatListHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatListHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.tio_home_chat_header, this, true);
    }
}
