package io.github.portlek.fakeplayer.nms.v1_8_R3;

import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;

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