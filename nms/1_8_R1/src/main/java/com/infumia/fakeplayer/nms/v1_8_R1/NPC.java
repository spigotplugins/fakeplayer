package com.infumia.fakeplayer.nms.v1_8_R1;

import com.infumia.fakeplayer.api.INPC;
import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class NPC extends EntityPlayer implements INPC {

    public NPC(@NotNull final UUID uuid, @NotNull final String name, @NotNull final CraftWorld world) {
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
            conn.a(this.playerConnection);
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void spawn(@NotNull final Location location) {
        this.world.addEntity(this);
        final Player player = this.getBukkitEntity();
        assert player != null;
        player.teleport(location);
        player.setRemoveWhenFarAway(false);
        this.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

}
