package com.infumia.fakeplayer.nms.v1_9_R2;

import java.net.SocketAddress;
import java.util.Arrays;
import net.minecraft.server.v1_9_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.jetbrains.annotations.NotNull;

final class Util {

    private Util() {
    }

    static void initNetworkManager(@NotNull final NetworkManager network) {
        network.channel = new EmptyChannel(null);
        network.l = new SocketAddress() {
            private static final long serialVersionUID = 8207338859896320185L;
        };
    }

    static void addEntityToWorld(@NotNull final EntityPlayer player) {
        player.world.addEntity(player, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    static void addOrRemoveFromPlayerList(@NotNull final EntityPlayer player, final boolean remove) {
        if (player.world == null) {
            return;
        }
        if (remove) {
            player.world.players.remove(player);
            player.server.getPlayerList().players.add(player);
        } else if (!player.world.players.contains(player)) {
            player.world.players.add(player);
            player.server.getPlayerList().players.add(player);
        }
    }

    static void removeFromServerPlayerList(@NotNull final EntityPlayer player) {
        player.server.getPlayerList().players.remove(player);
    }

    static void removeFromWorld(@NotNull final EntityPlayer player) {
        player.world.removeEntity(player);
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
        player.aP = clamp;
        player.aO = clamp;
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
