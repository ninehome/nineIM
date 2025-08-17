package com.tiocloud.chat.feature.home.user.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;

/**
 * author : TaoWang
 * date : 2020/4/14
 * desc :
 */
public class UserViewHolder {

    public final View rootView;

    public TioImageView hiv_avatar;
    public TextView tv_name;
    public TextView tv_email;
    public RelativeLayout rl_modifyInfo;
    public RelativeLayout rl_settings;

    public UserViewHolder(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.tio_user_fragment, container, false);

        hiv_avatar = rootView.findViewById(R.id.hiv_avatar);
        tv_name = rootView.findViewById(R.id.tv_name);
        tv_email = rootView.findViewById(R.id.tv_email);
        rl_modifyInfo = rootView.findViewById(R.id.rl_modifyInfo);
        rl_settings = rootView.findViewById(R.id.rl_settings);
    }
}
