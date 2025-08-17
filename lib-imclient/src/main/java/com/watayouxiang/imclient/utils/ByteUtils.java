package com.watayouxiang.imclient.utils;

import java.io.UnsupportedEncodingException;

public class ByteUtils {
    /**
     * 字符床转字节数组
     *
     * @param string      字符串
     * @param charsetName 编码类型
     * @return 字节数组
     * @throws UnsupportedEncodingException 不支持的编码异常
     */
    public static byte[] string2Bytes(String string, String charsetName) throws UnsupportedEncodingException {
        if (string != null && charsetName != null) {
            return string.getBytes(charsetName);
        }
        return null;
    }

    /**
     * 字节数组转字符串
     *
     * @param bytes       字节数组
     * @param charsetName 编码类型
     * @return 字符串
     * @throws UnsupportedEncodingException 不支持的编码异常
     */
    public static String bytes2String(byte[] bytes, String charsetName) throws UnsupportedEncodingException {
        if (bytes != null && charsetName != null) {
            return new String(bytes, charsetName);
        }
        return null;
    }
}