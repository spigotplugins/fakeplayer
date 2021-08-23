package io.github.portlek.fakeplayer.nms.v1_17_R1;

import com.mojang.authlib.GameProfile;
import io.github.portlek.fakeplayer.api.INPC;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.PlayerInteractManager;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.EnumGamemode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class NPC extends EntityPlayer implements INPC {

  private boolean visible = true;

  public NPC(@NotNull final UUID uuid, @NotNull final String name, @NotNull final String tabname,
             @NotNull final CraftWorld world) {
    super(
      ((CraftServer) Bukkit.getServer()).getServer(),
      world.getHandle(),
      new GameProfile(uuid, ChatColor.translateAlternateColorCodes('&', name))
    );
    this.d.setGameMode(EnumGamemode.b);
    try (final Socket ignored = new EmptySocket()) {
      final NetworkManager conn = new EmptyNetworkManager(EnumProtocolDirection.b);
      this.b = new EmptyNetHandler(this.c, conn, this);
      conn.setPacketListener(this.b);
    } catch (final IOException exception) {
      exception.printStackTrace();
    }
    Util.addEntityToWorld(this);
    final Player player = this.getBukkitEntity();
    player.setGameMode(GameMode.CREATIVE);
    player.setPlayerListName(tabname);
    player.setSleepingIgnored(true);
    Util.addOrRemoveFromPlayerList(this, false);
  }

  @Override
  public void spawn(@NotNull final Location location) {
    Util.addOrRemoveFromPlayerList(this, false);
    Util.sendTabListAdd(this);
    Util.sendPacket(
      new PacketPlayOutNamedEntitySpawn(this)
    );
    this.getBukkitEntity().teleport(location);
    Util.sendPositionUpdate(this);
    this.toggleVisible();
    this.toggleVisible();
  }

  @Override
  public void deSpawn() {
    Util.sendPacket(
      new PacketPlayOutEntityDestroy(this.getId()),
      new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, this)
    );
    Util.removeFromWorld(this);
    Util.removeFromServerPlayerList(this);
  }

  @Override
  public void toggleVisible() {
    if (this.visible) {
      Util.sendPacket(new PacketPlayOutEntityDestroy(this.getId()));
    } else {
      Util.sendPacket(new PacketPlayOutNamedEntitySpawn(this));
    }
    this.visible = !this.visible;
  }

  @Override
  public void die(final DamageSource damagesource) {
    if (!this.isRemoved()) {
      super.die(damagesource);
//      ((WorldServer) this.t).removeEntity(this);
    }
  }
}
