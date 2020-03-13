package com.infumia.fakeplayer.nms.v1_12_R1;

import java.net.SocketAddress;
import net.minecraft.server.v1_12_R1.EnumProtocolDirection;
import net.minecraft.server.v1_12_R1.NetworkManager;
import net.minecraft.server.v1_12_R1.Packet;

class EmptyNetworkManager extends NetworkManager {

    EmptyNetworkManager(final EnumProtocolDirection flag) {
        super(flag);
        try {
            this.channel = new EmptyChannel(null);
        } catch (final Exception e1) {
            e1.printStackTrace();
        }
        this.l = new SocketAddress() {
            private static final long serialVersionUID = 8207338859896320185L;
        };
    }

    @Override
    public void sendPacket(final Packet<?> packet) {
    }

}