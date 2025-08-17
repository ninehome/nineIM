package com.tiocloud.chat.feature.curr.modify.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.widget.WtTitleBar;

/**
 * author : TaoWang
 * date : 2020/4/14
 * desc :
 */
public class ModifyViewHolder {
    public final View rootView;

    public final TextView tv_wordCount;
    public final EditText et_content;
    public final WtTitleBar titleBar;
    public final TextView tv_menuBtn;
    public final View tipView;
    public final TextView tipTv;

    public ModifyViewHolder(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.tio_modify_activity, null);
        titleBar = rootView.findViewById(R.id.titleBar);
        tv_menuBtn = titleBar.getTvRight();
        tv_wordCount = rootView.findViewById(R.id.tv_wordCount);
        et_content = rootView.findViewById(R.id.et_content);
        tipView = rootView.findViewById(R.id.tip_view);
        tipTv = rootView.findViewById(R.id.tip_tv);
    }
}
