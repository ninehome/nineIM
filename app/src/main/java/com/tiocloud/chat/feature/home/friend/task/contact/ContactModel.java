package com.tiocloud.chat.feature.home.friend.task.contact;

import com.tiocloud.chat.feature.home.friend.adapter.model.IContact;
import com.watayouxiang.httpclient.model.response.WxMyFriendsResp;

import java.util.Random;

/**
 * author : TaoWang
 * date : 2020-02-17
 * desc :
 */
public class ContactModel implements IContact {
    private String avatar;
    private String name;
    private final int uid;

    public ContactModel(WxMyFriendsResp.ListBean bean) {
        name = bean.nick;
        avatar = bean.avatar;
        uid = bean.uid;
    }

    private static String randomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 头像
     *
     * @return
     */
    @Override
    public String getAvatar() {
        return avatar;
    }

    /**
     * 名字
     *
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return String.valueOf(uid);
    }
}
