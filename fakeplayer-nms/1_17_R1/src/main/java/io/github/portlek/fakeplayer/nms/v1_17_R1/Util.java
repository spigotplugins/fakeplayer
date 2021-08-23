package io.github.portlek.fakeplayer.nms.v1_17_R1;

import java.lang.reflect.Field;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutEntityTeleport;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.level.ChunkProviderServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.PlayerChunkMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.jetbrains.annotations.NotNull;

final class Util {

  private Util() {
  }

  static void initNetworkManager(@NotNull final NetworkManager network) {
    network.k = new EmptyChannel(null);
    network.l = new SocketAddress() {
      private static final long serialVersionUID = 8207338859896320185L;
    };
  }

  static void addEntityToWorld(@NotNull final EntityPlayer player) {
    int viewDistance = -1;
    PlayerChunkMap chunkMap = null;
    try {
      final Field viewDistanceField = PlayerChunkMap.class.getDeclaredField("J");
      final boolean access = viewDistanceField.isAccessible();
      viewDistanceField.setAccessible(true);
      try {
        chunkMap = ((ChunkProviderServer) player.t.getChunkProvider()).a;
        viewDistance = (int) viewDistanceField.get(chunkMap);
        viewDistanceField.set(chunkMap, -1);
      } catch (final Throwable e) {
        e.printStackTrace();
      }
      player.t.addEntity(player);
      try {
        if (chunkMap != null) {
          viewDistanceField.set(chunkMap, viewDistance);
        }
      } catch (final Throwable e) {
        e.printStackTrace();
      }
      viewDistanceField.setAccessible(access);
    } catch (final NoSuchFieldException e) {
      e.printStackTrace();
    }
  }

  static void addOrRemoveFromPlayerList(@NotNull final EntityPlayer player, final boolean remove) {
    if (player.t == null) {
      return;
    }
    if (remove) {
      player.t.getPlayers().remove(player);
      player.c.getPlayerList().j.remove(player);
    } else if (!player.t.getPlayers().contains(player)) {
      ((List) player.t.getPlayers()).add(player);
      player.c.getPlayerList().j.add(player);
    }
  }

  static void removeFromServerPlayerList(@NotNull final EntityPlayer player) {
    player.c.getPlayerList().j.remove(player);
  }

  static void removeFromWorld(@NotNull final EntityPlayer player) {
    ((WorldServer) player.t).a(player, Entity.RemovalReason.a);
  }

  static void sendPositionUpdate(@NotNull final EntityPlayer from) {
    Util.sendPacket(new PacketPlayOutEntityTeleport(from));
  }

  static void sendPacket(@NotNull final Packet<?>... packets) {
    Bukkit.getOnlinePlayers().stream()
      .map(player -> (CraftPlayer) player)
      .forEach(player ->
        Arrays.stream(packets).forEach(player.getHandle().b::sendPacket)
      );
  }

  static void sendTabListAdd(@NotNull final EntityPlayer player) {
    Util.sendPacket(
      new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, player)
    );
  }

  static void sendTabListRemove(@NotNull final EntityPlayer... players) {
    Util.sendPacket(
      new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, players)
    );
  }

  static void setHeadYaw(@NotNull final EntityPlayer player, final float yaw) {
    final float clamp = Util.clampYaw(yaw);
    player.aZ = clamp;
    player.ba = clamp;
  }

  static float clampYaw(final float yaw) {
    float clamp = yaw;
    while (clamp < -180.0F) {
      clamp += 360.0F;
    }
    while (clamp >= 180.0F) {
      clamp -= 360.0F;
    }
    return clamp;
  }
}
