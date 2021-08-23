package io.github.portlek.fakeplayer.nms.v1_17_R1;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;

final class EmptyNetHandler extends PlayerConnection {

  EmptyNetHandler(final MinecraftServer minecraftServer, final NetworkManager networkManager,
                  final EntityPlayer entityPlayer) {
    super(minecraftServer, networkManager, entityPlayer);
  }

  @Override
  public void sendPacket(final Packet packet) {
  }
}
