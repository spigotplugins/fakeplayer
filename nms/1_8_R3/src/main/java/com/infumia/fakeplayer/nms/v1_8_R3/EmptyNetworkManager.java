package com.infumia.fakeplayer.nms.v1_8_R3;

import java.net.SocketAddress;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;

class EmptyNetworkManager extends NetworkManager {

    EmptyNetworkManager(final EnumProtocolDirection flag) {
        super(flag);
        try {
            this.getClass().getField("  i").set(this, new EmptyChannel(null));
        } catch (final Exception e1) {
            e1.printStackTrace();
        }
        this.l = new SocketAddress() {
            private static final long serialVersionUID = 8207338859896320185L;
        };
    }

    @Override
    public void handle(final Packet packet) {
    }

    @Override
    public boolean g() {
        return true;
    }

}