package io.github.portlek.fakeplayer.nms.v1_17_R1;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.Packet;

final class EmptyNetworkManager extends NetworkManager {

  EmptyNetworkManager(final EnumProtocolDirection enumprotocoldirection) {
    super(enumprotocoldirection);
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
