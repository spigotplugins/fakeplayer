package com.infumia.fakeplayer.nms.v1_15_R1;

import java.lang.reflect.Field;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.jetbrains.annotations.NotNull;

final class Util {

    private Util() {
    }

    static void initNetworkManager(@NotNull final NetworkManager network) {
        network.channel = new EmptyChannel(null);
        network.socketAddress = new SocketAddress() {
            private static final long serialVersionUID = 8207338859896320185L;
        };
    }

    static void addEntityToWorld(@NotNull final EntityPlayer player) {
        int viewDistance = -1;
        PlayerChunkMap chunkMap = null;
        try {
            final Field viewDistanceField = PlayerChunkMap.class.getDeclaredField("viewDistance");
            final boolean access = viewDistanceField.isAccessible();
            viewDistanceField.setAccessible(true);
            try {
                chunkMap = ((ChunkProviderServer) player.world.getChunkProvider()).playerChunkMap;
                viewDistance = (int) viewDistanceField.get(chunkMap);
                viewDistanceField.set(chunkMap, -1);
            } catch (final Throwable e) {
                e.printStackTrace();
            }
            player.world.addEntity(player);
            try {
                if (chunkMap != null) {
                    viewDistanceField.set(chunkMap, viewDistance);
                }
            } catch (final Throwable e) {
                e.printStackTrace();
            }
            viewDistanceField.setAccessible(access);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    static void addOrRemoveFromPlayerList(@NotNull final EntityPlayer player, final boolean remove) {
        if (player.world == null) {
            return;
        }
        if (remove) {
            player.world.getPlayers().remove(player);
        } else if (!player.world.getPlayers().contains(player)) {
            ((List) player.world.getPlayers()).add(player);
        }
    }

    static void removeFromServerPlayerList(@NotNull final EntityPlayer player) {
        ((CraftServer) Bukkit.getServer()).getHandle().players.remove(player);
    }

    static void removeFromWorld(@NotNull final EntityPlayer player) {
        ((WorldServer) player.world).removePlayer(player);
    }

    static void sendPositionUpdate(@NotNull final EntityPlayer from) {
        Util.sendPacket(new PacketPlayOutEntityTeleport(from));
    }

    static void sendPacket(@NotNull final Packet<?>... packets) {
        Bukkit.getOnlinePlayers().stream()
            .map(player -> (CraftPlayer) player)
            .forEach(player ->
                Arrays.stream(packets).forEach(player.getHandle().playerConnection::sendPacket)
            );
    }

    static void sendTabListAdd(@NotNull final EntityPlayer player) {
        Util.sendPacket(
            new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, player)
        );
    }

    static void sendTabListRemove(@NotNull final EntityPlayer... players) {
        Util.sendPacket(
            new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, players)
        );
    }

    static void setHeadYaw(@NotNull final EntityPlayer player, final float yaw) {
        final float clamp = Util.clampYaw(yaw);
        player.aJ = clamp;
        player.aK = clamp;
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
