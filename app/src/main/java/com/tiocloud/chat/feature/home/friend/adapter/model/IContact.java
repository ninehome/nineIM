package com.tiocloud.chat.feature.home.friend.adapter.model;

/**
 * author : TaoWang
 * date : 2020-01-17
 * desc : 联系人
 */
public interface IContact {
    /**
     * 头像
     *
     * @return
     */
    String getAvatar();

    /**
     * 名字
     *
     * @return
     */
    String getName();

    /**
     * id
     *
     * @return
     */
    String getId();
}