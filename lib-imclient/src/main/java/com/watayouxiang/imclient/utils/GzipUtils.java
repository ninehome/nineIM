package com.watayouxiang.imclient.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtils {
    /**
     * 字节数组压缩
     *
     * @param bytes 字节数组
     * @return 压缩后的字节数组
     * @throws IOException IO异常
     */
    public static byte[] compress(byte[] bytes) throws IOException {
        if (null == bytes || bytes.length <= 0) {
            return bytes;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(bytes);
        gzip.close();
        return out.toByteArray();
    }

    /**
     * 字节数组解压
     *
     * @param bytes 字节数组
     * @return 压缩后的字节数组
     * @throws IOException IO异常
     */
    public static byte[] unCompress(byte[] bytes) throws IOException {
        if (null == bytes || bytes.length <= 0) {
            return bytes;
        }
        byte[] unCompressBytes;
        ByteArrayOutputStream out = null;
        GZIPInputStream gzip = null;
        try {
            out = new ByteArrayOutputStream();
            gzip = new GZIPInputStream(new ByteArrayInputStream(bytes));
            byte[] buffer = new byte[256];
            int n;
            while ((n = gzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            unCompressBytes = out.toByteArray();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignored) {
                }
            }
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException ignored) {
                }
            }
        }
        return unCompressBytes;
    }
}
