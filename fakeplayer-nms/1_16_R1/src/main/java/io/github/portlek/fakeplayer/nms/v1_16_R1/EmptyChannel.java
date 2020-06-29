package io.github.portlek.fakeplayer.nms.v1_16_R1;

import io.netty.channel.*;

import java.net.SocketAddress;

public final class EmptyChannel extends AbstractChannel {

    private final ChannelConfig config = new DefaultChannelConfig(this);

    public EmptyChannel(final Channel parent) {
        super(parent);
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
    protected AbstractUnsafe newUnsafe() {
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
