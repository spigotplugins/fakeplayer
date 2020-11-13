package io.github.portlek.fakeplayer.nms.v1_16_R3;

import net.minecraft.server.v1_16_R3.EnumProtocolDirection;
import net.minecraft.server.v1_16_R3.NetworkManager;
import net.minecraft.server.v1_16_R3.Packet;

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
