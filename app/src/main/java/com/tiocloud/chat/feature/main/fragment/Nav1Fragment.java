package com.tiocloud.chat.feature.main.fragment;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.main.base.MainTabFragment;
import com.tiocloud.chat.feature.session.group.GroupSessionActivity;
import com.tiocloud.chat.feature.user.detail.UserDetailActivity;
import com.tiocloud.chat.widget.titlebar.HomeTitleBar;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.oper.EasyOperDialog;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallbackImpl;
import com.watayouxiang.httpclient.model.request.CheckCardJoinGroupReq;
import com.watayouxiang.httpclient.model.request.JoinGroupReq;
import com.watayouxiang.httpclient.model.response.ConfigResp;

import java.util.ArrayList;
import java.util.List;


/**
 * 导航1
 */
public class Nav1Fragment extends MainTabFragment implements View.OnClickListener {
    private HomeTitleBar homeTitleBar;
    private LinearLayout webContentLine;

    private List<Fragment> fragments;

    public static List<ConfigResp.Website> findItems;
    public Nav1Fragment () {
    }

    private void initView() {
        setStatusBarCustom(findViewById(R.id.fl_statusBar));
        homeTitleBar = findViewById(R.id.homeTitleBar);
        homeTitleBar.setTitle(getString(R.string.my_find));
        homeTitleBar.hideRight();
        if (TioConfig.OpenCloseConfig.showFriendCircle()){
            findViewById(R.id.friend_circle).setVisibility(View.VISIBLE);
            findViewById(R.id.friend_circle).setOnClickListener(v -> {
//                ToastUtils.showShort("朋友圈功能尚在开发");
            });
        }else {
            findViewById(R.id.friend_circle).setVisibility(View.GONE);
        }

        webContentLine = findViewById(R.id.web_content_line);
        if (findItems == null || findItems.size() < 1){
            webContentLine.setVisibility(View.GONE);
            return;
        }
        fragments = new ArrayList<>();
        for (int i = 0; i < findItems.size(); i++){
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_find_web, null);
            TioImageView icoView = itemView.findViewById(R.id.image_view);
            TextView titleView = itemView.findViewById(R.id.title);
            icoView.load(findItems.get(i).siteicon);
            titleView.setText(findItems.get(i).sitename);
//            itemView.setTag(findItems.get(i));
            itemView.setTag(i);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConfigResp.Website website = findItems.get((Integer) itemView.getTag());
                    if (TioConfig.OpenCloseConfig.isUliao() && website.siteurl.startsWith("uliao://")){
                        toDecode(getActivity(), website.siteurl.substring(8));
                    }else {
                        changeFragment((Integer) itemView.getTag());
                    }
                }
            });
            //设置水波纹背景
            if (i == 0 && i == findItems.size() - 1){
                itemView.setBackgroundResource(R.drawable.ripple_corner_all);
            }else if (i == 0){
                itemView.setBackgroundResource(R.drawable.ripple_corner_top);
            }else if (i == findItems.size() - 1){
                itemView.setBackgroundResource(R.drawable.ripple_corner_bottom);
            }else {
                itemView.setBackgroundResource(R.drawable.ripple_corner_non);
            }

            webContentLine.addView(itemView);
            if (i != findItems.size() - 1){
                View dividerView = LayoutInflater.from(getContext()).inflate(R.layout.view_divider, null);
                webContentLine.addView(dividerView);
            }

            WebItemFragment itemFragment = new WebItemFragment(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findViewById(R.id.v1).setVisibility(View.VISIBLE);
                    findViewById(R.id.main_content).setVisibility(View.GONE);
                }
            });
            itemFragment.homeUrl = findItems.get(i).siteurl;
            itemFragment.title = findItems.get(i).sitename;
            fragments.add(itemFragment);
        }
    }

    private void changeFragment(int position) {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        Fragment fragment = fragments.get(position);
        transaction.replace(R.id.main_content, fragment);
        transaction.commit();

        findViewById(R.id.v1).setVisibility(View.GONE);
        findViewById(R.id.main_content).setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageShow(int count, boolean isInit) {
        super.onPageShow(count, isInit);
        setStatusBarLightMode(true);
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onInit() {
        if (TioConfig.OpenCloseConfig.showWebViewIfOneSite() && findItems != null && findItems.size() < 2){
            WebItemFragment itemFragment = new WebItemFragment(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findViewById(R.id.v1).setVisibility(View.VISIBLE);
                    findViewById(R.id.main_content).setVisibility(View.GONE);
                }
            });
            itemFragment.homeUrl = findItems.get(0).siteurl;
            itemFragment.title = findItems.get(0).sitename;
            itemFragment.showHView = true;
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.main_content, itemFragment);
            transaction.commit();

            findViewById(R.id.v1).setVisibility(View.GONE);
            findViewById(R.id.main_content).setVisibility(View.VISIBLE);
        }else {
            initView();
        }
    }

    private static void enterGroup(Context context, String groupId, String shareFromUid){
        new EasyOperDialog.Builder(context.getString(R.string.shifou_jeihsou_add_groupchat))
                .setPositiveBtnTxt(context.getString(R.string.add_groupchat))
                .setNegativeBtnTxt(context.getString(R.string.cancel))
                .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                    @Override
                    public void onClickPositive(View view, EasyOperDialog dialog) {
                        reqJoinGroup(context, groupId, shareFromUid, dialog);
                    }

                    @Override
                    public void onClickNegative(View view, EasyOperDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show_unCancel(context);
//        new CheckCardJoinGroupReq(groupId, shareFromUid).setCancelTag(context).get(new TioCallbackImpl<Integer>() {
//            @Override
//            public void onTioSuccess(Integer integer) {
//                if (integer == 1) {
//                    // 已经进群
//                    GroupSessionActivity.active(context, groupId);
//                } else if (integer == 2) {
//                    // 未进群
////                    showJoinGroupConfirmDialog(context, groupId, shareFromUid);
//
//
//
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onTioError(String msg) {
//                TioToast.showShort(msg);
//            }
//        });
    }

    private static void reqJoinGroup(Context context, String groupId, String shareFromUid, EasyOperDialog dialog) {
        long currUid = TioDBPreferences.getCurrUid();
        if (currUid == -1) {
            TioToast.showShort("currUid is empty");
            return;
        }
        // public JoinGroupReq(String uids, String groupid, String applyuid) {
        new JoinGroupReq(String.valueOf(currUid), groupId, shareFromUid).setCancelTag(context).post(new TioCallbackImpl<String>() {
            @Override
            public void onTioSuccess(String s) {
                TioToast.showShort(s);
                dialog.dismiss();
                if (s != null && s.contains("等待审核")){
                    return;
                }
                GroupSessionActivity.active(context, groupId);
            }

            @Override
            public void onTioError(String msg) {
                super.onTioError(msg);
                TioToast.showShort(msg);
            }
        });
    }

    public static void toDecode(Context context, String uri) {
        String[] array = uri.split("\\.");
        if (array.length < 2){
            return;
        }
        if (array[1].equals("group")){
            if (array.length < 3){
                return;
            }
            enterGroup(context, array[0], array[2]);
        }else if (array[1].equals("uid")){
            UserDetailActivity.start(context, array[0]);
        }
    }
}
