package com.watayouxiang.imclient.packet;

import java.util.Map;

public interface TioPacketDecoderOperation {
    /**
     * 设置命令码、消息体映射表
     *
     * @param bodyMap 命令码、消息体映射表
     */
    void setCommandBodyMap(Map<Short, Class> bodyMap);
}
