package io.github.portlek.fakeplayer.nms.v1_9_R2;

import net.minecraft.server.v1_9_R2.EnumProtocolDirection;
import net.minecraft.server.v1_9_R2.NetworkManager;
import net.minecraft.server.v1_9_R2.Packet;

class EmptyNetworkManager extends NetworkManager {

  EmptyNetworkManager(final EnumProtocolDirection flag) {
    super(flag);
    Util.initNetworkManager(this);
  }

  @Override
  public void sendPacket(final Packet<?> packet) {
  }
}