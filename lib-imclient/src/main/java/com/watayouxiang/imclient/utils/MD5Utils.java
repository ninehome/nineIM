package com.watayouxiang.imclient.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    /**
     * MD5加密
     *
     * @param string 字符串
     * @return 加密后的字符串
     */
    public static String getMd5(String string) {
        if (string == null) return null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) {
        }
        if (md == null) return null;
        md.update(string.getBytes());
        byte[] bytes = md.digest();
        StringBuilder buf = new StringBuilder();
        for (int aByte : bytes) {
            int i = aByte;
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        //32位加密
        return buf.toString();
    }
}
