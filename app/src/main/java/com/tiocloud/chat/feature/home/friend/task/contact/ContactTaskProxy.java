package com.tiocloud.chat.feature.home.friend.task.contact;

import com.tiocloud.chat.feature.home.friend.adapter.model.IData;
import com.tiocloud.chat.feature.home.friend.adapter.model.IItem;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-07
 * desc :
 */
public interface ContactTaskProxy {
    List<? extends IItem> onTaskPrepare();

    void onTaskUpdate(IData result, Throwable throwable);

    void onTaskDone(ContactTask task);
}
