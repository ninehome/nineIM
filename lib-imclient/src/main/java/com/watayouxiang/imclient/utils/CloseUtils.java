package com.watayouxiang.imclient.utils;

import java.net.Socket;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/10
 *     desc   :
 * </pre>
 */
public class CloseUtils {
    /**
     * 关闭Socket
     *
     * @param socket SSLSocket
     */
    public static void closeSocket(Socket socket) {
        try {
            socket.shutdownInput();
        } catch (Exception ignored) {
        }
        try {
            socket.shutdownOutput();
        } catch (Exception ignored) {
        }
        try {
            socket.getInputStream().close();
        } catch (Exception ignored) {
        }
        try {
            socket.getOutputStream().close();
        } catch (Exception ignored) {
        }
        try {
            socket.close();
        } catch (Exception ignored) {
        }
    }
}
