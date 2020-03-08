package com.infumia.fakeplayer.nms.v1_15_R1;

import java.util.Arrays;
import net.minecraft.server.v1_15_R1.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.jetbrains.annotations.NotNull;

final class NPCProtocol {

    NPCProtocol() {
    }

    static void sendPacket(@NotNull final Packet<?>... packets) {
        Bukkit.getOnlinePlayers().stream()
            .map(player -> (CraftPlayer) player)
            .forEach(player ->
                Arrays.stream(packets).forEach(player.getHandle().playerConnection::sendPacket)
            );
    }

}
