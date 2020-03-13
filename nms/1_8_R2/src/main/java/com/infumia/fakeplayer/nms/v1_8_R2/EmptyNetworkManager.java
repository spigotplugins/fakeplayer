package com.infumia.fakeplayer.nms.v1_8_R2;

import net.minecraft.server.v1_8_R2.EnumProtocolDirection;
import net.minecraft.server.v1_8_R2.NetworkManager;
import net.minecraft.server.v1_8_R2.Packet;

class EmptyNetworkManager extends NetworkManager {

    EmptyNetworkManager(final EnumProtocolDirection flag) {
        super(flag);
        Util.initNetworkManager(this);
    }

    @Override
    public void handle(final Packet packet) {
    }

    @Override
    public boolean g() {
        return true;
    }

}