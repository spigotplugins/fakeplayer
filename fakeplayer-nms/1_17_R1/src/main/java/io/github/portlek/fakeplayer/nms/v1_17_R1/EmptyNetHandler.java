package io.github.portlek.fakeplayer.nms.v1_17_R1;

import net.minecraft.server.v1_17_R1.*;

final class EmptyNetHandler extends PlayerConnection {

  EmptyNetHandler(final MinecraftServer minecraftServer, final NetworkManager networkManager,
                  final EntityPlayer entityPlayer) {
    super(minecraftServer, networkManager, entityPlayer);
  }

  @Override
  public void sendPacket(final Packet packet) {
  }
}