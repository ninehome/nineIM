package com.watayouxiang.imclient.packet;

public class TioPacket implements Packet {
    static final String CHARSET = "utf-8";
    static final int HEADER_LENGTH = 5;
    static final short GZIP_LENGTH = 500;

    /**
     * 消息命令码
     */
    private short command;
    /**
     * 消息体
     */
    private Object body;

    public short getCommand() {
        return command;
    }

    public void setCommand(short command) {
        this.command = command;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    // =====================================================================================
    // 以下内部使用（编解码后才有值）
    // =====================================================================================

    /**
     * 消息体json字符串
     */
    private String bodyJson;
    /**
     * 消息体长度
     */
    private short bodyLength;
    /**
     * 是否gzip压缩过消息体
     * 1：压缩过，0：没压缩
     */
    private byte gzip;

    void setBodyJson(String bodyJson) {
        this.bodyJson = bodyJson;
    }

    void setBodyLength(short bodyLength) {
        this.bodyLength = bodyLength;
    }

    void setGzip(byte gzip) {
        this.gzip = gzip;
    }

    @Override
    public String getJson() {
        return "{" +
                "\"command\":" + command +
                ",\"bodyLength\":" + bodyLength +
                ",\"gzip\":" + gzip +
                ",\"body\":" + bodyJson +
                '}';
    }
}
