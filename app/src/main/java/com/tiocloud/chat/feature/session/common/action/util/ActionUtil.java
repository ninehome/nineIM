package com.tiocloud.chat.feature.session.common.action.util;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tiocloud.chat.feature.session.common.SessionActivity;
import com.tiocloud.chat.feature.session.common.SessionFragment;
import com.tiocloud.chat.feature.session.common.action.model.MapAction;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseAction;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseCallAction;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseCardAction;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseRedPaperAction;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseUploadAction;
import com.tiocloud.chat.feature.session.p2p.P2PSessionActivity;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020/3/9
 * desc :
 */
public class ActionUtil {
    /**
     * 设置参数
     */
    public static void setParams(@Nullable List<BaseAction> actions, @NonNull SessionFragment fragment, @NonNull List<String> chatLinkIds) {
        if (actions == null) return;
        for (int i = 0; i < actions.size(); i++) {
            BaseAction action = actions.get(i);
            if (action instanceof BaseUploadAction) {
                BaseUploadAction uploadAction = (BaseUploadAction) action;
                uploadAction.fragment = fragment;
            } else if (action instanceof BaseCardAction) {
                BaseCardAction cardAction = (BaseCardAction) action;
                SessionActivity sessionActivity = fragment.getSessionActivity();
                cardAction.activity = sessionActivity;
                cardAction.sessionType = sessionActivity.getSessionType();
                cardAction.chatLinkIds = chatLinkIds;
            } else if (action instanceof BaseCallAction) {
                BaseCallAction baseCallAction = (BaseCallAction) action;
                if (fragment.getActivity() instanceof P2PSessionActivity) {
                    baseCallAction.setP2PActivity((P2PSessionActivity) fragment.getActivity());
                }
            } else if (action instanceof BaseRedPaperAction) {
                BaseRedPaperAction redPaperAction = (BaseRedPaperAction) action;
                redPaperAction.activity = fragment.getSessionActivity();
                redPaperAction.chatLinkIds = chatLinkIds;
            }
        }
    }

//    public static void setParams(@Nullable List<BaseAction> actions, SessionFragment fragment, Activity activity,  @NonNull String chatLinkId) {
//        if (actions == null) return;
//        for (int i = 0; i < actions.size(); i++) {
//            BaseAction action = actions.get(i);
//            if (action instanceof BaseUploadAction) {
//                BaseUploadAction uploadAction = (BaseUploadAction) action;
//                uploadAction.fragment = fragment;
//                uploadAction.activity = activity;
//            } else if (action instanceof BaseCardAction) {
//                BaseCardAction cardAction = (BaseCardAction) action;
//                SessionActivity sessionActivity = fragment.getSessionActivity();
//                cardAction.activity = sessionActivity;
//                cardAction.sessionType = sessionActivity.getSessionType();
//                cardAction.chatLinkId = chatLinkId;
//            } else if (action instanceof BaseCallAction) {
//                BaseCallAction baseCallAction = (BaseCallAction) action;
//                if (fragment.getActivity() instanceof P2PSessionActivity) {
//                    baseCallAction.setP2PActivity((P2PSessionActivity) fragment.getActivity());
//                }
//            } else if (action instanceof BaseRedPaperAction) {
//                BaseRedPaperAction redPaperAction = (BaseRedPaperAction) action;
//                redPaperAction.activity = fragment.getSessionActivity();
//                redPaperAction.chatLinkId = chatLinkId;
//            }
//        }
//    }

    /**
     * fragment & Activity 的回调
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data, List<BaseAction> actions) {
        if (actions == null) return;
        for (BaseAction action : actions) {
            if (action instanceof BaseCardAction) {
                BaseCardAction cardAction = (BaseCardAction) action;
                cardAction.onActivityResult(requestCode, resultCode, data);
            } else if (action instanceof BaseUploadAction) {
                BaseUploadAction uploadAction = (BaseUploadAction) action;
                uploadAction.onActivityResult(requestCode, resultCode, data);
            }else if (action instanceof MapAction){
                MapAction mapAction = (MapAction) action;

            }
        }
    }

    /**
     * fragment & Activity 的回调
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, List<BaseAction> actions) {
        if (actions == null) return;
        for (int i = 0; i < actions.size(); i++) {
            BaseAction action = actions.get(i);
            if (action instanceof BaseUploadAction) {
                BaseUploadAction uploadAction = (BaseUploadAction) action;
                uploadAction.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    /**
     * 释放资源
     */
    public static void release(List<BaseAction> actions) {
        if (actions == null) return;
        for (int i = 0; i < actions.size(); i++) {
            BaseAction action = actions.get(i);
            if (action instanceof BaseUploadAction) {
                BaseUploadAction uploadAction = (BaseUploadAction) action;
                uploadAction.release();
            }
        }
    }
}
