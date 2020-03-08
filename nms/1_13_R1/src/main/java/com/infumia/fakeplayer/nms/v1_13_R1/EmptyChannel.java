package com.infumia.fakeplayer.nms.v1_13_R1;

import io.netty.channel.*;
import java.net.SocketAddress;

public class EmptyChannel extends AbstractChannel {

    private final ChannelConfig config;

    EmptyChannel(final Channel parent) {
        super(parent);
        this.config = new DefaultChannelConfig(this);
    }

    @Override
    public ChannelConfig config() {
        this.config.setAutoRead(true);
        return this.config;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public ChannelMetadata metadata() {
        return new ChannelMetadata(true);
    }

    @Override
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return null;
    }

    @Override
    protected boolean isCompatible(final EventLoop arg0) {
        return true;
    }

    @Override
    protected SocketAddress localAddress0() {
        return null;
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return null;
    }

    @Override
    protected void doBind(final SocketAddress arg0) {
    }

    @Override
    protected void doDisconnect() {
    }

    @Override
    protected void doClose() {
    }

    @Override
    protected void doBeginRead() {
    }

    @Override
    protected void doWrite(final ChannelOutboundBuffer arg0) {
    }

}