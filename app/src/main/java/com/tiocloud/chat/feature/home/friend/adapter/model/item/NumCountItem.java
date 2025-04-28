package com.tiocloud.chat.feature.home.friend.adapter.model.item;

import androidx.annotation.NonNull;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.home.friend.adapter.model.IGroup;
import com.tiocloud.chat.feature.home.friend.adapter.model.IItem;
import com.watayouxiang.imclient.utils.ConstantUtils;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/10
 *     desc   :个数统计 数据模型
 * </pre>
 */
public class NumCountItem extends IItem {
    private int numCount;

    public NumCountItem(int numCount) {
        this.numCount = numCount;
    }

    public @NonNull
    CharSequence getNumCountDesc() {
        return String.format(Locale.getDefault(), "%d"+ ConstantUtils.context.getString(R.string.weilianxiren), numCount);
    }

    @Override
    public int getType() {
        return Type.NUM_COUNT;
    }

    @Override
    public String groupId() {
        return IGroup.ID_HEADER;
    }
}
