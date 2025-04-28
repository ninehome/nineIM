package com.watayouxiang.httpclient.model;

import java.io.Serializable;

public class BaseResp<T> implements Serializable, TioRespCode {
    /**
     * 具体数据
     */
    private T data;
    /**
     * 响应码
     */
    private int code;
    /**
     * 是否成功
     */
    private boolean ok;
    /**
     * 提示消息
     */
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "TioResp{" +
                "data=" + data +
                ", code=" + code +
                ", ok=" + ok +
                ", msg='" + msg + '\'' +
                '}';
    }
}
