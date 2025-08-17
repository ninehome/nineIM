package com.watayouxiang.imclient.event;

import androidx.annotation.NonNull;

/**
 * author : TaoWang
 * date : 2020/3/24
 * desc : 长连接状态
 */
public class TioStateEvent {

    @NonNull
    public final TioState state;

    public TioStateEvent(@NonNull TioState state) {
        this.state = state;
    }
}
