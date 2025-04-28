package com.watayouxiang.httpclient.model.response;

import java.io.Serializable;

public class ImServerResp implements Serializable {
    /**
     * ip
     */
    public String ip;
    /**
     * 端口号
     */
    public int port;
    /**
     * 是否开启SSL验证
     * <p>
     * 1 开启，2 关闭
     */
    public int ssl;

    @Override
    public String toString() {
        return "ImServerResp{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", ssl=" + ssl +
                '}';
    }
}
