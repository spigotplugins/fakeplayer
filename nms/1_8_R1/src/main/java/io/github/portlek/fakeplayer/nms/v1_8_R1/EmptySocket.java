package io.github.portlek.fakeplayer.nms.v1_8_R1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class EmptySocket extends Socket {

    private static final byte[] EMPTY;

    static {
        EMPTY = new byte[50];
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(EmptySocket.EMPTY);
    }

    @Override
    public OutputStream getOutputStream() {
        return new ByteArrayOutputStream(10);
    }

}