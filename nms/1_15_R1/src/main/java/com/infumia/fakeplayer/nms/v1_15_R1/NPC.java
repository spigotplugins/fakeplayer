package com.infumia.fakeplayer.nms.v1_15_R1;

import com.infumia.fakeplayer.api.INPC;
import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.jetbrains.annotations.NotNull;

public final class NPC extends EntityPlayer implements INPC {

    public NPC(@NotNull final UUID uuid, @NotNull final String name, @NotNull final CraftWorld world,
               @NotNull final Location location) {
        super(
            ((CraftServer) Bukkit.getServer()).getServer(),
            world.getHandle(),
            new GameProfile(uuid, name),
            new PlayerInteractManager(world.getHandle())
        );
        this.playerInteractManager.b(EnumGamemode.CREATIVE);
        try (final Socket ignored = new EmptySocket()) {
            final NetworkManager conn = new EmptyNetworkManager(EnumProtocolDirection.CLIENTBOUND);
            this.playerConnection = new EmptyNetHandler(this.server, conn, this);
            conn.setPacketListener(this.playerConnection);
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
        this.world.addEntity(this);
        Optional.ofNullable(this.getBukkitEntity()).ifPresent(player -> {
            player.teleport(location);
            player.setRemoveWhenFarAway(false);
        });
    }

    @Override
    public void spawn(@NotNull final Location location) {
        NPCProtocol.sendPacket(
            new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this),
            new PacketPlayOutNamedEntitySpawn(this)
        );
        this.server.getPlayerList().players.add(this);
        this.world.getWorld().getHandle().addPlayerJoin(this);
        this.tp(new Location(this.world.getWorld(), this.locX(), this.locY(), this.locZ(), this.yaw, this.pitch));
    }

    @Override
    public void deSpawn() {
        NPCProtocol.sendPacket(
            new PacketPlayOutEntityDestroy(this.getId()),
            new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this)
        );
        this.server.getPlayerList().players.remove(this);
        this.world.getWorld().getHandle().removePlayer(this);
    }

    @Override
    public void tp(@NotNull final Location location) {
        this.setPositionRotation(
            location.getX(),
            location.getY(),
            location.getZ(),
            location.getYaw(),
            location.getPitch()
        );
        this.update();
    }

    @Override
    public void update() {
        NPCProtocol.sendPacket(new PacketPlayOutNamedEntitySpawn(this));
    }

}
