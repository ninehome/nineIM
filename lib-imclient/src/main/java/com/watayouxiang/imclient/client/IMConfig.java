package com.watayouxiang.imclient.client;

public class IMConfig {
    // 默认连接超时
    private static final int TIMEOUT_DEFAULT = 15 * 1000;

    // 最小重连间隔
    public static final long RECONNECT_INTERVAL_MIN = 1000;
    // 默认重连间隔
    private static final long RECONNECT_INTERVAL_DEFAULT = 3 * 1000;

    // 最小心跳间隔
    public static final long HEARTBEAT_INTERVAL_MIN = 1000;
    // 默认心跳间隔
    private static final long HEARTBEAT_INTERVAL_DEFAULT = 60 * 1000;

    // ip
    private String remoteIP;
    // 端口号
    private int remotePort;
    // 连接超时（单位毫秒）
    private int timeout;
    // 重连间隔（单位毫秒）
    private long reconnectInterval;
    // 心跳间隔（单位毫秒）
    private long heartBeatInterval;
    // 是否开启 SSL 验证
    private boolean openSsl;

    private IMConfig(String remoteIP,
                     int remotePort,
                     int connectionTimeout,
                     long reconnectInterval,
                     long heartBeatInterval,
                     boolean openSsl) {
        this.remoteIP = remoteIP;
        this.remotePort = remotePort;
        this.timeout = connectionTimeout;
        this.reconnectInterval = reconnectInterval;
        this.heartBeatInterval = heartBeatInterval;
        this.openSsl = openSsl;
    }

    public String getRemoteIP() {
        return remoteIP;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public int getTimeout() {
        return timeout;
    }

    public long getReconnectInterval() {
        return reconnectInterval;
    }

    public long getHeartBeatInterval() {
        return heartBeatInterval;
    }

    public boolean isOpenSsl() {
        return openSsl;
    }

    @Override
    public String toString() {
        return "IMConfig{" +
                "remoteIP='" + remoteIP + '\'' +
                ", remotePort=" + remotePort +
                ", timeout=" + timeout +
                ", reconnectInterval=" + reconnectInterval +
                ", heartBeatInterval=" + heartBeatInterval +
                ", openSsl=" + openSsl +
                '}';
    }

    public static class Builder {
        private String remoteIP;
        private int remotePort;
        private int timeout = TIMEOUT_DEFAULT;
        private long reconnectInterval = RECONNECT_INTERVAL_DEFAULT;
        private long heartBeatInterval = HEARTBEAT_INTERVAL_DEFAULT;
        private boolean openSsl = true;

        public Builder(String remoteIP, int remotePort) {
            this.remoteIP = remoteIP;
            this.remotePort = remotePort;
        }

        public Builder setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder setHeartBeatInterval(long heartBeatInterval) {
            this.heartBeatInterval = heartBeatInterval;
            return this;
        }

        public Builder setReconnectInterval(long reconnectInterval) {
            this.reconnectInterval = reconnectInterval;
            return this;
        }

        public Builder setOpenSsl(boolean openSsl) {
            this.openSsl = openSsl;
            return this;
        }

        public IMConfig build() {
            return new IMConfig(
                    remoteIP,
                    remotePort,
                    timeout,
                    reconnectInterval,
                    heartBeatInterval,
                    openSsl
            );
        }
    }
}
