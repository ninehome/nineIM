package com.tiocloud.chat.feature.session.p2p.customization;

import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.session.common.action.model.MapAction;
import com.tiocloud.chat.feature.session.common.action.model.TransAmountAction;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseAction;
import com.tiocloud.chat.feature.session.common.action.model.CallAction;
import com.tiocloud.chat.feature.session.common.action.model.FaceTimeAction;
import com.tiocloud.chat.feature.session.common.action.model.FileAction;
import com.tiocloud.chat.feature.session.common.action.model.GroupCardAction;
import com.tiocloud.chat.feature.session.common.action.model.ImageAction;
import com.tiocloud.chat.feature.session.common.action.model.P2PRedPaperAction;
import com.tiocloud.chat.feature.session.common.action.model.ShootAction;
import com.tiocloud.chat.feature.session.common.action.model.UserCardAction;

import java.util.ArrayList;

/**
 * author : TaoWang
 * date : 2020/3/19
 * desc :
 */
public class P2PActions extends ArrayList<BaseAction> {
    private P2PActions() {
    }

    public static ArrayList<BaseAction> get() {
        ArrayList<BaseAction> menus = new ArrayList<>();
        menus.add(new ImageAction());
        menus.add(new ShootAction());
        if (TioConfig.OpenCloseConfig.isVideoEnable()){
            menus.add(new FaceTimeAction());
        }
        if (TioConfig.OpenCloseConfig.isAudioEnable()){
            menus.add(new CallAction());
        }
        if (TioConfig.OpenCloseConfig.isRedpacketEnable()){
            menus.add(new P2PRedPaperAction());
        }
        if (TioConfig.OpenCloseConfig.transAmountEnable()){
            menus.add(new TransAmountAction());
        }
        menus.add(new UserCardAction());
        menus.add(new GroupCardAction());
        menus.add(new FileAction());
        if (TioConfig.OpenCloseConfig.isMapEnable()){
            menus.add(new MapAction());
        }
        return menus;
    }
}
