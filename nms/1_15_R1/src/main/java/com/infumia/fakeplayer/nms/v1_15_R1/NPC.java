package com.infumia.fakeplayer.nms.v1_15_R1;

import com.infumia.fakeplayer.api.INPC;
import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.jetbrains.annotations.NotNull;

public final class NPC extends EntityPlayer implements INPC {

    public NPC(@NotNull final UUID uuid, @NotNull final String name, @NotNull final CraftWorld world) {
        super(
            ((CraftServer) Bukkit.getServer()).getServer(),
            world.getHandle(),
            new GameProfile(uuid, ChatColor.translateAlternateColorCodes('&', name)),
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
//        this.listName = CraftChatMessage.fromStringOrNull(ChatColor.translateAlternateColorCodes('&', name));
        this.playerInteractManager.setGameMode(EnumGamemode.CREATIVE);
        Util.addEntityToWorld(this);
        Optional.ofNullable(this.getBukkitEntity()).ifPresent(player -> {
            player.setSleepingIgnored(true);
        });
        Util.addOrRemoveFromPlayerList(this, false);
    }

    @Override
    public void spawn(@NotNull final Location location) {
        Util.addOrRemoveFromPlayerList(this, false);
        Util.sendTabListAdd(this);
        Util.sendPacket(
            new PacketPlayOutNamedEntitySpawn(this)
        );
        this.tp(location);
        Util.sendPositionUpdate(this);
    }

    @Override
    public void deSpawn() {
        Util.sendPacket(
            new PacketPlayOutEntityDestroy(this.getId()),
            new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this)
        );
        Util.removeFromWorld(this);
        Util.removeFromServerPlayerList(this);
    }

    @Override
    public void tp(@NotNull final Location location) {
        Optional.ofNullable(this.getBukkitEntity()).ifPresent(craftPlayer -> craftPlayer.teleport(location));
    }

    @Override
    public void update() {
        Util.sendPacket(new PacketPlayOutNamedEntitySpawn(this));
    }

    @Override
    public void die(final DamageSource damagesource) {
        if (!this.dead) {
            super.die(damagesource);
            ((WorldServer) this.world).removeEntity(this);
        }
    }

}
