package com.tiocloud.chat.feature.user.detail.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/07
 *     desc   :
 * </pre>
 */
public class NonFriendApply implements Serializable {
    public @Nullable
    String appendMsg;// 附加消息
    public @NonNull
    String applyId;// 申请id
    public @Nullable
    String remarkName;// 备注名

    public NonFriendApply(@Nullable String appendMsg, @NonNull String applyId, @Nullable String remarkName) {
        this.appendMsg = appendMsg;
        this.applyId = applyId;
        this.remarkName = remarkName;
    }
}
