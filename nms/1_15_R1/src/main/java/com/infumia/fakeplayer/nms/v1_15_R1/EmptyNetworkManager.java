package com.infumia.fakeplayer.nms.v1_15_R1;

import net.minecraft.server.v1_15_R1.EnumProtocolDirection;
import net.minecraft.server.v1_15_R1.NetworkManager;
import net.minecraft.server.v1_15_R1.Packet;

final class EmptyNetworkManager extends NetworkManager {

    EmptyNetworkManager(final EnumProtocolDirection flag) {
        super(flag);
        Util.initNetworkManager(this);
    }

    @Override
    public void sendPacket(final Packet<?> packet) {
    }

    @Override
    public boolean isConnected() {
        return true;
    }

}