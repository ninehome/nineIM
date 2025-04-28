package com.watayouxiang.imclient.exception;

import java.io.IOException;

public class ServerSocketCloseException extends IOException {
    public ServerSocketCloseException(String message) {
        super(message);
    }
}
