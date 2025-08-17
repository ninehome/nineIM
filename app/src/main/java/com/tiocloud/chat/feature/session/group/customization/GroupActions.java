package com.tiocloud.chat.feature.session.group.customization;

import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.session.common.action.model.FileAction;
import com.tiocloud.chat.feature.session.common.action.model.GroupCardAction;
import com.tiocloud.chat.feature.session.common.action.model.GroupRedPaperAction;
import com.tiocloud.chat.feature.session.common.action.model.ImageAction;
import com.tiocloud.chat.feature.session.common.action.model.MapAction;
import com.tiocloud.chat.feature.session.common.action.model.ShootAction;
import com.tiocloud.chat.feature.session.common.action.model.UserCardAction;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseAction;

import java.util.ArrayList;

/**
 * author : TaoWang
 * date : 2020/3/19
 * desc :
 */
public class GroupActions extends ArrayList<BaseAction> {
    private GroupActions() {
    }

    public static ArrayList<BaseAction> get() {
        ArrayList<BaseAction> menus = new ArrayList<>();
        menus.add(new ImageAction());
        menus.add(new ShootAction());
//        menus.add(new FaceTimeAction());
//        menus.add(new CallAction());
        if (TioConfig.OpenCloseConfig.isRedpacketEnable()){
            menus.add(new GroupRedPaperAction());
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
