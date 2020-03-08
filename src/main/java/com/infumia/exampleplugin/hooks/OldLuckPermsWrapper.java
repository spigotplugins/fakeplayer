package com.infumia.exampleplugin.hooks;

import com.infumia.exampleplugin.Wrapped;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class OldLuckPermsWrapper implements Wrapped {

    @NotNull
    private final LuckPermsApi luckPermsApi;

    public OldLuckPermsWrapper(@NotNull final LuckPermsApi luckPermsApi) {
        this.luckPermsApi = luckPermsApi;
    }

    @NotNull
    public String getGroup(@NotNull final Player player) {
        final User user = this.luckPermsApi.getUserManager().getUser(player.getUniqueId());

        if (user == null) {
            return "";
        }

        return user.getPrimaryGroup();
    }

    // TODO Add new LuckPerms methods as you want.

}
